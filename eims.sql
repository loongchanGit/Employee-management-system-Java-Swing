/*
 Navicat MySQL Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 50729
 Source Host           : localhost:3306
 Source Schema         : eims

 Target Server Type    : MySQL
 Target Server Version : 50729
 File Encoding         : 65001

 Date: 28/12/2021 14:26:22
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_staff
-- ----------------------------
DROP TABLE IF EXISTS `tb_staff`;
CREATE TABLE `tb_staff`  (
  `id` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `gender` varchar(2) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `position` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `salary` double(10, 2) UNSIGNED NULL DEFAULT NULL,
  `telephone` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `state` int(1) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_staff
-- ----------------------------
INSERT INTO `tb_staff` VALUES ('1001', '陈荣城', '男', '保安队长', 3.00, '180', 0);
INSERT INTO `tb_staff` VALUES ('1002', '陈小龙', '男', '保安', 0.01, '100', 0);
INSERT INTO `tb_staff` VALUES ('1003', '曾嘉铭', '男', '2', 2.12, '1', 0);
INSERT INTO `tb_staff` VALUES ('1004', '马化腾', '男', 'CEO', 1.00, '140', 0);
INSERT INTO `tb_staff` VALUES ('1005', '张小龙', '男', 'CFO', 12000.00, '145', 0);

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user`  (
  `username` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `Permission_group` int(5) NOT NULL,
  `Employee_id` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`username`) USING BTREE,
  INDEX `id`(`Employee_id`) USING BTREE,
  CONSTRAINT `id` FOREIGN KEY (`Employee_id`) REFERENCES `tb_staff` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_user
-- ----------------------------
INSERT INTO `tb_user` VALUES ('admin', 'admin', 1, NULL);

SET FOREIGN_KEY_CHECKS = 1;
