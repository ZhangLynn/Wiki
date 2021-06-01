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
import java.util.Arrays;
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

    @DeleteMapping("/delete/{idsStr}")
    public CommonResp delete(@PathVariable String idsStr) {
        CommonResp resp = new CommonResp<>();
        // 字符串变成数组然后变成List<String>
        List<String> list = Arrays.asList(idsStr.split(","));
        docService.delete(list);
        return resp;
    }

    @GetMapping("/find-content/{id}")
    public CommonResp<String> findContent(@PathVariable Long id) {
        CommonResp<String> resp = new CommonResp<>();
        String content = docService.findContent(id);
        resp.setContent(content);
        return resp;
    }

}
