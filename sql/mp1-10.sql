/*
 Navicat Premium Data Transfer

 Source Server         : 127.0.0.1：3306
 Source Server Type    : MySQL
 Source Server Version : 80036
 Source Host           : localhost:3306
 Source Schema         : mp

 Target Server Type    : MySQL
 Target Server Version : 80036
 File Encoding         : 65001

 Date: 10/01/2025 11:08:04
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for demo
-- ----------------------------
DROP TABLE IF EXISTS `demo`;
CREATE TABLE `demo`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of demo
-- ----------------------------
INSERT INTO `demo` VALUES ('1', 'test');
INSERT INTO `demo` VALUES ('1867547672768950274', NULL);
INSERT INTO `demo` VALUES ('1867547808060420097', NULL);
INSERT INTO `demo` VALUES ('1867547938482302977', NULL);

-- ----------------------------
-- Table structure for order_table
-- ----------------------------
DROP TABLE IF EXISTS `order_table`;
CREATE TABLE `order_table`  (
  `id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '订单ID',
  `user_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户ID',
  `order_date` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '下单时间',
  `total_amount` decimal(10, 2) NOT NULL COMMENT '订单总金额',
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT 'pending' COMMENT '订单状态',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '订单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order_table
-- ----------------------------
INSERT INTO `order_table` VALUES ('1877286031356354560', '1', '2025-01-09 17:27:23', 0.00, '');
INSERT INTO `order_table` VALUES ('1877286452460281856', '1', '2025-01-09 17:29:03', 0.00, '');
INSERT INTO `order_table` VALUES ('1877286454469353472', '1', '2025-01-09 17:29:04', 0.00, '');
INSERT INTO `order_table` VALUES ('1877286455463403520', '1', '2025-01-09 17:29:04', 0.00, '');
INSERT INTO `order_table` VALUES ('1877286456532951040', '1', '2025-01-09 17:29:04', 0.00, '');
INSERT INTO `order_table` VALUES ('1877286457090793472', '1', '2025-01-09 17:29:04', 0.00, '');
INSERT INTO `order_table` VALUES ('1877286457657024512', '1', '2025-01-09 17:29:04', 0.00, '');
INSERT INTO `order_table` VALUES ('1877286458298753024', '1', '2025-01-09 17:29:05', 0.00, '');
INSERT INTO `order_table` VALUES ('1877286458823041024', '1', '2025-01-09 17:29:05', 0.00, '');
INSERT INTO `order_table` VALUES ('1877286459368300544', '1', '2025-01-09 17:29:05', 0.00, '');
INSERT INTO `order_table` VALUES ('1877286459926142976', '1', '2025-01-09 17:29:05', 0.00, '');
INSERT INTO `order_table` VALUES ('1877286460496568320', '1', '2025-01-09 17:29:05', 0.00, '');
INSERT INTO `order_table` VALUES ('1877286461066993664', '1', '2025-01-09 17:29:05', 0.00, '');
INSERT INTO `order_table` VALUES ('1877293796581244928', '2', '2025-01-09 17:58:14', 0.00, '');
INSERT INTO `order_table` VALUES ('1877293797810176000', '2', '2025-01-09 17:58:14', 0.00, '');
INSERT INTO `order_table` VALUES ('1877293798472876032', '2', '2025-01-09 17:58:15', 0.00, '');
INSERT INTO `order_table` VALUES ('1877293799039107072', '2', '2025-01-09 17:58:15', 0.00, '');
INSERT INTO `order_table` VALUES ('1877293799638892544', '2', '2025-01-09 17:58:15', 0.00, '');
INSERT INTO `order_table` VALUES ('1877293800670691328', '2', '2025-01-09 17:58:15', 0.00, '');
INSERT INTO `order_table` VALUES ('1877293801245310976', '2', '2025-01-09 17:58:15', 0.00, '');
INSERT INTO `order_table` VALUES ('1877293801832513536', '2', '2025-01-09 17:58:15', 0.00, '');
INSERT INTO `order_table` VALUES ('1877293802390355968', '2', '2025-01-09 17:58:16', 0.00, '');
INSERT INTO `order_table` VALUES ('1877293802981752832', '2', '2025-01-09 17:58:16', 0.00, '');
INSERT INTO `order_table` VALUES ('1877293803556372480', '2', '2025-01-09 17:58:16', 0.00, '');
INSERT INTO `order_table` VALUES ('1877293804151963648', '2', '2025-01-09 17:58:16', 0.00, '');
INSERT INTO `order_table` VALUES ('1877293804739166208', '2', '2025-01-09 17:58:16', 0.00, '');
INSERT INTO `order_table` VALUES ('1877293805309591552', '2', '2025-01-09 17:58:16', 0.00, '');
INSERT INTO `order_table` VALUES ('1877293805997457408', '2', '2025-01-09 17:58:16', 0.00, '');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户ID',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '邮箱',
  `created_at` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'zhangsan', '12121@qq.com', '2025-01-09 16:52:49');
INSERT INTO `user` VALUES ('1877277302556061697', 'zhangsan', '12121@qq.com', '2025-01-09 16:52:42');
INSERT INTO `user` VALUES ('1877280787406114818', 'zhangsan', '12121@qq.com', '2025-01-09 17:06:33');
INSERT INTO `user` VALUES ('1877280843689480193', 'zhangsan', '12121@qq.com', '2025-01-09 17:06:46');
INSERT INTO `user` VALUES ('1877281368585654273', 'zhangsan', '12121@qq.com', '2025-01-09 17:08:51');
INSERT INTO `user` VALUES ('2', 'zhangsan', '12121@qq.com', '2025-01-09 16:52:51');
INSERT INTO `user` VALUES ('23123', '21312', '121', '2025-01-09 19:39:05');

-- ----------------------------
-- Table structure for user_detail
-- ----------------------------
DROP TABLE IF EXISTS `user_detail`;
CREATE TABLE `user_detail`  (
  `id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户详情ID，等于用户ID',
  `user_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户ID，唯一且等于用户表ID',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '地址',
  `birthday` datetime(0) NULL DEFAULT NULL COMMENT '生日',
  `phone` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '联系电话',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户详情表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_detail
-- ----------------------------
INSERT INTO `user_detail` VALUES ('1877293755258634242', '2', '湖北武汉', NULL, '131232134');
INSERT INTO `user_detail` VALUES ('2131', '1', '湖北武汉', '2029-11-25 12:00:00', '131232134');

SET FOREIGN_KEY_CHECKS = 1;
