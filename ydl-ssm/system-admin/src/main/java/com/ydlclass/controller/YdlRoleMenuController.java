package com.ydlclass.controller;

import com.ydlclass.entity.YdlRoleMenu;
import com.ydlclass.service.YdlRoleMenuService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 角色和菜单关联表(YdlRoleMenu)表控制层
 *
 * @author makejava
 * @since 2022-02-24 15:09:34
 */
@RestController
@RequestMapping("ydlRoleMenu")
public class YdlRoleMenuController {
    /**
     * 服务对象
     */
    @Resource
    private YdlRoleMenuService ydlRoleMenuService;

    /**
     * 分页查询
     *
     * @param ydlRoleMenu 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @GetMapping
    public ResponseEntity<Page<YdlRoleMenu>> queryByPage(YdlRoleMenu ydlRoleMenu, PageRequest pageRequest) {
        return ResponseEntity.ok(this.ydlRoleMenuService.queryByPage(ydlRoleMenu, pageRequest));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity<YdlRoleMenu> queryById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(this.ydlRoleMenuService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param ydlRoleMenu 实体
     * @return 新增结果
     */
    @PostMapping
    public ResponseEntity<YdlRoleMenu> add(YdlRoleMenu ydlRoleMenu) {
        return ResponseEntity.ok(this.ydlRoleMenuService.insert(ydlRoleMenu));
    }

    /**
     * 编辑数据
     *
     * @param ydlRoleMenu 实体
     * @return 编辑结果
     */
    @PutMapping
    public ResponseEntity<YdlRoleMenu> edit(YdlRoleMenu ydlRoleMenu) {
        return ResponseEntity.ok(this.ydlRoleMenuService.update(ydlRoleMenu));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping
    public ResponseEntity<Boolean> deleteById(Long id) {
        return ResponseEntity.ok(this.ydlRoleMenuService.deleteById(id));
    }

}

