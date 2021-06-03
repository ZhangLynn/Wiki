package com.ccb.wiki.controller;

import com.ccb.wiki.service.EbookSnapshotService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/ebook-snapshot")
public class EbookSnapshotController {
    @Resource
    EbookSnapshotService ebookSnapshotService;

    private static final Logger LOG = LoggerFactory.getLogger(EbookSnapshotController.class);

}
