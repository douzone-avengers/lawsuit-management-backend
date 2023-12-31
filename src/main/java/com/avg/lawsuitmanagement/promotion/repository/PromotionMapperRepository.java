package com.avg.lawsuitmanagement.promotion.repository;

import com.avg.lawsuitmanagement.promotion.dto.ClientPromotionKeyDto;
import com.avg.lawsuitmanagement.promotion.dto.EmployeePromotionKeyDto;
import com.avg.lawsuitmanagement.promotion.repository.param.InsertClientPromotionKeyParam;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PromotionMapperRepository {

    void insertClientPromotionKey(InsertClientPromotionKeyParam param);
    ClientPromotionKeyDto selectPromotionKeyByValue(String value);
    void insertEmployeePromotionKey(String value);
    EmployeePromotionKeyDto selectEmployeePromotionKeyByValue(String value);
    void deactivateClientPromotionByValue(String value);
    void deactivateEmployeePromotionByValue(String value);

}
