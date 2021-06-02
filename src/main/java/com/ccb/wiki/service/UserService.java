package com.ccb.wiki.service;

import com.ccb.wiki.domain.User;
import com.ccb.wiki.domain.UserExample;
import com.ccb.wiki.exception.BusinessException;
import com.ccb.wiki.exception.BusinessExceptionCode;
import com.ccb.wiki.mapper.UserMapper;
import com.ccb.wiki.req.UserLoginReq;
import com.ccb.wiki.req.UserQueryReq;
import com.ccb.wiki.req.UserResetPwdReq;
import com.ccb.wiki.req.UserSaveReq;
import com.ccb.wiki.resp.PageResp;
import com.ccb.wiki.resp.UserLoginResp;
import com.ccb.wiki.resp.UserQueryResp;
import com.ccb.wiki.util.CopyUtil;
import com.ccb.wiki.util.SnowFlake;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    @Resource
    private SnowFlake snowFlake;

    @Resource
    private UserMapper userMapper;

    public List<UserQueryResp> all() {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        List<User> userList = userMapper.selectByExample(userExample);

        List<UserQueryResp> userQueryResps = CopyUtil.copyList(userList, UserQueryResp.class);

        return userQueryResps;
    }

    public PageResp<UserQueryResp> list(UserQueryReq req) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        if (!ObjectUtils.isEmpty(req.getLoginName())) {
            criteria.andLoginNameLike("%" + req.getLoginName() + "%");
        }

        PageHelper.startPage(req.getPage(), req.getSize());
        List<User> userList = userMapper.selectByExample(userExample);

        PageInfo<User> pageInfo = new PageInfo<>(userList);
        List<UserQueryResp> userQueryRespList = CopyUtil.copyList(userList, UserQueryResp.class);
        PageResp<UserQueryResp> pageResp = new PageResp<>();
        pageResp.setList(userQueryRespList);
        pageResp.setTotal(pageInfo.getTotal());
        return pageResp;
    }

    public void save(UserSaveReq req) {
        User user = CopyUtil.copy(req, User.class);
        if (ObjectUtils.isEmpty(req.getId())) {
            if (ObjectUtils.isEmpty(getUserByLoginName(req.getLoginName()))) {
                // 新增
                user.setId(snowFlake.nextId());
                userMapper.insert(user);
            } else {
                // 用户名已存在
                throw new BusinessException(BusinessExceptionCode.USER_LOGIN_NAME_EXIST);
            }

        } else {
            // 更新
            userMapper.updateByPrimaryKey(user);
        }
    }

    public void delete(Long id) {
        userMapper.deleteByPrimaryKey(id);
    }

    public User getUserByLoginName(String loginName) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andLoginNameEqualTo(loginName);
        List<User> users = userMapper.selectByExample(userExample);
        if (ObjectUtils.isEmpty(users)) {
            return null;
        } else {
            return users.get(0);
        }
    }

    public void resetPwd(UserResetPwdReq req) {
        User newUser = CopyUtil.copy(req, User.class);
        userMapper.updateByPrimaryKeySelective(newUser);
    }

    public UserLoginResp login(UserLoginReq req) {
        User userDb = getUserByLoginName(req.getLoginName());
        if (ObjectUtils.isEmpty(userDb)) {
            LOG.info("用户名不存在, {}", req.getLoginName());
            throw new BusinessException(BusinessExceptionCode.LOGIN_USER_ERROR);
        } else {
            if (!req.getPassword().equals(userDb.getPassword())) {
                LOG.info("密码不对, 输入密码：{}, 数据库密码：{}", req.getPassword(), userDb.getPassword());
                throw new BusinessException(BusinessExceptionCode.LOGIN_USER_ERROR);
            } else {
                UserLoginResp userLoginResp = CopyUtil.copy(userDb, UserLoginResp.class);
                return userLoginResp;
            }
        }

    }

}
