package com.ccb.wiki.controller;

import com.alibaba.fastjson.JSONObject;
import com.ccb.wiki.req.UserLoginReq;
import com.ccb.wiki.req.UserQueryReq;
import com.ccb.wiki.req.UserResetPwdReq;
import com.ccb.wiki.req.UserSaveReq;
import com.ccb.wiki.resp.CommonResp;
import com.ccb.wiki.resp.PageResp;
import com.ccb.wiki.resp.UserLoginResp;
import com.ccb.wiki.resp.UserQueryResp;
import com.ccb.wiki.service.UserService;
import com.ccb.wiki.util.SnowFlake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    UserService userService;

    @Resource
    SnowFlake snowFlake;

    @Resource
    RedisTemplate redisTemplate;

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/all")
    public CommonResp all() {
        CommonResp<List<UserQueryResp>> resp = new CommonResp();
        List<UserQueryResp> list = userService.all();
        resp.setContent(list);
        return resp;
    }

    @GetMapping("/list")
    public CommonResp list(@Valid UserQueryReq req) {
        CommonResp<PageResp<UserQueryResp>> resp = new CommonResp();
        PageResp<UserQueryResp> list = userService.list(req);
        resp.setContent(list);
        return resp;
    }

    @PostMapping("/save")
    public CommonResp list(@Valid @RequestBody UserSaveReq req) {
        req.setPassword(DigestUtils.md5DigestAsHex(req.getPassword().getBytes()));
        CommonResp resp = new CommonResp();
        userService.save(req);
        return resp;
    }

    @PostMapping("/login")
    public CommonResp login(@Valid @RequestBody UserLoginReq req) {
        req.setPassword(DigestUtils.md5DigestAsHex(req.getPassword().getBytes()));
        CommonResp<UserLoginResp> resp = new CommonResp();
        UserLoginResp userLoginResp = userService.login(req);
        Long token = snowFlake.nextId();
        LOG.info("生成单点登录token：{}，并放入redis中", token);
        userLoginResp.setToken(token.toString());
        // 传入的user信息序列化 才能在Redis中传递
        redisTemplate.opsForValue().set(token.toString(), JSONObject.toJSONString(userLoginResp), 3600 * 24, TimeUnit.SECONDS);

        resp.setContent(userLoginResp);
        return resp;
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable Long id) {
        CommonResp resp = new CommonResp<>();
        userService.delete(id);
        return resp;
    }

    @PostMapping("/reset-password")
    public CommonResp list(@Valid @RequestBody UserResetPwdReq req) {
        req.setPassword(DigestUtils.md5DigestAsHex(req.getPassword().getBytes()));
        CommonResp resp = new CommonResp();
        userService.resetPwd(req);
        return resp;
    }

    @GetMapping("/logout/{token}")
    public CommonResp logout(@PathVariable String token) {
        CommonResp resp = new CommonResp<>();
        redisTemplate.delete(token);
        LOG.info("从redis中删除token: {}", token);
        return resp;
    }

}
