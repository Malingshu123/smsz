-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('应用', '3', '1', 'resource', 'system/resource/index', 1, 0, 'C', '0', '0', 'system:resource:list', '#', 'admin', sysdate(), '', null, '应用菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('应用查询', @parentId, '1',  '#', '', 1, 0, 'F', '0', '0', 'system:resource:query',        '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('应用新增', @parentId, '2',  '#', '', 1, 0, 'F', '0', '0', 'system:resource:add',          '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('应用修改', @parentId, '3',  '#', '', 1, 0, 'F', '0', '0', 'system:resource:edit',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('应用删除', @parentId, '4',  '#', '', 1, 0, 'F', '0', '0', 'system:resource:remove',       '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('应用导出', @parentId, '5',  '#', '', 1, 0, 'F', '0', '0', 'system:resource:export',       '#', 'admin', sysdate(), '', null, '');