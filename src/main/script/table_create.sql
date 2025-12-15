CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `email` varchar(48) COLLATE utf8mb4_general_ci NOT NULL COMMENT '邮箱',
  `login_name` varchar(48) COLLATE utf8mb4_general_ci NOT NULL COMMENT '登录名称',
  `name` varchar(48) COLLATE utf8mb4_general_ci NOT NULL COMMENT '名称',
  `age` int DEFAULT NULL COMMENT '年龄',
  `status` tinyint(3) DEFAULT NULL COMMENT '状态',
  `remark` varchar(256) DEFAULT NULL COMMENT '备注',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_by` varchar(48) COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建用户',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `update_by` varchar(48) COLLATE utf8mb4_general_ci NOT NULL COMMENT '更新用户',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_email` (`email`),
  UNIQUE KEY `idx_login` (`login_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户信息表';