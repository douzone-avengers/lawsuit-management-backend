package com.avg.lawsuitmanagement.reception.repository;

import com.avg.lawsuitmanagement.reception.dto.ReceptionDto;
import com.avg.lawsuitmanagement.reception.repository.param.DeleteReceptionParam;
import com.avg.lawsuitmanagement.reception.repository.param.InsertReceptionParam;
import com.avg.lawsuitmanagement.reception.repository.param.SelectReceptionParam;
import com.avg.lawsuitmanagement.reception.repository.param.UpdateReceptionParam;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReceptionMapperRepository {

    List<ReceptionDto> select(SelectReceptionParam param);

    void insert(InsertReceptionParam param);

    void update(UpdateReceptionParam param);

    void delete(DeleteReceptionParam param);

}
