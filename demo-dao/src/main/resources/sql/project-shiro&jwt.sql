/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50627
 Source Host           : localhost:3306
 Source Schema         : project

 Target Server Type    : MySQL
 Target Server Version : 50627
 File Encoding         : 65001

 Date: 03/07/2020 15:44:50
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for con_user_role
-- ----------------------------
DROP TABLE IF EXISTS `con_user_role`;
CREATE TABLE `con_user_role`  (
  `user_role_id` int(11) NOT NULL AUTO_INCREMENT COMMENT ''用户角色关联表id'',
  `user_id` int(11) NULL DEFAULT NULL COMMENT ''用户id'',
  `role_id` int(11) NULL DEFAULT NULL COMMENT ''角色id'',
  PRIMARY KEY (`user_role_id`) USING BTREE,
  UNIQUE INDEX `con_user_role`(`user_id`, `role_id`) USING BTREE COMMENT ''用户角色关联唯一索引''
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of con_user_role
-- ----------------------------
INSERT INTO `con_user_role` VALUES (2, 2, 1);

-- ----------------------------
-- Table structure for re_param_info
-- ----------------------------
DROP TABLE IF EXISTS `re_param_info`;
CREATE TABLE `re_param_info`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT ''id'',
  `param_sign` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT ''创建时间'',
  `update_time` timestamp(0) NULL DEFAULT NULL COMMENT ''更新时间'',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of re_param_info
-- ----------------------------
INSERT INTO `re_param_info` VALUES (''2'', ''+'', ''2020-07-03 06:38:50'', ''2020-07-03 06:38:50'');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `role_id` int(11) NOT NULL AUTO_INCREMENT COMMENT ''角色id'',
  `role_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT ''角色名称'',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT ''角色描述'',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (0, ''admin'', ''管理员'');
INSERT INTO `role` VALUES (1, ''normal'', ''普通用户'');
INSERT INTO `role` VALUES (2, ''vip'', ''会员用户'');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT ''用户id'',
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT ''手机号'',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT ''密码'',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT ''用户名'',
  `sex` tinyint(255) NULL DEFAULT 0 COMMENT ''性别'',
  `birth` date NULL DEFAULT NULL COMMENT ''出生日期'',
  `register_time` datetime(0) NULL DEFAULT NULL COMMENT ''注册时间'',
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `phone`(`phone`) USING BTREE COMMENT ''手机号唯一索引''
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (2, ''18366106928'', ''e974340b4f3fb2cd79db5424072f376d'', ''估富扒惊'', 0, NULL, ''2020-06-24 02:28:44'');

SET FOREIGN_KEY_CHECKS = 1;
