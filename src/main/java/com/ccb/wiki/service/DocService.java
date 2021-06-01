package com.ccb.wiki.service;

import com.ccb.wiki.domain.Doc;
import com.ccb.wiki.domain.DocExample;
import com.ccb.wiki.mapper.DocMapper;
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

    public void save(DocSaveReq req) {
        Doc doc = CopyUtil.copy(req, Doc.class);
        if (ObjectUtils.isEmpty(req.getId())) {
            // 新增
            doc.setId(snowFlake.nextId());
            docMapper.insert(doc);
        } else {
            // 更新
            docMapper.updateByPrimaryKey(doc);
        }
    }

    public void delete(Long id) {
        docMapper.deleteByPrimaryKey(id);
    }

}
