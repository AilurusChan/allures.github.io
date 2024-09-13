package com.ydlclass.service;

import com.ydlclass.entity.YdlMenu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * 菜单权限表(YdlMenu)表服务接口
 *
 * @author makejava
 * @since 2022-02-24 15:09:33
 */
public interface YdlMenuService {

    /**
     * 通过ID查询单条数据
     *
     * @param menuId 主键
     * @return 实例对象
     */
    YdlMenu queryById(Long menuId);

    /**
     * 分页查询
     *
     * @param ydlMenu 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    Page<YdlMenu> queryByPage(YdlMenu ydlMenu, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param ydlMenu 实例对象
     * @return 实例对象
     */
    YdlMenu insert(YdlMenu ydlMenu);

    /**
     * 修改数据
     *
     * @param ydlMenu 实例对象
     * @return 实例对象
     */
    YdlMenu update(YdlMenu ydlMenu);

    /**
     * 通过主键删除数据
     *
     * @param menuId 主键
     * @return 是否成功
     */
    boolean deleteById(Long menuId);

}
