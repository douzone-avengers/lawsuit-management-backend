package com.avg.lawsuitmanagement.reception.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.avg.lawsuitmanagement.reception.controller.form.ReceptionCreateForm;
import com.avg.lawsuitmanagement.reception.controller.form.ReceptionEditForm;
import com.avg.lawsuitmanagement.reception.controller.form.ReceptionSearchForm;
import com.avg.lawsuitmanagement.reception.dto.ReceptionDto;
import com.avg.lawsuitmanagement.reception.repository.ReceptionMapperRepository;
import com.avg.lawsuitmanagement.reception.repository.param.ReceptionInsertParam;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class ReceptionServiceTest {

    @Autowired
    ReceptionService receptionService;
    @Autowired
    ReceptionMapperRepository receptionRepository;

    @BeforeEach
    void beforeEach() {
        receptionRepository.truncate();

        ReceptionInsertParam param = ReceptionInsertParam.builder()
            .id(1L)
            .lawsuitId(1L)
            .status(false)
            .category("fixed")
            .contents("내용")
            .receivedAt(LocalDate.of(2023, 8, 1))
            .deadline(LocalDate.of(2023, 9, 1))
            .build();
        receptionRepository.insert(param);
    }

    @Test
    void search() {
        // Given
        Long lawsuitId = 1L;
        Long page = 0L;
        Long count = 1L;
        Boolean status = false;
        String category = "fixed";
        String contents = "내용";
        LocalDate startReceivedAt = LocalDate.of(2023, 8, 1);
        LocalDate endReceivedAt = LocalDate.of(2023, 8, 1);
        LocalDate startDeadline = LocalDate.of(2023, 9, 1);
        LocalDate endDeadline = LocalDate.of(2023, 9, 1);

        ReceptionSearchForm form = ReceptionSearchForm.builder()
            .lawsuitId(lawsuitId)
            .page(page)
            .count(count)
            .status(status)
            .category(category)
            .contents(contents)
            .startReceivedAt(startReceivedAt)
            .endReceivedAt(endReceivedAt)
            .startDeadline(startDeadline)
            .endDeadline(endDeadline)
            .build();

        // When
        List<ReceptionDto> result = receptionService.search(form);
        ReceptionDto receptionDto = result.get(0);

        // Then
        assertThat(receptionDto).isNotNull();
    }

    @Test
    void create() {
        // Given
        Long id = 2L;
        Long lawsuitId = 2L;
        Boolean status = true;
        String category = "scheduled";
        String contents = "다른 내용";
        LocalDate receivedAt = LocalDate.of(2023, 10, 1);
        LocalDate deadline = LocalDate.of(2023, 11, 1);

        ReceptionCreateForm form = ReceptionCreateForm.builder()
            .id(id)
            .lawsuitId(lawsuitId)
            .status(status)
            .category(category)
            .contents(contents)
            .receivedAt(receivedAt)
            .deadline(deadline)
            .build();

        // When
        ReceptionDto receptionDto = receptionService.create(form);
        Long count = receptionRepository.count();

        // Then
        assertThat(count).isEqualTo(2);
        assertThat(receptionDto.getId()).isEqualTo(id);
        assertThat(receptionDto.getLawsuitId()).isEqualTo(lawsuitId);
        assertThat(receptionDto.getStatus()).isEqualTo(status);
        assertThat(receptionDto.getCategory()).isEqualTo(category);
        assertThat(receptionDto.getContents()).isEqualTo(contents);
        assertThat(receptionDto.getReceivedAt()).isEqualTo(receivedAt);
        assertThat(receptionDto.getDeadline()).isEqualTo(deadline);
    }

    @Test
    void edit() {
        // Given
        Long id = 1L;
        Boolean status = true;
        String category = "scheduled";
        String contents = "다른 내용";
        LocalDate receivedAt = LocalDate.of(2023, 10, 1);
        LocalDate deadline = LocalDate.of(2023, 11, 1);

        ReceptionEditForm form = ReceptionEditForm.builder()
            .status(status)
            .category(category)
            .contents(contents)
            .receivedAt(receivedAt)
            .deadline(deadline)
            .build();

        // When
        ReceptionDto receptionDto = receptionService.edit(id, form);

        // Then
        assertThat(receptionDto.getId()).isEqualTo(id);
        assertThat(receptionDto.getStatus()).isEqualTo(status);
        assertThat(receptionDto.getCategory()).isEqualTo(category);
        assertThat(receptionDto.getContents()).isEqualTo(contents);
        assertThat(receptionDto.getReceivedAt()).isEqualTo(receivedAt);
        assertThat(receptionDto.getDeadline()).isEqualTo(deadline);
    }

    @Test
    void delete() {
        // Given
        Long id = 1L;

        // When
        receptionService.remove(id);
        Long count = receptionRepository.count();

        // Then
        assertThat(count).isEqualTo(0);
    }
}