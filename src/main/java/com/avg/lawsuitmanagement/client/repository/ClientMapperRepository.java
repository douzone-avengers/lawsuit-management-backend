package com.avg.lawsuitmanagement.client.repository;

import com.avg.lawsuitmanagement.client.dto.ClientDto;
import com.avg.lawsuitmanagement.client.repository.param.InsertClientParam;
import com.avg.lawsuitmanagement.client.repository.param.ReRegisterClientParam;
import com.avg.lawsuitmanagement.client.repository.param.UpdateClientInfoParam;
import com.avg.lawsuitmanagement.client.repository.param.UpdateClientMemberIdParam;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ClientMapperRepository {

    ClientDto selectClientById(long clientId);
    ClientDto selectClientByEmailContainDeleted(String email);
    ClientDto selectClientByMemberId(long memberId);
    ClientDto selectClientByEmail(String email);
    ClientDto selectDeletedClientByEmail(String email);
    void insertClient(InsertClientParam param);
    void reRegisterClient(ReRegisterClientParam param);
    void updateClientMemberId(UpdateClientMemberIdParam param);
    void updateClientInfo(UpdateClientInfoParam param);
    void deleteClientInfo(long clientId);
    List<ClientDto> selectClientList();
    List<ClientDto> selectClientListById(List<Long> clientIdList);
    List<Long> selectClientIdListByLawsuitId(long lawsuitId);
    List<Long> selectClientByLawsuitId(long lawsuitId);

}
