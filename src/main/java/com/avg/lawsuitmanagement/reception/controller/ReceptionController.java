package com.avg.lawsuitmanagement.reception.controller;

import com.avg.lawsuitmanagement.reception.controller.form.ReceptionCreateForm;
import com.avg.lawsuitmanagement.reception.controller.form.ReceptionEditForm;
import com.avg.lawsuitmanagement.reception.controller.form.ReceptionSearchForm;
import com.avg.lawsuitmanagement.reception.dto.ReceptionDto;
import com.avg.lawsuitmanagement.reception.dto.ReceptionSearchDto;
import com.avg.lawsuitmanagement.reception.service.ReceptionService;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/receptions")
@RequiredArgsConstructor
public class ReceptionController {

    private final ReceptionService receptionService;

    @GetMapping
    public ResponseEntity<?> search(
        @RequestParam(name = "lawsuit")
        Long lawsuitId,
        @RequestParam(required = false)
        Long page,
        @RequestParam(required = false)
        Boolean status,
        @RequestParam(required = false)
        String category,
        @RequestParam(required = false)
        String contents,
        @RequestParam(name = "start-received-at", required = false)
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate startReceivedAt,
        @RequestParam(name = "end-received-at", required = false)
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate endReceivedAt,
        @RequestParam(name = "start-deadline", required = false)
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate startDeadline,
        @RequestParam(name = "end-deadline", required = false)
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate endDeadline
    ) {
        ReceptionSearchForm form = ReceptionSearchForm.builder()
            .lawsuitId(lawsuitId)
            .page(page)
            .count(5L)
            .status(status)
            .category(category)
            .contents(contents)
            .startReceivedAt(startReceivedAt)
            .endReceivedAt(endReceivedAt)
            .startDeadline(startDeadline)
            .endDeadline(endDeadline)
            .build();
        List<ReceptionDto> receptions = receptionService.search(form);
        Long size = receptionService.size();
        ReceptionSearchDto result = ReceptionSearchDto.builder()
            .receptions(receptions)
            .size(size)
            .build();
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<?> create(
        @Validated
        @RequestBody
        ReceptionCreateForm form
    ) {
        ReceptionDto result = receptionService.create(form);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> edit(
        @PathVariable
        Long id,
        @Validated
        @RequestBody
        ReceptionEditForm form
    ) {
        ReceptionDto result = receptionService.edit(id, form);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<?> remove(
        @PathVariable
        Long id
    ) {
        receptionService.remove(id);
        return ResponseEntity.ok(null);
    }

}
