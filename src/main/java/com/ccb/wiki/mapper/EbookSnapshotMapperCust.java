package com.ccb.wiki.mapper;


import com.ccb.wiki.resp.StatisticResp;

import java.util.List;

public interface EbookSnapshotMapperCust {

    int genSnapshot();

    List<StatisticResp> getStatistic();

    List<StatisticResp> get30Statistic();
}
