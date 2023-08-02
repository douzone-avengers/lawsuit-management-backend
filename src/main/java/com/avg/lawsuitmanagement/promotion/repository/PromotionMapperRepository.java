package com.avg.lawsuitmanagement.promotion.repository;

import com.avg.lawsuitmanagement.promotion.dto.ClientPromotionKeyDto;
import com.avg.lawsuitmanagement.promotion.repository.param.InsertPromotionKeyParam;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PromotionMapperRepository {

    void insertPromotionKey(InsertPromotionKeyParam param);
    ClientPromotionKeyDto selectPromotionKeyByValue(String value);
}
