package com.avg.lawsuitmanagement.lawsuit.repository;

import com.avg.lawsuitmanagement.lawsuit.dto.ClientLawsuitCountDto;
import com.avg.lawsuitmanagement.lawsuit.dto.LawsuitBasicRawDto;
import com.avg.lawsuitmanagement.lawsuit.dto.LawsuitCountDto;
import com.avg.lawsuitmanagement.lawsuit.dto.LawsuitDto;
import com.avg.lawsuitmanagement.lawsuit.dto.LawsuitPrintRawDto;
import com.avg.lawsuitmanagement.lawsuit.repository.param.LawsuitClientMemberIdParam;
import com.avg.lawsuitmanagement.lawsuit.repository.param.InsertLawsuitParam;
import com.avg.lawsuitmanagement.lawsuit.repository.param.LawsuitStatusUpdateParam;
import com.avg.lawsuitmanagement.lawsuit.repository.param.SelectClientLawsuitListParam;
import com.avg.lawsuitmanagement.lawsuit.repository.param.SelectEmployeeLawsuitListParam;
import com.avg.lawsuitmanagement.lawsuit.repository.param.UpdateLawsuitInfoParam;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LawsuitMapperRepository {

    List<LawsuitDto> selectClientLawsuitList(SelectClientLawsuitListParam param);

    LawsuitCountDto countLawsuitsStatusByClientId(SelectClientLawsuitListParam param);

    List<LawsuitDto> selectEmployeeLawsuitList(SelectEmployeeLawsuitListParam param);

    int selectClientLawsuitCountBySearchWordAndLawsuitStatus(SelectClientLawsuitListParam param);

    int selectEmployeeLawsuitCountBySearchWord(SelectEmployeeLawsuitListParam param);

    LawsuitDto selectLawsuitById(long lawsuitId);

    void insertLawsuit(InsertLawsuitParam param);

    Long getLastInsertedLawsuitId();

    void insertLawsuitClientMap(LawsuitClientMemberIdParam param);

    List<Long> selectClientByLawsuitId(long lawsuitId);

    void insertLawsuitMemberMap(LawsuitClientMemberIdParam param);

    List<LawsuitDto> selectLawsuitList();

    void updateLawsuitInfo(UpdateLawsuitInfoParam param);

    void deleteMemberLawsuitMemberMap(LawsuitClientMemberIdParam param);

    void deleteClientLawsuitClientMap(LawsuitClientMemberIdParam param);

    List<Long> selectMemberByLawsuitId(long lawsuitId);

    void deleteLawsuitInfo(long lawsuitId);

    void deleteLawsuitClientMap(long lawsuitId);

    void deleteLawsuitMemberMap(long lawsuitId);

    void deleteLawsuitClientMapByClientId(long clientId);

    List<ClientLawsuitCountDto> selectLawsuitCountByClientId(long clientId);

    List<LawsuitBasicRawDto> selectBasicLawInfo(Long lawsuitId);

    void updateLawsuitStatus(LawsuitStatusUpdateParam param);

    List<LawsuitPrintRawDto> selectPrintInfo(Long lawsuitId);
}
