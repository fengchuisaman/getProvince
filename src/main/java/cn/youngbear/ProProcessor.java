package cn.youngbear;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.io.IOException;
import java.util.List;

public class ProProcessor implements PageProcessor {
    // 判断是不是首页
    private boolean isIndex = true;
    private Site site = Site.me();
    public void process(Page page) {
        //获取url并处理，方便填写下一个地址
        String url =page.getUrl().toString();
        String baseUrl = url.substring(0,url.lastIndexOf("/"))+"/";
        Html html = page.getHtml();
        String path ;
        String data ;
        if(isIndex){
            isIndex = false;
            Selectable selectable = html.xpath("//*[@id=\"Map\"]/table/tbody/tr[1]/td/table/tbody/tr[2]/td/table/tbody/tr/td/table/tbody/tr[@class=\"provincetr\"]/td");
            //获取当前页数下的所有信息个数，方便下面遍历使用
            List<String> size = selectable.all();
            for(int i=0;i<size.size();i++){
                path = html.xpath("//*[@id=\"Map\"]/table/tbody/tr[1]/td/table/tbody/tr[2]/td/table/tbody/tr/td/table/tbody/tr["+(4+i/8)+"]/td["+(i%8+1)+"]/a/@href").toString();
                //获取 统计用区划代码
                String id = path.split(".html")[0];
                //获取 名称
                String name = html.xpath("//*[@id=\"Map\"]/table/tbody/tr[1]/td/table/tbody/tr[2]/td/table/tbody/tr/td/table/tbody/tr["+(4+i/8)+"]/td["+(i%8+1)+"]/a/text()").toString();
                //构造sql语句，因为是第一个页面所以肯定是省
                data = "INSERT INTO `lock_province`(`province_id`, `province_name`) VALUES ('"+id+"0000000000"+"', '"+name+"');";
                try {
                    Utils.readData(data,"E:\\12.sql");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String nextUrl = baseUrl+path;
                page.addTargetRequest(nextUrl);
            }
        }
        else{
            Selectable selectable = html.xpath("//*[@id=\"Map\"]/table/tbody/tr[1]/td/table/tbody/tr[2]/td/table/tbody/tr/td/table/tbody/tr");
            List<String> size = selectable.all();
            //遍历table
            for(int i=0;i<size.size()-1;i++) {
                path = html.xpath("//*[@id=\"Map\"]/table/tbody/tr[1]/td/table/tbody/tr[2]/td/table/tbody/tr/td/table/tbody/tr["+(i+2)+"]/td["+1+"]/a/@href").toString();
                String id = html.xpath("//*[@id=\"Map\"]/table/tbody/tr[1]/td/table/tbody/tr[2]/td/table/tbody/tr/td/table/tbody/tr["+(i+2)+"]/td["+1+"]/a/text()").toString();
                String name = html.xpath("//*[@id=\"Map\"]/table/tbody/tr[1]/td/table/tbody/tr[2]/td/table/tbody/tr/td/table/tbody/tr["+(i+2)+"]/td["+2+"]/a/text()").toString();
                if(id == null && name == null){ //在最后一层，居委会是没有a标签的，并且居委会名是第三个td下
                    id = html.xpath("//*[@id=\"Map\"]/table/tbody/tr[1]/td/table/tbody/tr[2]/td/table/tbody/tr/td/table/tbody/tr["+(i+2)+"]/td["+1+"]/text()").toString();
                    name = html.xpath("//*[@id=\"Map\"]/table/tbody/tr[1]/td/table/tbody/tr[2]/td/table/tbody/tr/td/table/tbody/tr["+(i+2)+"]/td["+3+"]/text()").toString();
                    if(name == null){ // 市辖区也没有a标签并且他的td是2
                        name = html.xpath("//*[@id=\"Map\"]/table/tbody/tr[1]/td/table/tbody/tr[2]/td/table/tbody/tr/td/table/tbody/tr["+(i+2)+"]/td["+2+"]/text()").toString();
                    }
                }
                //判断层级
                String codeDeep = id.replaceAll("(0)+$", "");
                if(codeDeep.length() == 4||codeDeep.length() == 3){ //市
                    // 准备sql
                    data = "INSERT INTO `lock_city`(`city_id`, `city_name`, `province_id`) VALUES ('"+id+"', '"+name+"', '"+ Utils.getPid(id)+"');";
                }else if(codeDeep.length() == 6||codeDeep.length() == 5){//区
                    data = "INSERT INTO `lock_area`(`area_id`, `area_name`, `city_id`) VALUES ('"+id+"', '"+name+"', '"+Utils.getPid(id)+"');";
                }else if(codeDeep.length() == 9|| codeDeep.length() == 8||codeDeep.length() == 7) {//街
                    data = "INSERT INTO `lock_street`(`street_id`, `street_name`, `area_id`) VALUES ('"+id+"', '"+name+"', '"+Utils.getPid(id)+"');";
                }else if(codeDeep.length() == 12 || codeDeep.length() == 11 || codeDeep.length() == 10) {//居委会
                    data = "INSERT INTO `lock_community`(`community_id`, `community_name`, `street_id`) VALUES ('"+id+"', '"+name+"', '"+Utils.getPid(id)+"');";
                }else{
                    data = (codeDeep.length()+"error"+"==================>");
                }
                try {
                    Utils.readData(data,"E:\\12.sql");
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if(path != null){
                    String nextUrl = baseUrl+path;
                    page.addTargetRequest(nextUrl);
                }

            }


        }
    }

    public Site getSite() {
        return site.setRetryTimes(100).addHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:82.0) Gecko/20100101 Firefox/82.0")
                ;
    }

    public static void main(String[] args) {
        Spider spider = Spider.create(new ProProcessor());
        spider.addUrl("http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2018/")
                .run();
    }

}
