package com.avg.lawsuitmanagement.advice.repository;

import com.avg.lawsuitmanagement.advice.dto.AdviceDto;
import com.avg.lawsuitmanagement.advice.repository.param.InsertAdviceParam;
import com.avg.lawsuitmanagement.advice.repository.param.UpdateAdviceInfoParam;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdviceMapperRepository {

    AdviceDto selectAdviceById(long adviceId);

    void insertAdvice(InsertAdviceParam param);

    void updateAdvcieInfo(UpdateAdviceInfoParam param);
    void deleteAdviceInfo(long adviceId);
}
