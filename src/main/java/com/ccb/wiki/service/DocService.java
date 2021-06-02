package com.ccb.wiki.service;

import com.ccb.wiki.domain.Content;
import com.ccb.wiki.domain.Doc;
import com.ccb.wiki.domain.DocExample;
import com.ccb.wiki.mapper.ContentMapper;
import com.ccb.wiki.mapper.DocMapper;
import com.ccb.wiki.mapper.DocMapperCust;
import com.ccb.wiki.req.DocQueryReq;
import com.ccb.wiki.req.DocSaveReq;
import com.ccb.wiki.resp.DocQueryResp;
import com.ccb.wiki.resp.PageResp;
import com.ccb.wiki.util.CopyUtil;
import com.ccb.wiki.util.SnowFlake;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DocService {

    private static final Logger LOG = LoggerFactory.getLogger(DocService.class);

    @Resource
    private SnowFlake snowFlake;

    @Resource
    private DocMapper docMapper;

    @Resource
    private DocMapperCust docMapperCust;

    @Resource
    private ContentMapper contentMapper;

    public List<DocQueryResp> all(long ebookId) {
        DocExample docExample = new DocExample();
        docExample.createCriteria().andEbookIdEqualTo(ebookId);
        docExample.setOrderByClause("sort asc");
        List<Doc> docList = docMapper.selectByExample(docExample);

        return CopyUtil.copyList(docList, DocQueryResp.class);
    }

    public PageResp<DocQueryResp> list(DocQueryReq req) {
        DocExample docExample = new DocExample();
        DocExample.Criteria criteria = docExample.createCriteria();

        PageHelper.startPage(req.getPage(), req.getSize());
        List<Doc> docList = docMapper.selectByExample(docExample);

        PageInfo<Doc> pageInfo = new PageInfo<>(docList);
        List<DocQueryResp> docQueryRespList = CopyUtil.copyList(docList, DocQueryResp.class);
        PageResp<DocQueryResp> pageResp = new PageResp<>();
        pageResp.setList(docQueryRespList);
        pageResp.setTotal(pageInfo.getTotal());
        return pageResp;
    }

    // 事务 保证两张表可以同时更新
    @Transactional
    public void save(DocSaveReq req) {
        Doc doc = CopyUtil.copy(req, Doc.class);
        Content content = CopyUtil.copy(req, Content.class);
        if (ObjectUtils.isEmpty(req.getId())) {
            // 新增
            doc.setId(snowFlake.nextId());
            doc.setViewCount(0);
            doc.setVoteCount(0);
            docMapper.insert(doc);

            content.setId(doc.getId());
            contentMapper.insert(content);
        } else {
            // 更新
            docMapper.updateByPrimaryKey(doc);
            int count = contentMapper.updateByPrimaryKeyWithBLOBs(content);
            if (count == 0) {
                contentMapper.insert(content);
            }
        }
    }

    public void delete(Long id) {
        docMapper.deleteByPrimaryKey(id);
        contentMapper.deleteByPrimaryKey(id);
    }

    public void delete(List<String> ids) {
        DocExample docExample = new DocExample();
        DocExample.Criteria criteria = docExample.createCriteria();
        criteria.andIdIn(ids);
        int res = docMapper.deleteByExample(docExample);
        LOG.info(String.valueOf(res));
    }

    public void vote(Long id) {
        docMapperCust.increaseVoteCount(id);
    }

    public String findContent(Long id) {
        Content content = contentMapper.selectByPrimaryKey(id);
        docMapperCust.increaseViewCount(id);
        if (!ObjectUtils.isEmpty(content)) {
            return content.getContent();
        }
        return "";
    }

    public void updateEbookInfo() {
        docMapperCust.updateEbookInfo();
    }


}
