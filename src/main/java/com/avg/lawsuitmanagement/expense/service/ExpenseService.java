package com.avg.lawsuitmanagement.expense.service;

import com.avg.lawsuitmanagement.common.custom.CustomRuntimeException;
import com.avg.lawsuitmanagement.common.util.SecurityUtil;
import com.avg.lawsuitmanagement.expense.controller.form.*;
import com.avg.lawsuitmanagement.expense.dto.ExpenseBillSelectDto;
import com.avg.lawsuitmanagement.expense.dto.ExpenseDto;
import com.avg.lawsuitmanagement.expense.repository.ExpenseMapperRepository;
import com.avg.lawsuitmanagement.expense.repository.param.*;
import com.avg.lawsuitmanagement.file.dto.FileDto;
import com.avg.lawsuitmanagement.file.dto.FileMetaDto;
import com.avg.lawsuitmanagement.file.repository.FileMapperRepository;
import com.avg.lawsuitmanagement.file.service.FileService;
import com.avg.lawsuitmanagement.lawsuit.service.LawsuitService;
import com.avg.lawsuitmanagement.lawsuit.type.LawsuitStatus;
import com.avg.lawsuitmanagement.member.repository.MemberMapperRepository;
import com.avg.lawsuitmanagement.member.service.LoginUserInfoService;

import java.util.List;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import static com.avg.lawsuitmanagement.common.exception.type.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExpenseService {

    private final ExpenseMapperRepository expenseRepository;
    private final LawsuitService lawsuitService;
    private final MemberMapperRepository memberMapperRepository;
    private final LoginUserInfoService loginUserInfoService;
    private final FileService fileService;

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
        } else {
            throw new CustomRuntimeException(MEMBER_NOT_ASSIGNED_TO_LAWSUIT);
        }
    }

    public ExpenseDto updateExpense(Long expenseId, ExpenseUpdateForm form) {
        // 기존에 등록된 멤버 id 리스트
        List<Long> originMemberIdList = memberMapperRepository.selectMemberIdListByLawsuitId(form.getLawsuitId());

        if (isUserAuthorizedForLawsuit(loginUserInfoService.getLoginMemberInfo().getId(), originMemberIdList)) {
            expenseRepository.updateExpense(ExpenseUpdateParam.of(expenseId, form));
        } else {
            throw new CustomRuntimeException(MEMBER_NOT_ASSIGNED_TO_LAWSUIT);
        }

        return expenseRepository.selectExpenseById(expenseId);
    }

    public void deleteExpense(Long expenseId, Long lawsuitId) {
        // 기존에 등록된 멤버 id 리스트
        List<Long> originMemberIdList = memberMapperRepository.selectMemberIdListByLawsuitId(lawsuitId);

        if (isUserAuthorizedForLawsuit(loginUserInfoService.getLoginMemberInfo().getId(), originMemberIdList)) {
            expenseRepository.deleteExpense(expenseId);
        } else {
            throw new CustomRuntimeException(MEMBER_NOT_ASSIGNED_TO_LAWSUIT);
        }
    }

    public List<FileMetaDto> selectExpenseBillInfo(long expenseId, Long page) {
        ExpenseDto expenseDto = expenseRepository.selectExpenseById(expenseId);

        if (expenseDto == null) {
            throw new CustomRuntimeException(EXPENSE_NOT_FOUND);
        }

        return fileService.selectFileInfoListByExpenseId(ExpenseBillSelectDto.of(expenseId, page, 5L));
    }

    @Transactional
    public void insertExpenseBill(long expenseId, MultipartFile file) {
        ExpenseDto expenseDto = expenseRepository.selectExpenseById(expenseId);
        if (expenseDto == null) {
            throw new CustomRuntimeException(EXPENSE_NOT_FOUND);
        }

        String path = "expenseBill/";
        String uuid = UUID.randomUUID().toString();

        // 파일 생성 및 DB에 meta 데이터 저장
        FileDto fileDto = FileDto.of(file, path, uuid);
        fileService.createFileAndInsertDataBase(fileDto);

        FileMetaDto fileMetaDto = fileService.selectFileByOriginFileName(fileDto.getOriginFileName());
        ExpenseFileIdParam param = ExpenseFileIdParam.of(expenseId, fileMetaDto.getId());
        // 지출-파일 테이블에 해당 지출id, 파일id 삽입
        expenseRepository.insertExpenseFileMap(param);
    }

    public void deleteExpenseBill(long fileId, long expenseId) {
        FileMetaDto fileMetaDto = fileService.selectFileById(fileId);

        if (fileMetaDto == null) {
            throw new CustomRuntimeException(FILE_NOT_FOUND);
        }

        fileService.deleteFile(fileId, expenseId);
    }

    private boolean isUserAuthorizedForLawsuit(long userId, List<Long> memberIds) {
        if (SecurityUtil.getCurrentLoginRoleList().contains("ROLE_ADMIN")) {
            return true;
        }

        // 로그인한 사용자가 해당 사건의 담당자가 아니라면
        return memberIds.contains(userId);
    }
}
