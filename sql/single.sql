/*
 Navicat Premium Data Transfer

 Source Server         : 本地3306
 Source Server Type    : MySQL
 Source Server Version : 50736
 Source Host           : localhost:3306
 Source Schema         : single

 Target Server Type    : MySQL
 Target Server Version : 50736
 File Encoding         : 65001

 Date: 08/12/2021 10:04:58
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `id` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键',
  `username` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '操作用户',
  `result_type` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '操作结果类型',
  `class_name` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '操作资源类名',
  `method_name` varchar(64) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '操作资源方法名',
  `method_type` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '操作资源请求类型',
  `log_desc` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '操作日志描述',
  `elapsed_time` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '消耗时间，单位毫秒',
  `browser` varchar(128) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '操作请求浏览器 + 版本',
  `os` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '操作请求的系统名',
  `platform` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '操作请求的平台名',
  `ip` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '操作请求的ip',
  `params` text COLLATE utf8mb4_unicode_ci COMMENT '操作请求的参数',
  `error_msg` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '异常Msg',
  `trace_id` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '异常日志traceId',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统操作日志';

-- ----------------------------
-- Records of sys_log
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键',
  `name` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '菜单名称',
  `pid` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '0' COMMENT '父菜单ID',
  `weight` tinyint(4) DEFAULT '0' COMMENT '显示权重',
  `uri` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '请求路径',
  `type` varchar(1) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '菜单类型（D目录 M菜单 B按钮）',
  `perms` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '权限标识',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='菜单权限表';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
BEGIN;
INSERT INTO `sys_menu` VALUES ('1', '测试权限', '0', 0, '/test/fail', 'B', 'test', '2021-12-06 17:47:24', '2021-12-06 17:47:24');
COMMIT;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键',
  `name` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '名称',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='角色表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_role` VALUES ('1', '测试角色1', '2021-12-06 17:45:04', '2021-12-06 17:45:04');
COMMIT;

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `id` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键',
  `role_id` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '角色id',
  `menu_id` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '菜单id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk-role_menu` (`role_id`,`menu_id`) COMMENT '联合唯一索引: 角色id + 权限id'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='角色菜单关系表';

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
BEGIN;
INSERT INTO `sys_role_menu` VALUES ('1', '1', '1');
COMMIT;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键',
  `username` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '用户名，唯一',
  `password` varchar(128) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '密码',
  `name` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '姓名',
  `phone` varchar(11) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '手机号，唯一',
  `is_enable` tinyint(1) NOT NULL DEFAULT '1' COMMENT '状态  0未启用 1 启用',
  `comment` varchar(300) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '备注',
  `is_admin` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否超级管理员 1是 0否',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标记 0未删除 1删除',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk-username` (`username`) USING BTREE,
  KEY `idx-phone` (`phone`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统用户表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` VALUES ('1', 'AgeFades', '$2a$10$OlG5Z62zpTvPEBMuuWlqeOFHvX8Z6.dCt4VMcAcsDwLez5L0nGlrC', '唐玄宗', '17570019433', 1, '测试用户', 1, 0, '2021-12-06 13:39:54', '2021-12-06 17:48:04');
INSERT INTO `sys_user` VALUES ('2', 'test1', '$2a$10$OlG5Z62zpTvPEBMuuWlqeOFHvX8Z6.dCt4VMcAcsDwLez5L0nGlrC', 'test1', '17570019422', 1, '权限测试用户', 0, 0, '2021-12-06 17:44:46', '2021-12-06 17:45:34');
COMMIT;

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '主键',
  `role_id` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '角色id',
  `user_id` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk-user_role` (`user_id`,`role_id`) COMMENT '联合唯一索引: 用户id + 角色id'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户角色关系表';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_user_role` VALUES ('1', '1', '2');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
