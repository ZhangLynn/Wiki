package com.ccb.wiki.service;

import com.ccb.wiki.domain.Category;
import com.ccb.wiki.domain.CategoryExample;
import com.ccb.wiki.mapper.CategoryMapper;
import com.ccb.wiki.req.CategoryQueryReq;
import com.ccb.wiki.req.CategorySaveReq;
import com.ccb.wiki.resp.CategoryQueryResp;
import com.ccb.wiki.resp.PageResp;
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
public class CategoryService {

    private static final Logger LOG = LoggerFactory.getLogger(CategoryService.class);

    @Resource
    private SnowFlake snowFlake;

    @Resource
    private CategoryMapper categoryMapper;

    public List<CategoryQueryResp> all() {
        CategoryExample categoryExample = new CategoryExample();
        CategoryExample.Criteria criteria = categoryExample.createCriteria();
        List<Category> categoryList = categoryMapper.selectByExample(categoryExample);

        List<CategoryQueryResp> categoryQueryResps = CopyUtil.copyList(categoryList, CategoryQueryResp.class);

        return categoryQueryResps;
    }

    public PageResp<CategoryQueryResp> list(CategoryQueryReq req) {
        CategoryExample categoryExample = new CategoryExample();
        CategoryExample.Criteria criteria = categoryExample.createCriteria();
        if (!ObjectUtils.isEmpty(req.getName())) {
            criteria.andNameLike("%" + req.getName() + "%");
        }

        PageHelper.startPage(req.getPage(), req.getSize());
        List<Category> categoryList = categoryMapper.selectByExample(categoryExample);

        PageInfo<Category> pageInfo = new PageInfo<>(categoryList);
        List<CategoryQueryResp> categoryQueryRespList = CopyUtil.copyList(categoryList, CategoryQueryResp.class);
        PageResp<CategoryQueryResp> pageResp = new PageResp<>();
        pageResp.setList(categoryQueryRespList);
        pageResp.setTotal(pageInfo.getTotal());
        return pageResp;
    }

    public void save(CategorySaveReq req) {
        Category category = CopyUtil.copy(req, Category.class);
        if (ObjectUtils.isEmpty(req.getId())) {
            // 新增
            category.setId(snowFlake.nextId());
            categoryMapper.insert(category);
        } else {
            // 更新
            categoryMapper.updateByPrimaryKey(category);
        }
    }

    public void delete(Long id) {
        categoryMapper.deleteByPrimaryKey(id);
    }

}
