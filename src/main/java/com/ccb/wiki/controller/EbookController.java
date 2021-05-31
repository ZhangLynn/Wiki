package com.ccb.wiki.controller;

import com.ccb.wiki.req.EbookQueryReq;
import com.ccb.wiki.resp.CommonResp;
import com.ccb.wiki.resp.EbookQueryResp;
import com.ccb.wiki.resp.PageResp;
import com.ccb.wiki.service.EbookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@RequestMapping("/ebook")
public class EbookController {
    @Resource
    EbookService ebookService;

    private static final Logger LOG = LoggerFactory.getLogger(EbookController.class);

    @GetMapping("/list")
    public CommonResp list(@Valid EbookQueryReq req) {
        CommonResp<PageResp<EbookQueryResp>> resp = new CommonResp();
        PageResp<EbookQueryResp> list = ebookService.list(req);
        resp.setContent(list);
        return resp;
    }

}
