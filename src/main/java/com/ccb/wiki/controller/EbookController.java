package com.ccb.wiki.controller;

import com.ccb.wiki.domain.Ebook;
import com.ccb.wiki.resp.CommonResp;
import com.ccb.wiki.service.EbookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/ebook")
public class EbookController {
    @Resource
    EbookService ebookService;

    private static final Logger LOG = LoggerFactory.getLogger(EbookController.class);

    @GetMapping("/list")
    public CommonResp list() {
        CommonResp<List<Ebook>> resp = new CommonResp();
        List<Ebook> list = ebookService.list();
        resp.setContent(list);
        return resp;
    }

}
