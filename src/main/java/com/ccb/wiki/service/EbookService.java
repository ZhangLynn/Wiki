package com.ccb.wiki.service;

import com.ccb.wiki.domain.Ebook;
import com.ccb.wiki.domain.EbookExample;
import com.ccb.wiki.mapper.EbookMapper;
import com.ccb.wiki.req.EbookQueryReq;
import com.ccb.wiki.resp.EbookQueryResp;
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

    public List<EbookQueryResp> list(EbookQueryReq req) {
        EbookExample ebookExample = new EbookExample();
        EbookExample.Criteria criteria = ebookExample.createCriteria();
        if (!ObjectUtils.isEmpty(req.getName())) {
            criteria.andNameLike("%" + req.getName() + "%");
        }

        PageHelper.startPage(2, 3);
        List<Ebook> ebookList = ebookMapper.selectByExample(ebookExample);
        PageInfo<Ebook> pageInfo = new PageInfo<>(ebookList);
//        LOG.info(pageInfo.getTotal())；
        List<EbookQueryResp> ebookQueryRespList = CopyUtil.copyList(ebookList, EbookQueryResp.class);

        return ebookQueryRespList;
    }
}
