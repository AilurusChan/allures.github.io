package com.ydlclass.service.impl;

import com.ydlclass.entity.YdlUserRole;
import com.ydlclass.dao.YdlUserRoleDao;
import com.ydlclass.service.YdlUserRoleService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;

/**
 * 用户和角色关联表(YdlUserRole)表服务实现类
 *
 * @author makejava
 * @since 2022-02-24 15:09:34
 */
@Service("ydlUserRoleService")
public class YdlUserRoleServiceImpl implements YdlUserRoleService {
    @Resource
    private YdlUserRoleDao ydlUserRoleDao;

    /**
     * 通过ID查询单条数据
     *
     * @param userId 主键
     * @return 实例对象
     */
    @Override
    public YdlUserRole queryById(Long userId) {
        return this.ydlUserRoleDao.queryById(userId);
    }

    /**
     * 分页查询
     *
     * @param ydlUserRole 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @Override
    public Page<YdlUserRole> queryByPage(YdlUserRole ydlUserRole, PageRequest pageRequest) {
        long total = this.ydlUserRoleDao.count(ydlUserRole);
        return new PageImpl<>(this.ydlUserRoleDao.queryAllByLimit(ydlUserRole, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param ydlUserRole 实例对象
     * @return 实例对象
     */
    @Override
    public YdlUserRole insert(YdlUserRole ydlUserRole) {
        this.ydlUserRoleDao.insert(ydlUserRole);
        return ydlUserRole;
    }

    /**
     * 修改数据
     *
     * @param ydlUserRole 实例对象
     * @return 实例对象
     */
    @Override
    public YdlUserRole update(YdlUserRole ydlUserRole) {
        this.ydlUserRoleDao.update(ydlUserRole);
        return this.queryById(ydlUserRole.getUserId());
    }

    /**
     * 通过主键删除数据
     *
     * @param userId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long userId) {
        return this.ydlUserRoleDao.deleteById(userId) > 0;
    }
}
