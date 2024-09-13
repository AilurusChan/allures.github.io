package com.ydlclass.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;

import java.io.Serializable;

/**
 * 角色和菜单关联表(YdlRoleMenu)实体类
 *
 * @author makejava
 * @since 2022-02-24 15:09:34
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class YdlBaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;


    // 分页使用的字段
    private int page;
    private int size;
    private Sort sort;

}

