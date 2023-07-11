package com.avg.lawsuitmanagement.token.repository;

import com.avg.lawsuitmanagement.token.dto.ClientDto;
import com.avg.lawsuitmanagement.token.repository.param.ClientLoginParam;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TokenMapperRepository {

    ClientDto selectClientByIdAndPassword(ClientLoginParam param);

}
