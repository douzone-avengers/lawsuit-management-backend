package com.avg.lawsuitmanagement.expense.controller;

import com.avg.lawsuitmanagement.expense.controller.form.ExpenseInsertForm;
import com.avg.lawsuitmanagement.expense.controller.form.ExpenseSearchForm;
import com.avg.lawsuitmanagement.expense.controller.form.ExpenseUpdateForm;
import com.avg.lawsuitmanagement.expense.dto.ExpenseDto;
import com.avg.lawsuitmanagement.expense.dto.ExpenseSearchDto;
import com.avg.lawsuitmanagement.expense.service.ExpenseService;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
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

@RestController
@RequestMapping("/expenses")
@RequiredArgsConstructor
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
        Long endAmount
    ) {
        System.out.println("page = " + page);
        ExpenseSearchForm form = ExpenseSearchForm.builder()
            .lawsuitId(lawsuitId)
            .page(page)
            .count(5L)
            .contents(contents)
            .startSpeningAt(startSpeningAt)
            .endSpeningAt(endSpeningAt)
            .startAmount(startAmount)
            .endAmount(endAmount)
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
    public ResponseEntity<Void> deleteExpense(@PathVariable Long expenseId) {
        expenseService.deleteExpense(expenseId);
        return ResponseEntity.ok(null);
    }

}
