package com.ydlclass.service;

import com.ydlclass.entity.YdlRoleMenu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * 角色和菜单关联表(YdlRoleMenu)表服务接口
 *
 * @author makejava
 * @since 2022-02-24 15:09:34
 */
public interface YdlRoleMenuService {

    /**
     * 通过ID查询单条数据
     *
     * @param roleId 主键
     * @return 实例对象
     */
    YdlRoleMenu queryById(Long roleId);

    /**
     * 分页查询
     *
     * @param ydlRoleMenu 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    Page<YdlRoleMenu> queryByPage(YdlRoleMenu ydlRoleMenu, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param ydlRoleMenu 实例对象
     * @return 实例对象
     */
    YdlRoleMenu insert(YdlRoleMenu ydlRoleMenu);

    /**
     * 修改数据
     *
     * @param ydlRoleMenu 实例对象
     * @return 实例对象
     */
    YdlRoleMenu update(YdlRoleMenu ydlRoleMenu);

    /**
     * 通过主键删除数据
     *
     * @param roleId 主键
     * @return 是否成功
     */
    boolean deleteById(Long roleId);

}
