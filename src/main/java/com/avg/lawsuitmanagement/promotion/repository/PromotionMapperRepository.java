package com.avg.lawsuitmanagement.promotion.repository;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PromotionMapperRepository {

    void insertPromotionKey(String value);
}
