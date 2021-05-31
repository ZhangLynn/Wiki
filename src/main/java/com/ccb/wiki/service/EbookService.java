package com.ccb.wiki.service;

import com.ccb.wiki.domain.Ebook;
import com.ccb.wiki.domain.EbookExample;
import com.ccb.wiki.mapper.EbookMapper;
import com.ccb.wiki.req.EbookQueryReq;
import com.ccb.wiki.resp.EbookQueryResp;
import com.ccb.wiki.resp.PageResp;
import com.ccb.wiki.util.CopyUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class EbookService {

//    private static final Logger LOG = LoggerFactory.getLogger();

    @Resource
    private EbookMapper ebookMapper;

    public PageResp<EbookQueryResp> list(EbookQueryReq req) {
        EbookExample ebookExample = new EbookExample();
        EbookExample.Criteria criteria = ebookExample.createCriteria();
        if (!ObjectUtils.isEmpty(req.getName())) {
            criteria.andNameLike("%" + req.getName() + "%");
        }

        PageHelper.startPage(req.getPage(), req.getSize());
        List<Ebook> ebookList = ebookMapper.selectByExample(ebookExample);

        PageInfo<Ebook> pageInfo = new PageInfo<>(ebookList);
        List<EbookQueryResp> ebookQueryRespList = CopyUtil.copyList(ebookList, EbookQueryResp.class);
        PageResp<EbookQueryResp> pageResp = new PageResp<>();
        pageResp.setList(ebookQueryRespList);
        pageResp.setTotal(pageInfo.getTotal());
        return pageResp;
    }
}
