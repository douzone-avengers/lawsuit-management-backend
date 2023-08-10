package com.avg.lawsuitmanagement.advice.repository;

import com.avg.lawsuitmanagement.advice.dto.AdviceDto;
import com.avg.lawsuitmanagement.advice.repository.param.InsertAdviceClientMemberIdParam;
import com.avg.lawsuitmanagement.advice.repository.param.InsertAdviceParam;
import com.avg.lawsuitmanagement.advice.repository.param.UpdateAdviceInfoParam;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdviceMapperRepository {

    AdviceDto selectAdviceById(long adviceId);

    void insertAdvice(InsertAdviceParam param);
    void insertAdviceClientMap(InsertAdviceClientMemberIdParam param);
    void insertAdviceMemberMap(InsertAdviceClientMemberIdParam param);
    Long getLastInsertedAdviceId();
    void updateAdviceInfo(UpdateAdviceInfoParam param);
    void deleteAdviceInfo(long adviceId);
}
