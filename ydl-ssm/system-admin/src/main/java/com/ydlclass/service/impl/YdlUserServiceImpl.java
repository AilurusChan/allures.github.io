package com.ydlclass.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.ydlclass.configuration.CustomObjectMapper;
import com.ydlclass.constant.Constants;
import com.ydlclass.core.RedisTemplate;
import com.ydlclass.entity.YdlLoginUser;
import com.ydlclass.entity.YdlMenu;
import com.ydlclass.entity.YdlRole;
import com.ydlclass.entity.YdlUser;
import com.ydlclass.dao.YdlUserDao;
import com.ydlclass.exception.PasswordIncorrectException;
import com.ydlclass.exception.UserNotFoundException;
import com.ydlclass.service.YdlUserService;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.apache.bcel.classfile.Constant;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 用户信息表(YdlUser)表服务实现类
 *
 * @author makejava
 * @since 2022-02-24 15:09:34
 */
@Service("ydlUserService")
@Slf4j
public class YdlUserServiceImpl implements YdlUserService {
    @Resource
    private YdlUserDao ydlUserDao;

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private CustomObjectMapper objectMapper;

    @Resource
    private RedisTemplate redisTemplate;

    /**
     * 通过ID查询单条数据
     *
     * @param userId 主键
     * @return 实例对象
     */
    @Override
    public YdlUser queryById(Long userId) {
        return this.ydlUserDao.queryById(userId);
    }

    /**
     * 分页查询
     *
     * @param ydlUser 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @Override
    public Page<YdlUser> queryByPage(YdlUser ydlUser, PageRequest pageRequest) {
        long total = this.ydlUserDao.count(ydlUser);
        return new PageImpl<>(this.ydlUserDao.queryAllByLimit(ydlUser, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param ydlUser 实例对象
     * @return 实例对象
     */
    @Override
    public YdlUser insert(YdlUser ydlUser) {
        // 1、密码是不是要加密

        this.ydlUserDao.insert(ydlUser);
        return ydlUser;
    }

    /**
     * 修改数据
     *
     * @param ydlUser 实例对象
     * @return 实例对象
     */
    @Override
    public YdlUser update(YdlUser ydlUser) {
        this.ydlUserDao.update(ydlUser);
        return this.queryById(ydlUser.getUserId());
    }

    /**
     * 通过主键删除数据
     *
     * @param userId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long userId) {
        return this.ydlUserDao.deleteById(userId) > 0;
    }

    @Override
    public YdlLoginUser login(String userName, String password) throws JsonProcessingException {

        // 1、登陆，使用用户名查询用户，没有查询到，说明没有该账户
        YdlUser ydlUser = ydlUserDao.queryByUserName(userName);
        if(ydlUser == null) throw new UserNotFoundException("执行登陆操作：【"+ userName +"】该用户不存在");

        // 2、如果查到了，比较比较密码，密码如果不正确，登陆失败
        if(!password.equals(ydlUser.getPassword())){
            log.info("执行登陆操作：【"+ userName +"】该用户密码输入错误");
            throw new PasswordIncorrectException("执行登陆操作：【"+ userName +"】该用户密码输入错误");
        }

        // 3、如果验证成功了

        // (1) 生成token
        String token = UUID.randomUUID().toString();

        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        UserAgent userAgent = new UserAgent(request.getHeader("User-Agent"));

        // 通过ip获取其所属的地址
        ResponseEntity<String> result = restTemplate.getForEntity("https://whois.pconline.com.cn/ipJson.jsp?ip="+request.getRemoteHost()+"&json=true", String.class);
        String body = result.getBody();
        Map<String,String> map = objectMapper.readValue(body, new TypeReference<>() {});

        String location = map.get("addr")+map.get("pro")+map.get("city")+map.get("region");

        // (2) 封装一个YdlLoginUser，保存在redis
        YdlLoginUser ydlLoginUser = YdlLoginUser.builder()
                .userId(ydlUser.getUserId())
                .token(token)
                .ipaddr(request.getRemoteAddr())
                .loginTime(new Date())
                .os(userAgent.getOperatingSystem().getName())
                .browser(userAgent.getBrowser().getName())
                .loginLocation(location)
                .ydlUser(ydlUser)
                .build();

        //key进行处理  token:username:uuid

        // 1、根据用户名生成一个key前缀token:username:
        String keyPrefix =  Constants.TOKEN_PREFIX + userName +":";
        // 2、查询token:username:前缀的数据
        Set<String> keys = redisTemplate.keys(keyPrefix + "*");
        // 3、删除原来的数据
        keys.forEach(key -> redisTemplate.remove(key));
        // 4、把新的数据加入redis
        redisTemplate.setObject(keyPrefix + token,ydlLoginUser,Constants.TOKEN_TIME);

        return ydlLoginUser;
    }

    @Override
    public void logout() {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        // 获取首部信息的token
        String token = request.getHeader(Constants.HEAD_AUTHORIZATION);

        // 删除redis中的token user这些数据
        redisTemplate.remove(Constants.TOKEN_PREFIX + token);

    }

    @Override
    public HashMap<String,List<String>> getInfo() {
        // 1、获取当前登陆的对象
        YdlLoginUser loginUser = getLoginUser();

        // 2、查询当前用户的角色和权限
        YdlUser info = ydlUserDao.getInfo(loginUser.getUserId());

        // 3、处理权限和角色的相关信息
        // (1) roles:token : [admin,xxx,yyy]   perms:token: [system:user:add,system:user:update]
        List<String> roleTags = info.getYdlRoles().stream().map(YdlRole::getRoleTag).collect(Collectors.toList());
        redisTemplate.setObject(Constants.ROLE_PREFIX + loginUser.getToken(),roleTags,Constants.TOKEN_TIME);

        List<String> prems = new ArrayList<>();
        // [{roleName:cc,roleTag:xxx,perms:[{id,'xxx',perm:'system'},{id,'xxx',perm:'system'}]},{}]
        // [[{id,'xxx',perm:'system'},{id,'xxx',perm:'system'}],[{id,'xxx',perm:'system'},{id,'xxx',perm:'system'}]]
        // ['system','system:user:add']
        info.getYdlRoles().stream().map(YdlRole::getYdlMenus).forEach(menus -> {
            prems.addAll(menus.stream().map(YdlMenu::getPerms).collect(Collectors.toList()));
        });
        redisTemplate.setObject(Constants.PERM_PREFIX + loginUser.getToken(),prems,Constants.TOKEN_TIME);

        // 整合数据
        HashMap<String,List<String>> data = new HashMap<>();
        data.put("roles",roleTags);
        data.put("perms",prems);

        return data;
    }

    private YdlLoginUser getLoginUser(){

        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        // 获取首部信息的token
        String token = request.getHeader(Constants.HEAD_AUTHORIZATION);

        if (token == null) {
            throw new RuntimeException("当前用户未登录！");
        }
        Set<String> keys = redisTemplate.keys(Constants.TOKEN_PREFIX + "*" + token);
        if (keys== null || keys.size() == 0){
            throw new RuntimeException("当前用户未登录！");
        }
        String tokenKey = (String)keys.toArray()[0];
        // 3、使用token去redis中查看，有没有对应的loginUser
        return redisTemplate.getObject(tokenKey, new TypeReference<>() {});
    }



    public static void main(String[] args) {
        //使用 java 发送http请求
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> forEntity = restTemplate.getForEntity("https://whois.pconline.com.cn/ipJson.jsp?ip=122.255.144.12&json=true", String.class);
        System.out.println(forEntity.getBody());
    }


}
