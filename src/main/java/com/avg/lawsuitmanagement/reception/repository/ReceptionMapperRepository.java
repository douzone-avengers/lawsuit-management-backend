package com.avg.lawsuitmanagement.reception.repository;

import com.avg.lawsuitmanagement.reception.dto.ReceptionDto;
import com.avg.lawsuitmanagement.reception.repository.param.ReceptionDeleteParam;
import com.avg.lawsuitmanagement.reception.repository.param.ReceptionInsertParam;
import com.avg.lawsuitmanagement.reception.repository.param.ReceptionSelectParam;
import com.avg.lawsuitmanagement.reception.repository.param.ReceptionUpdateParam;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReceptionMapperRepository {

    List<ReceptionDto> select(ReceptionSelectParam param);

    void insert(ReceptionInsertParam param);

    void update(ReceptionUpdateParam param);

    void delete(ReceptionDeleteParam param);

    void truncate();

    Long count();

}
