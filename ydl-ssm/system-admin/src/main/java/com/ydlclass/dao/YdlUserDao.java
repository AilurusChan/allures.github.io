package com.ydlclass.dao;

import com.ydlclass.entity.YdlUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * 用户信息表(YdlUser)表数据库访问层
 *
 * @author makejava
 * @since 2022-02-24 15:09:34
 */
public interface YdlUserDao {

    /**
     * 通过ID查询单条数据
     *
     * @param userId 主键
     * @return 实例对象
     */
    YdlUser queryById(Long userId);

    /**
     * 查询指定行数据
     *
     * @param ydlUser 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<YdlUser> queryAllByLimit(@Param("ydlUser") YdlUser ydlUser, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param ydlUser 查询条件
     * @return 总行数
     */
    long count(YdlUser ydlUser);

    /**
     * 新增数据
     *
     * @param ydlUser 实例对象
     * @return 影响行数
     */
    int insert(YdlUser ydlUser);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<YdlUser> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<YdlUser> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<YdlUser> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<YdlUser> entities);

    /**
     * 修改数据
     *
     * @param ydlUser 实例对象
     * @return 影响行数
     */
    int update(YdlUser ydlUser);

    /**
     * 通过主键删除数据
     *
     * @param userId 主键
     * @return 影响行数
     */
    int deleteById(Long userId);

    /**
     * 通过用户名查询用户
     * @param userName
     * @return
     */
    YdlUser queryByUserName(String userName);


    /**
     * 通用用户id查新角色和权限等信息
     * @param UserId
     * @return
     */
    YdlUser getInfo(Long UserId);
}

