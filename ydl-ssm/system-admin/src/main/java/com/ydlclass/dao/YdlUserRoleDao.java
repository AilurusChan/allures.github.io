package com.ydlclass.dao;

import com.ydlclass.entity.YdlUserRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * 用户和角色关联表(YdlUserRole)表数据库访问层
 *
 * @author makejava
 * @since 2022-02-24 15:09:34
 */
public interface YdlUserRoleDao {

    /**
     * 通过ID查询单条数据
     *
     * @param userId 主键
     * @return 实例对象
     */
    YdlUserRole queryById(Long userId);

    /**
     * 查询指定行数据
     *
     * @param ydlUserRole 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<YdlUserRole> queryAllByLimit(YdlUserRole ydlUserRole, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param ydlUserRole 查询条件
     * @return 总行数
     */
    long count(YdlUserRole ydlUserRole);

    /**
     * 新增数据
     *
     * @param ydlUserRole 实例对象
     * @return 影响行数
     */
    int insert(YdlUserRole ydlUserRole);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<YdlUserRole> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<YdlUserRole> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<YdlUserRole> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<YdlUserRole> entities);

    /**
     * 修改数据
     *
     * @param ydlUserRole 实例对象
     * @return 影响行数
     */
    int update(YdlUserRole ydlUserRole);

    /**
     * 通过主键删除数据
     *
     * @param userId 主键
     * @return 影响行数
     */
    int deleteById(Long userId);

}

