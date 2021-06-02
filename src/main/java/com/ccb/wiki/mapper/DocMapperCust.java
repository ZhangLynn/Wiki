package com.ccb.wiki.mapper;


public interface DocMapperCust {

    int increaseViewCount(Long id);

    int increaseVoteCount(Long id);

    int updateEbookInfo();
}
