package cn.youngbear;

import java.io.*;

public class Utils {
    public static String[] splitCode(String code){
        String[] result= new String[5];
        result[0] = code.substring(0,2);
        result[1] = code.substring(0,4);
        result[2] = code.substring(0,6);
        result[3] = code.substring(0,9);
        result[4] = code.substring(0,12);
        return result;
    }
    public static String addZero(String data,int length){
        if(data.length() == length){
            return data;
        }else if(data.length() > length){
            return null;
        }

        int dataLength = data.length();
        for(int i =0 ;i<length-dataLength;i++){
            data=data+"0";
        }
        return data;
    }
    public static String subString(String str, String strStart, String strEnd) {

        /* 找出指定的2个字符在 该字符串里面的 位置 */
        int strStartIndex = str.indexOf(strStart);
        int strEndIndex = str.indexOf(strEnd);

        /* index 为负数 即表示该字符串中 没有该字符 */
        if (strStartIndex < 0) {
            return "字符串 :---->" + str + "<---- 中不存在 " + strStart + ", 无法截取目标字符串";
        }
        if (strEndIndex < 0) {
            return "字符串 :---->" + str + "<---- 中不存在 " + strEnd + ", 无法截取目标字符串";
        }
        /* 开始截取 */
        String result = str.substring(strStartIndex, strEndIndex).substring(strStart.length());
        return result;
    }
    public static void readData(String data,String filePath) throws IOException {
        File file = new File(filePath);
        if(!file.exists()){
            file.createNewFile();
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath,true));
        writer.write(data+"\n");
        writer.flush();
        writer.close();
    }

    /**
     * 传入 统计用区划代码 返回上级id
     * @param data
     * @return
     */
    public static String getPid(String data) {
        String b = data.replaceAll("(0)+$", "");
        if(b.length()>=1 && b.length()<=2){
            return b+"000000000000";
        }else if(b.length()>=3 && b.length()<=4){
            return b.substring(0,2)+"0000000000";
        }else if(b.length()>=5 && b.length()<=6){
            return b.substring(0,4)+"00000000";
        }else if(b.length()>=7 && b.length()<=9){
            return b.substring(0,6)+"000000";
        }else if(b.length()>=10 && b.length()<=12){
            return b.substring(0,9)+"000";
        }else{
            System.exit(0);
        }
        return b.length()+"error"+"==================>";
    }
}
