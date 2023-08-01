package com.avg.lawsuitmanagement.promotion.repository;

import com.avg.lawsuitmanagement.promotion.dto.PromotionKeyDto;
import com.avg.lawsuitmanagement.promotion.repository.param.InsertPromotionKeyParam;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PromotionMapperRepository {

    void insertPromotionKey(InsertPromotionKeyParam param);
    PromotionKeyDto selectPromotionKeyByValue(String value);
}
