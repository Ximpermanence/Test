/*
 Navicat MySQL Data Transfer

 Source Server         : 本地
 Source Server Type    : MySQL
 Source Server Version : 50717
 Source Host           : localhost:3306
 Source Schema         : textjava

 Target Server Type    : MySQL
 Target Server Version : 50717
 File Encoding         : 65001

 Date: 27/01/2021 16:11:02
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for class
-- ----------------------------
DROP TABLE IF EXISTS `class`;
CREATE TABLE `class`  (
  `id` int(11) NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `tid` int(11) NULL DEFAULT NULL COMMENT '班主任id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of class
-- ----------------------------
INSERT INTO `class` VALUES (1, '一班', 1);

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student`  (
  `id` int(11) NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `age` int(11) NULL DEFAULT NULL,
  `gender` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `nation_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES (1, 'v', 11, '男', '1');
INSERT INTO `student` VALUES (2, 'a', 12, '男', '1');
INSERT INTO `student` VALUES (3, 'b', 11, '女', '1');
INSERT INTO `student` VALUES (4, 'd', 11, '女', '1');
INSERT INTO `student` VALUES (5, 'e', 12, '女', '1');

-- ----------------------------
-- Table structure for student_class
-- ----------------------------
DROP TABLE IF EXISTS `student_class`;
CREATE TABLE `student_class`  (
  `id` int(11) NOT NULL,
  `cid` int(11) NULL DEFAULT NULL,
  `sid` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of student_class
-- ----------------------------
INSERT INTO `student_class` VALUES (1, 1, 1);

-- ----------------------------
-- Table structure for student_teacher
-- ----------------------------
DROP TABLE IF EXISTS `student_teacher`;
CREATE TABLE `student_teacher`  (
  `id` int(11) NOT NULL,
  `tid` int(11) NULL DEFAULT NULL,
  `sid` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of student_teacher
-- ----------------------------
INSERT INTO `student_teacher` VALUES (1, 1, 1);

-- ----------------------------
-- Table structure for sys_nation
-- ----------------------------
DROP TABLE IF EXISTS `sys_nation`;
CREATE TABLE `sys_nation`  (
  `code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `nation` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_nation
-- ----------------------------
INSERT INTO `sys_nation` VALUES ('1', '汉族');

-- ----------------------------
-- Table structure for teacher
-- ----------------------------
DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher`  (
  `id` int(11) NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `age` int(11) NULL DEFAULT NULL,
  `gender` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of teacher
-- ----------------------------
INSERT INTO `teacher` VALUES (1, 't', 25, '25');

-- ----------------------------
-- Table structure for teacher_class
-- ----------------------------
DROP TABLE IF EXISTS `teacher_class`;
CREATE TABLE `teacher_class`  (
  `id` int(11) NULL DEFAULT NULL,
  `tid` int(11) NULL DEFAULT NULL,
  `cid` int(11) NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of teacher_class
-- ----------------------------
INSERT INTO `teacher_class` VALUES (1, 1, 1);

SET FOREIGN_KEY_CHECKS = 1;
