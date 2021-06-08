package com.ccb.wiki.service;

import com.ccb.wiki.mapper.EbookSnapshotMapperCust;
import com.ccb.wiki.resp.StatisticResp;
import com.ccb.wiki.util.SnowFlake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class EbookSnapshotService {

    private static final Logger LOG = LoggerFactory.getLogger(EbookSnapshotService.class);

    @Resource
    private SnowFlake snowFlake;

    @Resource
    private EbookSnapshotMapperCust ebookSnapshotMapperCust;

    public void genSnapshot() {
        ebookSnapshotMapperCust.genSnapshot();
    }

    public List<StatisticResp> getStatistic() {
        return ebookSnapshotMapperCust.getStatistic();
    }

    public List<StatisticResp> get30Statistic() {
        return ebookSnapshotMapperCust.get30Statistic();
    }

}
