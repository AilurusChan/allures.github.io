package com.ydlclass.controller;

import com.ydlclass.entity.YdlRole;
import com.ydlclass.service.YdlRoleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 角色信息表(YdlRole)表控制层
 *
 * @author makejava
 * @since 2022-02-24 15:09:34
 */
@RestController
@RequestMapping("ydlRole")
public class YdlRoleController {
    /**
     * 服务对象
     */
    @Resource
    private YdlRoleService ydlRoleService;

    /**
     * 分页查询
     *
     * @param ydlRole 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @GetMapping
    public ResponseEntity<Page<YdlRole>> queryByPage(YdlRole ydlRole, PageRequest pageRequest) {
        return ResponseEntity.ok(this.ydlRoleService.queryByPage(ydlRole, pageRequest));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity<YdlRole> queryById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(this.ydlRoleService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param ydlRole 实体
     * @return 新增结果
     */
    @PostMapping
    public ResponseEntity<YdlRole> add(YdlRole ydlRole) {
        return ResponseEntity.ok(this.ydlRoleService.insert(ydlRole));
    }

    /**
     * 编辑数据
     *
     * @param ydlRole 实体
     * @return 编辑结果
     */
    @PutMapping
    public ResponseEntity<YdlRole> edit(YdlRole ydlRole) {
        return ResponseEntity.ok(this.ydlRoleService.update(ydlRole));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping
    public ResponseEntity<Boolean> deleteById(Long id) {
        return ResponseEntity.ok(this.ydlRoleService.deleteById(id));
    }

}

