package com.ydlclass.service.impl;

import com.ydlclass.entity.YdlRole;
import com.ydlclass.dao.YdlRoleDao;
import com.ydlclass.service.YdlRoleService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;

/**
 * 角色信息表(YdlRole)表服务实现类
 *
 * @author makejava
 * @since 2022-02-24 15:09:34
 */
@Service("ydlRoleService")
public class YdlRoleServiceImpl implements YdlRoleService {
    @Resource
    private YdlRoleDao ydlRoleDao;

    /**
     * 通过ID查询单条数据
     *
     * @param roleId 主键
     * @return 实例对象
     */
    @Override
    public YdlRole queryById(Long roleId) {
        return this.ydlRoleDao.queryById(roleId);
    }

    /**
     * 分页查询
     *
     * @param ydlRole 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @Override
    public Page<YdlRole> queryByPage(YdlRole ydlRole, PageRequest pageRequest) {
        long total = this.ydlRoleDao.count(ydlRole);
        return new PageImpl<>(this.ydlRoleDao.queryAllByLimit(ydlRole, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param ydlRole 实例对象
     * @return 实例对象
     */
    @Override
    public YdlRole insert(YdlRole ydlRole) {
        this.ydlRoleDao.insert(ydlRole);
        return ydlRole;
    }

    /**
     * 修改数据
     *
     * @param ydlRole 实例对象
     * @return 实例对象
     */
    @Override
    public YdlRole update(YdlRole ydlRole) {
        this.ydlRoleDao.update(ydlRole);
        return this.queryById(ydlRole.getRoleId());
    }

    /**
     * 通过主键删除数据
     *
     * @param roleId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long roleId) {
        return this.ydlRoleDao.deleteById(roleId) > 0;
    }
}
