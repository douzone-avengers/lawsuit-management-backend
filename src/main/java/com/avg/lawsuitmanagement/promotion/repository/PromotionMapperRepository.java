package com.avg.lawsuitmanagement.promotion.repository;

import com.avg.lawsuitmanagement.promotion.dto.PromotionKeyDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PromotionMapperRepository {

    void insertPromotionKey(String value);
    PromotionKeyDto selectPromotionKeyByValue(String value);
}
