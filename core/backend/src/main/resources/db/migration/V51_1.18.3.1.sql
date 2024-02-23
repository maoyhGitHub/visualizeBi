
CREATE TABLE IF NOT EXISTS `dataset_static_data` (
  `table_id` varchar(50) NOT NULL COMMENT '表id',
  `view_id` varchar(50) NOT NULL COMMENT '视图id',
  `excel_keys` varchar(500) DEFAULT NULL COMMENT 'x轴key',
  `json_details` longtext COMMENT '数据详情',
  `status` tinyint(4) DEFAULT '0' COMMENT '0初始，1已提交，2通过，3未通过',
  `new_json_details` longtext COMMENT '修改后数据回显',
  `excel_new_data` text COMMENT '修改后视图加载',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`table_id`,`view_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;


CREATE TABLE IF NOT EXISTS `panel_image_library` (
  `id` varchar(50) NOT NULL,
  `file_origin_name` varchar(50) DEFAULT NULL COMMENT '文件原始名称',
  `file_name` varchar(50) DEFAULT NULL COMMENT '文件唯一名称',
  `file_category` varchar(50) DEFAULT NULL COMMENT '分类',
  `file_path` varchar(100) DEFAULT NULL COMMENT '路径',
  `node_type` varchar(255) DEFAULT NULL COMMENT '节点类型 folder or image 分类目录或者图片',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;

REPLACE INTO `panel_image_library`(`id`, `file_origin_name`, `file_name`, `file_category`, `file_path`, `node_type`, `create_time`) VALUES ('-1', NULL, NULL, '默认分组', NULL, 'folder', '2023-08-10 14:56:31');


CREATE TABLE IF NOT EXISTS `panel_indicators` (
  `id` varchar(50) NOT NULL,
  `name` varchar(100) DEFAULT NULL COMMENT '名称',
  `indicators_no` varchar(100) DEFAULT NULL COMMENT '指标编号',
  `indicators_explain` varchar(255) DEFAULT NULL COMMENT '指标说明',
  `pid` varchar(50) DEFAULT NULL COMMENT '父级id',
  `level` int(11) DEFAULT NULL COMMENT '层级',
  `node_type` varchar(20) DEFAULT NULL COMMENT '节点类型  folder or subject 目录或者指标',
  `dataset_table_id` varchar(50) DEFAULT NULL COMMENT '数据集id',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `create_by` varchar(100) DEFAULT NULL COMMENT '创建人',
  `create_time` bigint(20) DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC;


REPLACE INTO `sys_menu` (`menu_id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `menu_sort`, `icon`, `path`, `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (36, 61, 1, 1, '图标库', 'system-icon', 'repository/icon/index', 13, 'dashboard', 'repository/icon/index', b'0', b'0', b'0', '', NULL, NULL, NULL, 1620444227389);
REPLACE INTO `sys_menu` (`menu_id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `menu_sort`, `icon`, `path`, `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (37, 61, 1, 1, '组件库', 'repository-component', 'repository/component/index', 13, 'dashboard', 'component', b'0', b'0', b'0', '', NULL, NULL, NULL, 1620444227389);
REPLACE INTO `sys_menu` (`menu_id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `menu_sort`, `icon`, `path`, `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (41, 61, 1, 1, '模板管理', 'system-template', 'panel/template/index', 13, 'dashboard', 'panel/template/index', b'0', b'0', b'0', 'template:read', NULL, NULL, NULL, 1620444227389);
REPLACE INTO `sys_menu` (`menu_id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `menu_sort`, `icon`, `path`, `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (42, 61, 1, 1, '指标库', 'system-indicators', 'repository/indicators/index', 13, 'dashboard', 'repository/indicators/index', b'0', b'0', b'0', '', NULL, NULL, NULL, 1620444227389);
REPLACE INTO `sys_menu` (`menu_id`, `pid`, `sub_count`, `type`, `title`, `name`, `component`, `menu_sort`, `icon`, `path`, `i_frame`, `cache`, `hidden`, `permission`, `create_by`, `update_by`, `create_time`, `update_time`) VALUES (61, 0, 3, 0, '资源库', 'repository', 'Layout', 5, 'sys-relationship', '/repository', b'0', b'0', b'0', '', NULL, NULL, NULL, NULL);


-- UPDATE `sys_menu` SET `pid` = 99999 WHERE `menu_id` = 203;
-- UPDATE `sys_menu` SET `pid` = 99999 WHERE `menu_id` = 1100;

CREATE TABLE IF NOT EXISTS `panel_link_jump_info_params` (
  `id` varchar(50)  NOT NULL,
  `link_jump_info_id` varchar(50)  DEFAULT NULL COMMENT 'panel_link_jump_info ID',
  `target_panel_id` varchar(255)  DEFAULT NULL COMMENT '关联仪表板ID',
  `is_fullscreen` varchar(255)  DEFAULT NULL COMMENT '是否全屏',
  `dialog_width` varchar(50) DEFAULT NULL COMMENT '弹窗宽度',
  `dialog_height` varchar(50) DEFAULT NULL COMMENT '弹窗高度',
  `open_params` varchar(255) DEFAULT NULL COMMENT '弹窗参数',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC

