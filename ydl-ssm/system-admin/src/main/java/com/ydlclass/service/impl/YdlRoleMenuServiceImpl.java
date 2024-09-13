package com.ydlclass.service.impl;

import com.ydlclass.entity.YdlRoleMenu;
import com.ydlclass.dao.YdlRoleMenuDao;
import com.ydlclass.service.YdlRoleMenuService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;

/**
 * 角色和菜单关联表(YdlRoleMenu)表服务实现类
 *
 * @author makejava
 * @since 2022-02-24 15:09:34
 */
@Service("ydlRoleMenuService")
public class YdlRoleMenuServiceImpl implements YdlRoleMenuService {
    @Resource
    private YdlRoleMenuDao ydlRoleMenuDao;

    /**
     * 通过ID查询单条数据
     *
     * @param roleId 主键
     * @return 实例对象
     */
    @Override
    public YdlRoleMenu queryById(Long roleId) {
        return this.ydlRoleMenuDao.queryById(roleId);
    }

    /**
     * 分页查询
     *
     * @param ydlRoleMenu 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @Override
    public Page<YdlRoleMenu> queryByPage(YdlRoleMenu ydlRoleMenu, PageRequest pageRequest) {
        long total = this.ydlRoleMenuDao.count(ydlRoleMenu);
        return new PageImpl<>(this.ydlRoleMenuDao.queryAllByLimit(ydlRoleMenu, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param ydlRoleMenu 实例对象
     * @return 实例对象
     */
    @Override
    public YdlRoleMenu insert(YdlRoleMenu ydlRoleMenu) {
        this.ydlRoleMenuDao.insert(ydlRoleMenu);
        return ydlRoleMenu;
    }

    /**
     * 修改数据
     *
     * @param ydlRoleMenu 实例对象
     * @return 实例对象
     */
    @Override
    public YdlRoleMenu update(YdlRoleMenu ydlRoleMenu) {
        this.ydlRoleMenuDao.update(ydlRoleMenu);
        return this.queryById(ydlRoleMenu.getRoleId());
    }

    /**
     * 通过主键删除数据
     *
     * @param roleId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long roleId) {
        return this.ydlRoleMenuDao.deleteById(roleId) > 0;
    }
}
