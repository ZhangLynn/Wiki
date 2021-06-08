package com.ccb.wiki.controller;

import com.ccb.wiki.resp.CommonResp;
import com.ccb.wiki.resp.StatisticResp;
import com.ccb.wiki.service.EbookSnapshotService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/ebook-snapshot")
public class EbookSnapshotController {
    @Resource
    EbookSnapshotService ebookSnapshotService;

    private static final Logger LOG = LoggerFactory.getLogger(EbookSnapshotController.class);

    @GetMapping("/get-statistic")
    public CommonResp getStatistic() {
        CommonResp<List<StatisticResp>> resp = new CommonResp();
        List<StatisticResp> data = ebookSnapshotService.getStatistic();
        resp.setContent(data);
        return resp;
    }

    @GetMapping("/get-30-statistic")
    public CommonResp get30Statistic() {
        CommonResp<List<StatisticResp>> resp = new CommonResp();
        List<StatisticResp> data = ebookSnapshotService.get30Statistic();
        resp.setContent(data);
        return resp;
    }

}
