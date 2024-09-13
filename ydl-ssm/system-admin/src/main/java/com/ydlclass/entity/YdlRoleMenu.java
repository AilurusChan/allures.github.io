package com.ydlclass.entity;

import java.io.Serializable;

/**
 * 角色和菜单关联表(YdlRoleMenu)实体类
 *
 * @author makejava
 * @since 2022-02-24 15:09:34
 */
public class YdlRoleMenu implements Serializable {
    private static final long serialVersionUID = -95607621655738616L;
    /**
     * 角色ID
     */
    private Long roleId;
    /**
     * 菜单ID
     */
    private Long menuId;


    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

}

