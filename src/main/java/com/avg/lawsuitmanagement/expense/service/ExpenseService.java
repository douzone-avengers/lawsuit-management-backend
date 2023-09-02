package com.avg.lawsuitmanagement.expense.service;

import com.avg.lawsuitmanagement.common.util.SecurityUtil;
import com.avg.lawsuitmanagement.expense.controller.form.ExpenseInsertForm;
import com.avg.lawsuitmanagement.expense.controller.form.ExpenseSearchForm;
import com.avg.lawsuitmanagement.expense.controller.form.ExpenseUpdateForm;
import com.avg.lawsuitmanagement.expense.dto.ExpenseDto;
import com.avg.lawsuitmanagement.expense.repository.ExpenseMapperRepository;
import com.avg.lawsuitmanagement.expense.repository.param.ExpenseInsertParam;
import com.avg.lawsuitmanagement.expense.repository.param.ExpenseSelectParam;
import com.avg.lawsuitmanagement.expense.repository.param.ExpenseUpdateParam;
import com.avg.lawsuitmanagement.lawsuit.service.LawsuitService;
import com.avg.lawsuitmanagement.lawsuit.type.LawsuitStatus;
import com.avg.lawsuitmanagement.member.repository.MemberMapperRepository;
import com.avg.lawsuitmanagement.member.service.LoginUserInfoService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseMapperRepository expenseRepository;
    private final LawsuitService lawsuitService;
    private final MemberMapperRepository memberMapperRepository;
    private final LoginUserInfoService loginUserInfoService;

    public List<ExpenseDto> searchExpense(ExpenseSearchForm form) {
        return expenseRepository.selectSearchExpense(form.toParam());
    }

    public Long searchSize(ExpenseSearchForm form) {
        ExpenseSelectParam param = form.toParam();

        return expenseRepository.searchCount(param);
    }

    @Transactional
    public void insertExpense(ExpenseInsertForm form) {
        // 기존에 등록된 멤버 id 리스트
        List<Long> originMemberIdList = memberMapperRepository.selectMemberIdListByLawsuitId(form.getLawsuitId());

        if (isUserAuthorizedForLawsuit(loginUserInfoService.getLoginMemberInfo().getId(), originMemberIdList)) {
            ExpenseInsertParam param = form.toParam();

            expenseRepository.insertExpense(param);
            lawsuitService.updateStatus(form.getLawsuitId(), LawsuitStatus.PROCEEDING);
        }
    }

    public ExpenseDto updateExpense(Long expenseId, ExpenseUpdateForm form) {
        // 기존에 등록된 멤버 id 리스트
        List<Long> originMemberIdList = memberMapperRepository.selectMemberIdListByLawsuitId(form.getLawsuitId());

        if (isUserAuthorizedForLawsuit(loginUserInfoService.getLoginMemberInfo().getId(), originMemberIdList)) {
            expenseRepository.updateExpense(ExpenseUpdateParam.of(expenseId, form));
        }

        return expenseRepository.selectExpenseById(expenseId);
    }

    public void deleteExpense(Long expenseId, Long lawsuitId) {
        // 기존에 등록된 멤버 id 리스트
        List<Long> originMemberIdList = memberMapperRepository.selectMemberIdListByLawsuitId(lawsuitId);

        if (isUserAuthorizedForLawsuit(loginUserInfoService.getLoginMemberInfo().getId(), originMemberIdList)) {
            expenseRepository.deleteExpense(expenseId);
        }
    }

    private boolean isUserAuthorizedForLawsuit(long userId, List<Long> memberIds) {
        if (SecurityUtil.getCurrentLoginRoleList().contains("ROLE_ADMIN")) {
            return true;
        }

        // 로그인한 사용자가 해당 사건의 담당자가 아니라면
        return memberIds.contains(userId);
    }
}
