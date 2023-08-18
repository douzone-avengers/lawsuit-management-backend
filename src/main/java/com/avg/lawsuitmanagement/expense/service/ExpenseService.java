package com.avg.lawsuitmanagement.expense.service;

import com.avg.lawsuitmanagement.expense.controller.form.ExpenseInsertForm;
import com.avg.lawsuitmanagement.expense.controller.form.ExpenseSearchForm;
import com.avg.lawsuitmanagement.expense.dto.ExpenseDto;
import com.avg.lawsuitmanagement.expense.repository.ExpenseMapperRepository;
import com.avg.lawsuitmanagement.expense.repository.param.ExpenseInsertParam;
import com.avg.lawsuitmanagement.expense.repository.param.ExpenseSelectParam;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseMapperRepository expenseRepository;

    public List<ExpenseDto> searchExpense(ExpenseSearchForm form) {
        return expenseRepository.selectSearchExpense(form.toParam());
    }

    public Long searchSize(ExpenseSearchForm form) {
        ExpenseSelectParam param = form.toParam();

        return expenseRepository.searchCount(param);
    }

    public void insertExpense(ExpenseInsertForm form) {
        ExpenseInsertParam param = form.toParam();
        expenseRepository.insertExpense(param);
    }
}
