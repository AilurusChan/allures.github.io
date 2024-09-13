package com.ydlclass.service;

import com.ydlclass.entity.YdlUserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * 用户和角色关联表(YdlUserRole)表服务接口
 *
 * @author makejava
 * @since 2022-02-24 15:09:34
 */
public interface YdlUserRoleService {

    /**
     * 通过ID查询单条数据
     *
     * @param userId 主键
     * @return 实例对象
     */
    YdlUserRole queryById(Long userId);

    /**
     * 分页查询
     *
     * @param ydlUserRole 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    Page<YdlUserRole> queryByPage(YdlUserRole ydlUserRole, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param ydlUserRole 实例对象
     * @return 实例对象
     */
    YdlUserRole insert(YdlUserRole ydlUserRole);

    /**
     * 修改数据
     *
     * @param ydlUserRole 实例对象
     * @return 实例对象
     */
    YdlUserRole update(YdlUserRole ydlUserRole);

    /**
     * 通过主键删除数据
     *
     * @param userId 主键
     * @return 是否成功
     */
    boolean deleteById(Long userId);

}
