-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('卡密', '1', '1', 'card', 'system/card/index', 1, 0, 'C', '0', '0', 'system:card:list', '#', 'admin', sysdate(), '', null, '卡密菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('卡密查询', @parentId, '1',  '#', '', 1, 0, 'F', '0', '0', 'system:card:query',        '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('卡密新增', @parentId, '2',  '#', '', 1, 0, 'F', '0', '0', 'system:card:add',          '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('卡密修改', @parentId, '3',  '#', '', 1, 0, 'F', '0', '0', 'system:card:edit',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('卡密删除', @parentId, '4',  '#', '', 1, 0, 'F', '0', '0', 'system:card:remove',       '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('卡密导出', @parentId, '5',  '#', '', 1, 0, 'F', '0', '0', 'system:card:export',       '#', 'admin', sysdate(), '', null, '');