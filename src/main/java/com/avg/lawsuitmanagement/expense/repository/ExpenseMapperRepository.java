package com.avg.lawsuitmanagement.expense.repository;

import com.avg.lawsuitmanagement.expense.dto.ExpenseDto;
import com.avg.lawsuitmanagement.expense.repository.param.ExpenseInsertParam;
import com.avg.lawsuitmanagement.expense.repository.param.ExpenseSelectParam;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ExpenseMapperRepository {
    List<ExpenseDto> selectSearchExpense(ExpenseSelectParam param);
    Long searchCount(ExpenseSelectParam param);
    void insertExpense(ExpenseInsertParam param);
}