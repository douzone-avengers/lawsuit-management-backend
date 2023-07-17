package com.avg.lawsuitmanagement.token.repository;


import com.avg.lawsuitmanagement.token.dto.RefreshTokenDto;
import com.avg.lawsuitmanagement.token.repository.param.RefreshTokenParam;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TokenMapperRepository {

    void insertRefreshToken(RefreshTokenParam param);
    RefreshTokenDto selectRefreshTokenByKey(String key);
}
