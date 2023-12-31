package com.avg.lawsuitmanagement.file.repository;

import com.avg.lawsuitmanagement.expense.repository.param.ExpenseBillSelectParam;
import com.avg.lawsuitmanagement.expense.repository.param.ExpenseFileIdParam;
import com.avg.lawsuitmanagement.expense.repository.param.ExpenseSelectParam;
import com.avg.lawsuitmanagement.file.dto.FileMetaDto;
import com.avg.lawsuitmanagement.file.repository.param.FileInsertParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FileMapperRepository {
    FileMetaDto selectFileByOriginFileName(String originFileName);
    FileMetaDto selectFileById(long fileId);
    List<Long> selectFileIdListByExpenseId(long expenseId);
    List<FileMetaDto> selectFileInfoListById(ExpenseBillSelectParam param);
    void insertFile(FileInsertParam param);
    void deleteFile(Long fileId);
    void deleteExpenseFileMap(ExpenseFileIdParam param);
    Long searchCount(Long expenseId);
}
