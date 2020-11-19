/*
 Navicat Premium Data Transfer
 Date: 19/11/2020 15:13:33
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for lock_area
-- ----------------------------
DROP TABLE IF EXISTS `lock_area`;
CREATE TABLE `lock_area`  (
  `area_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '辖区id',
  `area_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '区域名称',
  `city_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '所属城市id',
  PRIMARY KEY (`area_id`) USING BTREE,
  INDEX `FK_lockArea_lockCity`(`city_id`) USING BTREE,
  CONSTRAINT `FK_lockArea_lockCity` FOREIGN KEY (`city_id`) REFERENCES `lock_city` (`city_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '辖区表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for lock_city
-- ----------------------------
DROP TABLE IF EXISTS `lock_city`;
CREATE TABLE `lock_city`  (
  `city_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '城市id',
  `city_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '城市名称',
  `province_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '所属省份',
  PRIMARY KEY (`city_id`) USING BTREE,
  INDEX `FK_lockCity_localProvince`(`province_id`) USING BTREE,
  CONSTRAINT `FK_lockCity_localProvince` FOREIGN KEY (`province_id`) REFERENCES `lock_province` (`province_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '城市表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for lock_community
-- ----------------------------
DROP TABLE IF EXISTS `lock_community`;
CREATE TABLE `lock_community`  (
  `community_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '社区id',
  `community_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '社区名称',
  `street_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '街道id',
  PRIMARY KEY (`community_id`) USING BTREE,
  INDEX `FK_lockCommunity_lockStreet`(`street_id`) USING BTREE,
  CONSTRAINT `FK_lockCommunity_lockStreet` FOREIGN KEY (`street_id`) REFERENCES `lock_street` (`street_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '社区居委会表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for lock_province
-- ----------------------------
DROP TABLE IF EXISTS `lock_province`;
CREATE TABLE `lock_province`  (
  `province_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '省份id',
  `province_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '省份名称',
  PRIMARY KEY (`province_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '省份表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for lock_street
-- ----------------------------
DROP TABLE IF EXISTS `lock_street`;
CREATE TABLE `lock_street`  (
  `street_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '街道id',
  `street_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '街道名称',
  `area_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '所属区域',
  PRIMARY KEY (`street_id`) USING BTREE,
  INDEX `FK_lockStreet_lockArea`(`area_id`) USING BTREE,
  CONSTRAINT `FK_lockStreet_lockArea` FOREIGN KEY (`area_id`) REFERENCES `lock_area` (`area_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '街道表' ROW_FORMAT = DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
