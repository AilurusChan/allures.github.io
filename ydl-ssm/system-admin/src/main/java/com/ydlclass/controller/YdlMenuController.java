package com.ydlclass.controller;

import com.ydlclass.entity.YdlMenu;
import com.ydlclass.service.YdlMenuService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 菜单权限表(YdlMenu)表控制层
 *
 * @author makejava
 * @since 2022-02-24 15:09:31
 */
@RestController
@RequestMapping("ydlMenu")
public class YdlMenuController {
    /**
     * 服务对象
     */
    @Resource
    private YdlMenuService ydlMenuService;

    /**
     * 分页查询
     *
     * @param ydlMenu 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @GetMapping
    public ResponseEntity<Page<YdlMenu>> queryByPage(YdlMenu ydlMenu, PageRequest pageRequest) {
        return ResponseEntity.ok(this.ydlMenuService.queryByPage(ydlMenu, pageRequest));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity<YdlMenu> queryById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(this.ydlMenuService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param ydlMenu 实体
     * @return 新增结果
     */
    @PostMapping
    public ResponseEntity<YdlMenu> add(YdlMenu ydlMenu) {
        return ResponseEntity.ok(this.ydlMenuService.insert(ydlMenu));
    }

    /**
     * 编辑数据
     *
     * @param ydlMenu 实体
     * @return 编辑结果
     */
    @PutMapping
    public ResponseEntity<YdlMenu> edit(YdlMenu ydlMenu) {
        return ResponseEntity.ok(this.ydlMenuService.update(ydlMenu));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping
    public ResponseEntity<Boolean> deleteById(Long id) {
        return ResponseEntity.ok(this.ydlMenuService.deleteById(id));
    }

}

