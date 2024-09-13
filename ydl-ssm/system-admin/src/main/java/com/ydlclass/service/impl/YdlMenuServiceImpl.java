package com.ydlclass.service.impl;

import com.ydlclass.entity.YdlMenu;
import com.ydlclass.dao.YdlMenuDao;
import com.ydlclass.service.YdlMenuService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;

/**
 * 菜单权限表(YdlMenu)表服务实现类
 *
 * @author makejava
 * @since 2022-02-24 15:09:33
 */
@Service("ydlMenuService")
public class YdlMenuServiceImpl implements YdlMenuService {
    @Resource
    private YdlMenuDao ydlMenuDao;

    /**
     * 通过ID查询单条数据
     *
     * @param menuId 主键
     * @return 实例对象
     */
    @Override
    public YdlMenu queryById(Long menuId) {
        return this.ydlMenuDao.queryById(menuId);
    }

    /**
     * 分页查询
     *
     * @param ydlMenu 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @Override
    public Page<YdlMenu> queryByPage(YdlMenu ydlMenu, PageRequest pageRequest) {
        long total = this.ydlMenuDao.count(ydlMenu);
        return new PageImpl<>(this.ydlMenuDao.queryAllByLimit(ydlMenu, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param ydlMenu 实例对象
     * @return 实例对象
     */
    @Override
    public YdlMenu insert(YdlMenu ydlMenu) {
        this.ydlMenuDao.insert(ydlMenu);
        return ydlMenu;
    }

    /**
     * 修改数据
     *
     * @param ydlMenu 实例对象
     * @return 实例对象
     */
    @Override
    public YdlMenu update(YdlMenu ydlMenu) {
        this.ydlMenuDao.update(ydlMenu);
        return this.queryById(ydlMenu.getMenuId());
    }

    /**
     * 通过主键删除数据
     *
     * @param menuId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long menuId) {
        return this.ydlMenuDao.deleteById(menuId) > 0;
    }
}
