package com.ccb.wiki.controller;

import com.ccb.wiki.req.DocQueryReq;
import com.ccb.wiki.req.DocSaveReq;
import com.ccb.wiki.resp.CommonResp;
import com.ccb.wiki.resp.DocQueryResp;
import com.ccb.wiki.resp.PageResp;
import com.ccb.wiki.service.DocService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/doc")
public class DocController {
    @Resource
    DocService docService;

    private static final Logger LOG = LoggerFactory.getLogger(DocController.class);

    @GetMapping("/all/{ebookId}")
    public CommonResp all(@PathVariable long ebookId) {
        CommonResp<List<DocQueryResp>> resp = new CommonResp();
        List<DocQueryResp> list = docService.all(ebookId);
        resp.setContent(list);
        return resp;
    }

    @GetMapping("/list")
    public CommonResp list(@Valid DocQueryReq req) {
        CommonResp<PageResp<DocQueryResp>> resp = new CommonResp();
        PageResp<DocQueryResp> list = docService.list(req);
        resp.setContent(list);
        return resp;
    }

    @PostMapping("/save")
    public CommonResp list(@Valid @RequestBody DocSaveReq req) {
        CommonResp resp = new CommonResp();
        docService.save(req);
        return resp;
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable Long id) {
        CommonResp resp = new CommonResp<>();
        docService.delete(id);
        return resp;
    }

}
