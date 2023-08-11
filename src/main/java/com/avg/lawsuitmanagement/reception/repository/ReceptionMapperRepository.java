package com.avg.lawsuitmanagement.reception.repository;

import com.avg.lawsuitmanagement.reception.dto.ReceptionDto;
import com.avg.lawsuitmanagement.reception.repository.param.ReceptionInsertParam;
import com.avg.lawsuitmanagement.reception.repository.param.ReceptionSelectParam;
import com.avg.lawsuitmanagement.reception.repository.param.ReceptionUpdateParam;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReceptionMapperRepository {

    List<ReceptionDto> select(ReceptionSelectParam param);

    Long searchCount(ReceptionSelectParam param);

    ReceptionDto selectById(Long id);

    void insert(ReceptionInsertParam param);

    void update(ReceptionUpdateParam param);

    void delete(Long id);

    void truncate();

    Long count();

}
