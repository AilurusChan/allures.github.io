package com.ydlclass.dao;

import com.ydlclass.entity.YdlMenu;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * 菜单权限表(YdlMenu)表数据库访问层
 *
 * @author makejava
 * @since 2022-02-24 15:09:31
 */
public interface YdlMenuDao {

    /**
     * 通过ID查询单条数据
     *
     * @param menuId 主键
     * @return 实例对象
     */
    YdlMenu queryById(Long menuId);

    /**
     * 查询指定行数据
     *
     * @param ydlMenu 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<YdlMenu> queryAllByLimit(YdlMenu ydlMenu, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param ydlMenu 查询条件
     * @return 总行数
     */
    long count(YdlMenu ydlMenu);

    /**
     * 新增数据
     *
     * @param ydlMenu 实例对象
     * @return 影响行数
     */
    int insert(YdlMenu ydlMenu);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<YdlMenu> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<YdlMenu> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<YdlMenu> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<YdlMenu> entities);

    /**
     * 修改数据
     *
     * @param ydlMenu 实例对象
     * @return 影响行数
     */
    int update(YdlMenu ydlMenu);

    /**
     * 通过主键删除数据
     *
     * @param menuId 主键
     * @return 影响行数
     */
    int deleteById(Long menuId);

}

