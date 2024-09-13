package com.ydlclass.controller;

import com.ydlclass.entity.YdlUserRole;
import com.ydlclass.service.YdlUserRoleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 用户和角色关联表(YdlUserRole)表控制层
 *
 * @author makejava
 * @since 2022-02-24 15:09:34
 */
@RestController
@RequestMapping("ydlUserRole")
public class YdlUserRoleController {
    /**
     * 服务对象
     */
    @Resource
    private YdlUserRoleService ydlUserRoleService;

    /**
     * 分页查询
     *
     * @param ydlUserRole 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @GetMapping
    public ResponseEntity<Page<YdlUserRole>> queryByPage(YdlUserRole ydlUserRole, PageRequest pageRequest) {
        return ResponseEntity.ok(this.ydlUserRoleService.queryByPage(ydlUserRole, pageRequest));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity<YdlUserRole> queryById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(this.ydlUserRoleService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param ydlUserRole 实体
     * @return 新增结果
     */
    @PostMapping
    public ResponseEntity<YdlUserRole> add(YdlUserRole ydlUserRole) {
        return ResponseEntity.ok(this.ydlUserRoleService.insert(ydlUserRole));
    }

    /**
     * 编辑数据
     *
     * @param ydlUserRole 实体
     * @return 编辑结果
     */
    @PutMapping
    public ResponseEntity<YdlUserRole> edit(YdlUserRole ydlUserRole) {
        return ResponseEntity.ok(this.ydlUserRoleService.update(ydlUserRole));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping
    public ResponseEntity<Boolean> deleteById(Long id) {
        return ResponseEntity.ok(this.ydlUserRoleService.deleteById(id));
    }

}

