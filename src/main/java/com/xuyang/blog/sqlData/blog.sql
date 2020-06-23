/*
 Navicat Premium Data Transfer

 Source Server         : 本机1
 Source Server Type    : MySQL
 Source Server Version : 80013
 Source Host           : localhost:3306
 Source Schema         : blog

 Target Server Type    : MySQL
 Target Server Version : 80013
 File Encoding         : 65001

 Date: 23/06/2020 09:09:59
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for account
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account` (
  `uuid` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '主键UUID',
  `username` varchar(32) DEFAULT NULL COMMENT '用户名',
  `password` varchar(32) DEFAULT NULL COMMENT '密码',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `is_del` int(1) NOT NULL DEFAULT '0' COMMENT '是否删除 0 未删除 1 已删除',
  `phone` varchar(11) DEFAULT NULL COMMENT '手机号',
  `address` varchar(255) DEFAULT NULL COMMENT '地址',
  `email` varchar(255) DEFAULT NULL COMMENT '邮箱',
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of account
-- ----------------------------
BEGIN;
INSERT INTO `account` VALUES ('111', '1', '1', '2019-09-27 19:01:24', '2019-09-27 19:01:30', 0, NULL, NULL, NULL);
INSERT INTO `account` VALUES ('123', '232', '804B105F9C879E92098199A53F86B984', '2019-09-28 10:25:59', '2019-09-28 10:25:59', 0, NULL, NULL, NULL);
INSERT INTO `account` VALUES ('1233333', '432', '432432', '2019-09-28 10:54:45', '2019-09-28 10:54:45', 0, NULL, NULL, NULL);
INSERT INTO `account` VALUES ('123456', 'admin', '804B105F9C879E92098199A53F86B984', '2019-10-14 17:18:33', '2019-10-14 17:18:37', 0, NULL, NULL, NULL);
INSERT INTO `account` VALUES ('1694e129-f11f-436f-8fd3-89019ba9f5ce', '432', '804B105F9C879E92098199A53F86B984', '2019-09-30 12:02:04', '2019-09-30 12:02:04', 0, NULL, NULL, NULL);
INSERT INTO `account` VALUES ('2ced495c-8769-41aa-ac5c-7bdb6780037e', '432', '432432', '2019-09-30 11:59:26', '2019-09-30 11:59:26', 0, NULL, NULL, NULL);
INSERT INTO `account` VALUES ('3e5d81be-3aaf-4a49-8c86-ca253e98bf4a', '432', '432432', '2019-09-30 11:58:08', '2019-09-30 11:58:08', 0, NULL, NULL, NULL);
INSERT INTO `account` VALUES ('45f4ebfd-8de9-40cf-af5d-c6f3da805181', '432', '432432', '2019-09-30 11:58:14', '2019-09-30 11:58:14', 0, NULL, NULL, NULL);
INSERT INTO `account` VALUES ('48a014a2-3f90-4ef2-bfec-03dffd3b550c', '432', '432432', '2019-09-30 11:16:01', '2019-09-30 11:16:01', 0, NULL, NULL, NULL);
INSERT INTO `account` VALUES ('8899c5cc-b408-4e97-ad5c-27743d5f3faf', '432', '432432', '2019-09-30 11:59:29', '2019-09-30 11:59:29', 0, NULL, NULL, NULL);
INSERT INTO `account` VALUES ('a0895395-f52f-49b3-b038-759861d7583d', '432', '432432', '2019-09-30 11:58:52', '2019-09-30 11:58:52', 0, NULL, NULL, NULL);
INSERT INTO `account` VALUES ('e32bee46-77cd-4520-a636-a5b5c41bfd66', '432', '432432', '2019-09-30 11:58:15', '2019-09-30 11:58:15', 0, NULL, NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for account_info
-- ----------------------------
DROP TABLE IF EXISTS `account_info`;
CREATE TABLE `account_info` (
  `uuid` varchar(32) NOT NULL COMMENT '主键UUID',
  `username` varchar(32) NOT NULL COMMENT '账号名',
  `nickName` varchar(32) DEFAULT NULL COMMENT '别名',
  `name` varchar(32) NOT NULL COMMENT '姓名',
  `sex` char(1) DEFAULT '0' COMMENT '性别（0为未知，1为男，2为女）',
  `mobile` varchar(11) DEFAULT NULL COMMENT '手机号',
  `email` varchar(32) DEFAULT NULL COMMENT '邮箱地址',
  `avatar` varchar(128) DEFAULT NULL COMMENT '头像url',
  `is_del` char(1) DEFAULT '0' COMMENT '删除标识（0表示未删除，1表示删除）',
  `level` char(2) DEFAULT '0' COMMENT '用户等级',
  `job` varchar(32) DEFAULT NULL COMMENT '职位',
  `introduction_of_author` varchar(256) DEFAULT NULL COMMENT '作者简介',
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for article
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `title` varchar(256) DEFAULT NULL COMMENT '文章标题',
  `summary` text COMMENT '文章摘要',
  `first_tag` varchar(256) DEFAULT NULL COMMENT '文章大标签',
  `sec_tag` varchar(256) DEFAULT NULL COMMENT '文章小标签',
  `file` varchar(64) DEFAULT NULL COMMENT '文章文件',
  `user_id` varchar(32) DEFAULT NULL COMMENT '用户ID',
  `status` char(1) DEFAULT '0' COMMENT '审核状态（0表示未审核，1表示已审核）',
  `is_del` char(1) DEFAULT '0' COMMENT '删除标识（0标识未删除，1表示已删除）',
  `is_secret` char(1) DEFAULT '0' COMMENT '是否公开（0表示公开，1表示不公开）',
  `is_good` char(1) DEFAULT '0' COMMENT '是否精华（0表示不是精华，1表示精华）',
  `num_of_like` int(11) DEFAULT '0' COMMENT '点赞数',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `annex_id` varchar(256) DEFAULT NULL COMMENT '附件 Id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=79 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for attachment_info
-- ----------------------------
DROP TABLE IF EXISTS `attachment_info`;
CREATE TABLE `attachment_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(32) DEFAULT NULL COMMENT '附件名称',
  `url` varchar(256) NOT NULL COMMENT '附件链接',
  `article_id` int(11) DEFAULT NULL COMMENT '文章id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `original_name` varchar(255) DEFAULT NULL COMMENT '文件原名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2635 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `id` int(11) NOT NULL DEFAULT '0',
  `article_id` int(11) DEFAULT NULL COMMENT '主贴文章ID',
  `content` text COMMENT '评论内容',
  `parent_id` int(11) DEFAULT NULL COMMENT '评论父级ID',
  `user_id` varchar(32) DEFAULT NULL COMMENT '回复人ID',
  `title` varchar(128) DEFAULT NULL COMMENT '标题',
  `num_of_like` int(11) DEFAULT '0' COMMENT '点赞数',
  `create_time` datetime DEFAULT NULL COMMENT '发表时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `is_del` char(1) DEFAULT '0' COMMENT '删除标识（0标识未删除，1表示已删除）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for like_record
-- ----------------------------
DROP TABLE IF EXISTS `like_record`;
CREATE TABLE `like_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` varchar(32) DEFAULT NULL COMMENT '用户ID',
  `article_id` int(11) DEFAULT NULL COMMENT '文章ID',
  `comment_id` int(11) DEFAULT NULL COMMENT '评论ID',
  `like_status` char(1) DEFAULT NULL COMMENT '是否喜欢（0表示不喜欢，1表示喜欢）',
  `created_at` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_at` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=117 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for type
-- ----------------------------
DROP TABLE IF EXISTS `type`;
CREATE TABLE `type` (
  `id` tinyint(4) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `pid` tinyint(4) DEFAULT NULL COMMENT '父ID',
  `type_of_tag` varchar(32) DEFAULT NULL COMMENT '标签类型',
  `type_name` varchar(32) DEFAULT NULL COMMENT '类型名称',
  `type_count` int(11) DEFAULT '0' COMMENT '文章数',
  `type_icon` varchar(256) DEFAULT NULL COMMENT '类型图标',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

SET FOREIGN_KEY_CHECKS = 1;
