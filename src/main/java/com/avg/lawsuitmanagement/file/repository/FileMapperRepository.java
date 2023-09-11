package com.avg.lawsuitmanagement.file.repository;

import com.avg.lawsuitmanagement.file.dto.FileMetaDto;
import com.avg.lawsuitmanagement.file.repository.param.FileInsertParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FileMapperRepository {
    FileMetaDto selectFileByOriginFileName(String originFileName);
    FileMetaDto selectFileById(long fileId);
    List<Long> selectFileIdListByExpenseId(long expenseId);
    List<FileMetaDto> selectFileInfoListById(long expenseId);
    void insertFile(FileInsertParam param);
    void deleteFile(Long fileId);
}
