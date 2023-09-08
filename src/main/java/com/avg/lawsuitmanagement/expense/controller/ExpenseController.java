package com.avg.lawsuitmanagement.expense.controller;

import com.avg.lawsuitmanagement.common.custom.CustomRuntimeException;
import com.avg.lawsuitmanagement.common.type.SortOrder;
import com.avg.lawsuitmanagement.expense.controller.form.*;
import com.avg.lawsuitmanagement.expense.dto.ExpenseDto;
import com.avg.lawsuitmanagement.expense.dto.ExpenseSearchDto;
import com.avg.lawsuitmanagement.expense.service.ExpenseService;
import com.avg.lawsuitmanagement.expense.type.ExpenseSortKey;
import java.time.LocalDate;
import java.util.List;
import com.avg.lawsuitmanagement.file.dto.FileMetaDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import static com.avg.lawsuitmanagement.common.exception.type.ErrorCode.FILE_NOT_FOUND;

@RestController
@RequestMapping("/expenses")
@RequiredArgsConstructor
@Slf4j
public class ExpenseController {
    private final ExpenseService expenseService;

    // 지출 조회 및 검색
    @GetMapping()
    public ResponseEntity<ExpenseSearchDto> searchExpense(
        @RequestParam(name = "lawsuit")
        Long lawsuitId,
        @RequestParam(required = false)
        Long page,
        @RequestParam(required = false)
        String contents,
        @RequestParam(name = "start-spening-at", required = false)
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate startSpeningAt,
        @RequestParam(name = "end-spening-at", required = false)
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate endSpeningAt,
        @RequestParam(name = "start-amount", required = false)
        Long startAmount,
        @RequestParam(name = "end-amount", required = false)
        Long endAmount,
        @RequestParam(name = "sortKey", required = false)
        ExpenseSortKey sortKey,
        @RequestParam(name = "sortOrder", required = false)
        SortOrder sortOrder
    ) {
        ExpenseSearchForm form = ExpenseSearchForm.builder()
            .lawsuitId(lawsuitId)
            .page(page)
            .count(5L)
            .contents(contents)
            .startSpeningAt(startSpeningAt)
            .endSpeningAt(endSpeningAt)
            .startAmount(startAmount)
            .endAmount(endAmount)
            .sortKey(sortKey)
            .sortOrder(sortOrder)
            .build();

        List<ExpenseDto> expenses  = expenseService.searchExpense(form);
        Long size = expenseService.searchSize(form);

        ExpenseSearchDto result = ExpenseSearchDto.builder()
            .expenses(expenses)
            .size(size)
            .build();

        return ResponseEntity.ok(result);
    }

    // 지출 등록
    @PostMapping()
    public ResponseEntity<Void> insertExpense(@Validated @RequestBody ExpenseInsertForm form) {
        expenseService.insertExpense(form);
        return ResponseEntity.ok().build();
    }

    // 지출 수정
    @PutMapping("/update/{expenseId}")
    public ResponseEntity<ExpenseDto> updateExpense(@PathVariable Long expenseId, @Validated @RequestBody ExpenseUpdateForm form) {
        return ResponseEntity.ok(expenseService.updateExpense(expenseId, form));
    }

    // 지출 삭제
    @PatchMapping("/delete/{expenseId}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long expenseId, @Validated @RequestParam long lawsuitId) {
        expenseService.deleteExpense(expenseId, lawsuitId);
        return ResponseEntity.ok().build();
    }

    // 지출 증빙자료(File 데이터, Meta 데이터) 등록
    @PostMapping("/{expenseId}/bill")
    public ResponseEntity<?> insertExpenseBill(@PathVariable Long expenseId, @Validated ExpenseFileDataForm form) {
        MultipartFile file = form.getFileData();

        if (file == null || file.isEmpty()) {
            throw new CustomRuntimeException(FILE_NOT_FOUND);
        }

        expenseService.insertExpenseBill(expenseId, form);

        return ResponseEntity.ok().build();
    }

    // 지출 증빙자료(Meta 데이터) 조회
    @GetMapping("/{expenseId}/bill")
    public ResponseEntity<List<FileMetaDto>> selectExpenseBillData(@PathVariable Long expenseId) {
        return ResponseEntity.ok(expenseService.selectExpenseBillInfo(expenseId));
    }

    // 지출 증빙자료(Meta 데이터) 삭제
    @PatchMapping("/delete/{fileId}/bill")
    public ResponseEntity<Void> deleteExpenseBill(@PathVariable Long fileId) {
        expenseService.deleteExpenseBill(fileId);
        return ResponseEntity.ok().build();
    }
}
