package com.avg.lawsuitmanagement.advice.repository;

import com.avg.lawsuitmanagement.advice.dto.AdviceDto;
import com.avg.lawsuitmanagement.advice.repository.param.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdviceMapperRepository {

    AdviceDto selectAdviceById(long adviceId);

    void insertAdvice(InsertAdviceParam param);
    void insertAdviceClientMap(InsertAdviceClientIdParam param);
    void insertAdviceMemberMap(InsertAdviceMemberIdParam param);
    Long getLastInsertedAdviceId();
    void updateAdviceInfo(UpdateAdviceInfoParam param);
    List<Long> selectMemberByAdviceId(long adviceId);

    void deleteAdviceInfo(long adviceId);
    void deleteAdviceClientMap(long adviceId);
    void deleteAdviceMemberMap(long adviceId);
}
