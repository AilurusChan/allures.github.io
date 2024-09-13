package com.ydlclass.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ydlclass.entity.YdlLoginUser;
import com.ydlclass.entity.YdlUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.HashMap;
import java.util.List;

/**
 * 用户信息表(YdlUser)表服务接口
 *
 * @author makejava
 * @since 2022-02-24 15:09:34
 */
public interface YdlUserService {

    /**
     * 通过ID查询单条数据
     *
     * @param userId 主键
     * @return 实例对象
     */
    YdlUser queryById(Long userId);

    /**
     * 分页查询
     *
     * @param ydlUser 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    Page<YdlUser> queryByPage(YdlUser ydlUser, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param ydlUser 实例对象
     * @return 实例对象
     */
    YdlUser insert(YdlUser ydlUser);

    /**
     * 修改数据
     *
     * @param ydlUser 实例对象
     * @return 实例对象
     */
    YdlUser update(YdlUser ydlUser);

    /**
     * 通过主键删除数据
     *
     * @param userId 主键
     * @return 是否成功
     */
    boolean deleteById(Long userId);

    /**
     * 使用用户名和密码登陆
     * @param userName
     * @param password
     * @return
     */
    YdlLoginUser login(String userName, String password) throws JsonProcessingException;

    /**
     * 退出的方法
     */
    void logout();

    /**
     * 获取用户的所有信息
     * @return
     */
    HashMap<String, List<String>> getInfo();
}
