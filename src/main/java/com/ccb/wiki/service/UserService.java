package com.ccb.wiki.service;

import com.ccb.wiki.domain.User;
import com.ccb.wiki.domain.UserExample;
import com.ccb.wiki.mapper.UserMapper;
import com.ccb.wiki.req.UserQueryReq;
import com.ccb.wiki.req.UserSaveReq;
import com.ccb.wiki.resp.PageResp;
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
            // 新增
            user.setId(snowFlake.nextId());
            userMapper.insert(user);
        } else {
            // 更新
            userMapper.updateByPrimaryKey(user);
        }
    }

    public void delete(Long id) {
        userMapper.deleteByPrimaryKey(id);
    }

}
