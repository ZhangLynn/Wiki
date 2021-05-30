package com.ccb.wiki.service;

import com.ccb.wiki.domain.Ebook;
import com.ccb.wiki.domain.EbookExample;
import com.ccb.wiki.mapper.EbookMapper;
import com.ccb.wiki.req.EbookQueryReq;
import com.ccb.wiki.resp.EbookQueryResp;
import com.ccb.wiki.util.CopyUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class EbookService {

    @Resource
    private EbookMapper ebookMapper;

    public List<EbookQueryResp> list(EbookQueryReq req) {
        EbookExample ebookExample = new EbookExample();
        EbookExample.Criteria criteria = ebookExample.createCriteria();
        criteria.andNameLike("%" + req.getName() + "%");

        List<Ebook> ebookList = ebookMapper.selectByExample(ebookExample);
        List<EbookQueryResp> ebookQueryRespList = CopyUtil.copyList(ebookList, EbookQueryResp.class);
        return ebookQueryRespList;
    }
}
