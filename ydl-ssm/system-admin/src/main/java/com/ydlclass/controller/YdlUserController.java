package com.ydlclass.controller;

import com.ydlclass.annotation.HasPermission;
import com.ydlclass.annotation.HasRole;
import com.ydlclass.annotation.Log;
import com.ydlclass.annotation.Repeat;
import com.ydlclass.entity.YdlUser;
import com.ydlclass.service.YdlUserService;
import com.ydlclass.ydlEnum.DeleteFlagEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 用户信息表(YdlUser)表控制层
 *
 * @author makejava
 * @since 2022-02-24 15:09:34
 */
@RestController
@RequestMapping("ydlUser")
public class YdlUserController extends BaseController {
    /**
     * 服务对象
     */
    @Resource
    private YdlUserService ydlUserService;

    /**
     * 分页查询
     *
     * @param ydlUser 筛选条件
     * @return 查询结果
     */
    @GetMapping
    @Log(title="查询用户", businessType="用户操作")
    public ResponseEntity<Page<YdlUser>> queryByPage(YdlUser ydlUser) {
        System.out.println(ydlUser.getUserName()+">　＞");
        return ResponseEntity.ok(this.ydlUserService.queryByPage(ydlUser,PageRequest.of(ydlUser.getPage(), ydlUser.getSize())));
    }


    @GetMapping("getInfo")
    public ResponseEntity<HashMap<String, List<String>>> getInfo() {
        return ResponseEntity.ok(this.ydlUserService.getInfo());
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    @HasPermission("system:user:query")
    public ResponseEntity<YdlUser> queryById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(this.ydlUserService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param ydlUser 实体
     * @return 新增结果
     */
    @PostMapping
    @HasRole({"admin","hr"})
    @Log(title="创建用户", businessType="用户操作")
    @Repeat(5)
    public ResponseEntity<YdlUser> add(@RequestBody YdlUser ydlUser, HttpServletRequest request) {
        // 要不要进行数据的校验
        ydlUser.setLoginIp(request.getRemoteHost());
        ydlUser.setCreateTime(new Date());
        ydlUser.setCreateBy(getLoginUser().getYdlUser().getUserName());
        ydlUser.setStatus("0");
        ydlUser.setDelFlag(DeleteFlagEnum.NO.getValue());
        return ResponseEntity.ok(this.ydlUserService.insert(ydlUser));
    }

    /**
     * 编辑数据
     *
     * @param ydlUser 实体
     * @return 编辑结果
     */
    @PutMapping
    public ResponseEntity<YdlUser> edit(@RequestBody YdlUser ydlUser) {
        return ResponseEntity.ok(this.ydlUserService.update(ydlUser));
    }

    /**
     * 删除数据
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping("{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable Long id) {
        return ResponseEntity.ok(this.ydlUserService.deleteById(id));
    }

}

