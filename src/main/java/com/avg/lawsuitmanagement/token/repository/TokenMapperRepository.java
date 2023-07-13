package com.avg.lawsuitmanagement.token.repository;

import com.avg.lawsuitmanagement.token.dto.ClientDto;
import com.avg.lawsuitmanagement.token.dto.EmployeeDto;
import com.avg.lawsuitmanagement.token.repository.param.ClientSignUpParam;
import com.avg.lawsuitmanagement.token.repository.param.EmployeeSignUpParam;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TokenMapperRepository {
    ClientDto selectClientByEmail(String email);

    void insertClient(ClientSignUpParam param);

    EmployeeDto selectEmployeeById(String email);

    void insertEmployee(EmployeeSignUpParam param);


}
