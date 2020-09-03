/*
 Navicat Premium Data Transfer

 Source Server         : 安吉利
 Source Server Type    : MySQL
 Source Server Version : 50624
 Source Host           : 139.129.222.205:3306
 Source Schema         : mytest

 Target Server Type    : MySQL
 Target Server Version : 50624
 File Encoding         : 65001

 Date: 18/03/2019 11:59:31
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for countnum
-- ----------------------------
DROP TABLE IF EXISTS `countnum`;
CREATE TABLE `countnum`  (
  `weixinid` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `w_corpid` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `appid` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `scm` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `time` date NULL DEFAULT NULL,
  `content` varchar(10000) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for insorg
-- ----------------------------
DROP TABLE IF EXISTS `insorg`;
CREATE TABLE `insorg`  (
  `orgcode` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `c_corp` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `w_corpid` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `w_secret` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `w_trusturl` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `d_corpid` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `d_secret` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `d_trusturl` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `serverurl` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `uuid` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`uuid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for insstate
-- ----------------------------
DROP TABLE IF EXISTS `insstate`;
CREATE TABLE `insstate`  (
  `cid` smallint(6) NOT NULL,
  `sname` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `snamexa` varchar(40) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `snamexb` varchar(40) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `cidex` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `remark` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `c_corp` varchar(128) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `groups` varchar(40) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `smake` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `mkdate` date NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for inswaplist
-- ----------------------------
DROP TABLE IF EXISTS `inswaplist`;
CREATE TABLE `inswaplist`  (
  `orgcode` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `wapno` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `wapname` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `w_corpid` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `w_applyid` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '',
  `w_appsecret` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `w_wapurl` varchar(10000) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `d_applyid` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `d_wapurl` varchar(10000) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `dbid` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '01' COMMENT '数据库连接编号',
  `uuid` varchar(80) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `d_logo` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `d_appkey` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `d_appsecret` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  PRIMARY KEY (`orgcode`, `wapno`, `uuid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `NAME` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `spweixinid` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `spname` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `content` varchar(10000) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `yjcontent` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `tjtime` datetime(0) NULL DEFAULT NULL,
  `sptime` datetime(0) NULL DEFAULT NULL,
  `documentsid` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `documentstype` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `tablename` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `sbuid` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `state` int(11) NULL DEFAULT NULL,
  `state0` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `state1` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `dbid` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `scm` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `department` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `w_appid` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `wapno` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `w_corpid` varchar(500) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `smake` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `d_appid` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `d_corpid` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `id`(`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14895 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for oaggtz
-- ----------------------------
DROP TABLE IF EXISTS `oaggtz`;
CREATE TABLE `oaggtz`  (
  `id` int(9) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `c_corp` int(4) NULL DEFAULT 0 COMMENT '集团',
  `sid` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT '' COMMENT '编码',
  `slb` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '信息类别',
  `smaker` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '发布人',
  `sorgto` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '接收部门',
  `susr` longtext CHARACTER SET utf8 COLLATE utf8_bin NULL COMMENT '接收人',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '标题',
  `content` varchar(8000) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '内容',
  `mkdate` datetime(0) NULL DEFAULT NULL COMMENT '发布日期',
  `fj_root` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '附件路径',
  `suri` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '附件',
  `sbuid` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '单据类型',
  `state` int(4) NULL DEFAULT NULL COMMENT '状态',
  `schk` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '审核人',
  `ckdate` datetime(0) NULL DEFAULT NULL COMMENT '审核日期',
  `sorg` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '起草部门',
  `coll_cc` varchar(5) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '收藏次数',
  `scm` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '归属公司',
  `xxgs` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '信息归属',
  `read` blob NULL,
  `w_appid` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '应用id',
  `wapno` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '平台定义应用id',
  `w_corpid` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '微信企业号id',
  `serverurl` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '来源服务器地址',
  `source` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `dbid` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `d_appid` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `d_corpid` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 766 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for oaggtzread
-- ----------------------------
DROP TABLE IF EXISTS `oaggtzread`;
CREATE TABLE `oaggtzread`  (
  `sid` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `usercode` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `read` int(10) NULL DEFAULT NULL,
  `w_corpid` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `d_corpid` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `userid` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `tel` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `scm` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `w_corpid` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `w_imgurl` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `email` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `status` varchar(10) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '关注企业号状态',
  `d_corpid` varchar(100) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `d_imgurl` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  PRIMARY KEY (`userid`, `scm`, `w_corpid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
