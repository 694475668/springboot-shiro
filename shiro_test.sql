/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80020
 Source Host           : localhost:3306
 Source Schema         : shiro_test

 Target Server Type    : MySQL
 Target Server Version : 80020
 File Encoding         : 65001

 Date: 23/06/2020 19:28:45
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `test_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `test_key` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES (1, '添加', 'add');
INSERT INTO `permission` VALUES (2, '删除', 'delete');
INSERT INTO `permission` VALUES (3, '查询', 'query');
INSERT INTO `permission` VALUES (4, '修改', 'edit');

-- ----------------------------
-- Table structure for permission_role
-- ----------------------------
DROP TABLE IF EXISTS `permission_role`;
CREATE TABLE `permission_role`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `permission_id` int(0) NULL DEFAULT NULL,
  `role_id` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of permission_role
-- ----------------------------
INSERT INTO `permission_role` VALUES (1, 1, 1);
INSERT INTO `permission_role` VALUES (2, 2, 1);
INSERT INTO `permission_role` VALUES (3, 3, 1);
INSERT INTO `permission_role` VALUES (4, 4, 1);
INSERT INTO `permission_role` VALUES (5, 1, 2);
INSERT INTO `permission_role` VALUES (6, 2, 2);
INSERT INTO `permission_role` VALUES (7, 3, 2);
INSERT INTO `permission_role` VALUES (8, 1, 3);
INSERT INTO `permission_role` VALUES (9, 3, 3);

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `test_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `test_key` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, '管理员', 'admin');
INSERT INTO `role` VALUES (2, '没有修改权限', 'zhangsan');
INSERT INTO `role` VALUES (3, '没有删除和修改权限', 'zhaoliu');

-- ----------------------------
-- Table structure for role_user
-- ----------------------------
DROP TABLE IF EXISTS `role_user`;
CREATE TABLE `role_user`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `role_id` int(0) NULL DEFAULT NULL,
  `user_id` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_user
-- ----------------------------
INSERT INTO `role_user` VALUES (1, 1, 1);
INSERT INTO `role_user` VALUES (2, 2, 2);
INSERT INTO `role_user` VALUES (3, 2, 3);
INSERT INTO `role_user` VALUES (4, 1, 3);
INSERT INTO `role_user` VALUES (5, 3, 4);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `password` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'admin', 'bbe16109349710c85bbe5880d3570f7e');
INSERT INTO `user` VALUES (2, 'zhangsan', 'bbe16109349710c85bbe5880d3570f7e');
INSERT INTO `user` VALUES (3, 'lisi', 'bbe16109349710c85bbe5880d3570f7e');
INSERT INTO `user` VALUES (4, 'zhaoliu', 'bbe16109349710c85bbe5880d3570f7e');

SET FOREIGN_KEY_CHECKS = 1;
