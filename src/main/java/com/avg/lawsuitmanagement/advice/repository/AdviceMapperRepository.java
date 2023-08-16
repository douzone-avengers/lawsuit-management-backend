package com.avg.lawsuitmanagement.advice.repository;

import com.avg.lawsuitmanagement.advice.dto.AdviceDto;
import com.avg.lawsuitmanagement.advice.repository.param.*;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdviceMapperRepository {

    AdviceDto selectAdviceById(long adviceId);

    void insertAdvice(InsertAdviceParam param);
    void insertAdviceClientMap(InsertAdviceClientIdParam param);
    void insertAdviceMemberMap(InsertAdviceMemberIdParam param);
    Long getLastInsertedAdviceId();
    void updateAdviceInfo(UpdateAdviceInfoParam param);
    void deleteAdviceInfo(long adviceId);
}
