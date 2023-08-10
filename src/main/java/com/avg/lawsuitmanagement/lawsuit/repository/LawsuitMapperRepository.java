package com.avg.lawsuitmanagement.lawsuit.repository;

import com.avg.lawsuitmanagement.client.repository.param.SelectClientLawsuitListParam;
import com.avg.lawsuitmanagement.lawsuit.dto.LawsuitDto;
import com.avg.lawsuitmanagement.lawsuit.repository.param.InsertLawsuitClientMemberIdParam;
import com.avg.lawsuitmanagement.lawsuit.repository.param.InsertLawsuitParam;
import com.avg.lawsuitmanagement.lawsuit.repository.param.UpdateLawsuitInfoParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LawsuitMapperRepository {
    List<LawsuitDto> selectClientLawsuitList(SelectClientLawsuitListParam param);
    LawsuitDto selectLawsuitById(long lawsuitId);
    void insertLawsuit(InsertLawsuitParam param);
    Long getLastInsertedLawsuitId();
    void insertLawsuitClientMap(InsertLawsuitClientMemberIdParam param);
    void insertLawsuitMemberMap(InsertLawsuitClientMemberIdParam param);
    List<LawsuitDto> selectLawsuitList();
    List<Long> selectClientIdByLawsuitId(long LawsuitId);
    List<Long> selectMemberIdByLawsuitId(long LawsuitId);
    void updateLawsuitInfo(UpdateLawsuitInfoParam param);


}
