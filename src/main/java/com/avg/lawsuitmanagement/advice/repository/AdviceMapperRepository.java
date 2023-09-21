package com.avg.lawsuitmanagement.advice.repository;

import com.avg.lawsuitmanagement.advice.dto.AdviceDto;
import com.avg.lawsuitmanagement.advice.dto.AdviceRawDto;
import com.avg.lawsuitmanagement.advice.repository.param.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdviceMapperRepository {

    AdviceDto selectAdviceById(long adviceId);

    List<AdviceRawDto> detailAdviceById(long adviceId);



    void insertAdvice(InsertAdviceParam param);
    void insertAdviceClientMap(AdviceClientIdParam param);
    void insertAdviceMemberMap(AdviceMemberIdParam param);
    void updateAdviceMemberMap(AdviceMemberIdParam param);
    void updateAdviceClientMap(AdviceClientIdParam param);

    void deleteAdviceClientMap(DeleteAdviceClientMemberIdParam param);
    void deleteAdviceMemberMap(DeleteAdviceClientMemberIdParam param);
    void AdviceDeleteClientMap(long adviceId);
    void AdviceDeleteMemberMap(long adviceId);

    List<AdviceDto> selectAdviceListByPagingCondition(AdviceListParam param);
    int selectAdviceListByPagingConditionCount(AdviceListParam param);

    Long getLastInsertedAdviceId();
    void updateAdviceInfo(UpdateAdviceInfoParam param);
    List<Long> selectMemberByAdviceId(long adviceId);
    List<Long> selectClientByAdviceId(long adviceId);
    List<AdviceDto> selectAdviceByLawsuitId(long lawsuitId);

    void deleteAdviceInfo(long adviceId);



}
