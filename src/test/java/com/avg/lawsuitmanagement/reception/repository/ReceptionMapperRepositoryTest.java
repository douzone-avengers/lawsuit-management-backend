package com.avg.lawsuitmanagement.reception.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.avg.lawsuitmanagement.reception.dto.ReceptionDto;
import com.avg.lawsuitmanagement.reception.repository.param.ReceptionInsertParam;
import com.avg.lawsuitmanagement.reception.repository.param.ReceptionSelectParam;
import com.avg.lawsuitmanagement.reception.repository.param.ReceptionUpdateParam;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class ReceptionMapperRepositoryTest {

    @Autowired
    ReceptionMapperRepository receptionRepository;

    static Long LAWSUIT_ID = 1L;
    static Long COUNT = 20L;

    @BeforeEach
    void beforeEach() {
        receptionRepository.truncate();

        for (int i = 1; i <= COUNT; i++) {
            boolean isEven = i % 2 == 0;

            ReceptionInsertParam param = ReceptionInsertParam.builder()
                .lawsuitId(LAWSUIT_ID)
                .status(isEven)
                .category(isEven ? "fixed" : "scheduled")
                .contents("Document" + i)
                .receivedAt(LocalDate.of(2023, 1, i))
                .deadline(LocalDate.of(2023, 2, i))
                .build();

            receptionRepository.insert(param);
        }
    }

    @Test
    void selectTest() {
        // Given
        ReceptionSelectParam param1 = ReceptionSelectParam.builder()
            .lawsuitId(LAWSUIT_ID)
            .build();

        // When
        List<ReceptionDto> result1 = receptionRepository.select(param1);

        // Then
        assertThat(result1.size()).isEqualTo(5);
    }

    @Test
    void selectByIdTest() {
        // Given
        Long lawsuitId = 1L;
        Boolean status = false;
        String category = "fixed";
        String contents = "내용";
        LocalDate receivedAt = LocalDate.of(2023, 8, 1);
        LocalDate deadline = LocalDate.of(2023, 9, 1);

        ReceptionInsertParam param = ReceptionInsertParam.builder()
            .lawsuitId(lawsuitId)
            .status(status)
            .category(category)
            .contents(contents)
            .receivedAt(receivedAt)
            .deadline(deadline)
            .build();
        receptionRepository.insert(param);

        Long id = param.getId();

        // When
        ReceptionDto receptionDto = receptionRepository.selectById(id);

        // Then
        assertThat(receptionDto.getId()).isEqualTo(id);
        assertThat(receptionDto.getLawsuitId()).isEqualTo(lawsuitId);
        assertThat(receptionDto.getStatus()).isEqualTo(status);
        assertThat(receptionDto.getCategory()).isEqualTo(category);
        assertThat(receptionDto.getContents()).isEqualTo(contents);
        assertThat(receptionDto.getReceivedAt()).isEqualTo(receivedAt);
        assertThat(receptionDto.getDeadline()).isEqualTo(deadline);
    }

    @Test
    void insertTest() {
        // Given
        Long prevCount = receptionRepository.count();

        Long lawsuitId = 1L;
        Boolean status = false;
        String category = "fixed";
        String contents = "내용";
        LocalDate receivedAt = LocalDate.of(2023, 8, 1);
        LocalDate deadline = LocalDate.of(2023, 9, 1);

        ReceptionInsertParam param = ReceptionInsertParam.builder()
            .lawsuitId(lawsuitId)
            .status(status)
            .category(category)
            .contents(contents)
            .receivedAt(receivedAt)
            .deadline(deadline)
            .build();

        // When
        receptionRepository.insert(param);
        Long curCount = receptionRepository.count();

        // Then
        assertThat(curCount).isEqualTo(prevCount + 1);
    }

    @Test
    void updateTest() {
        // Given
        Long lawsuitId = 1L;
        Boolean status = false;
        String category = "fixed";
        String contents = "내용";
        LocalDate receivedAt = LocalDate.of(2023, 8, 1);
        LocalDate deadline = LocalDate.of(2023, 9, 1);

        ReceptionInsertParam insertParam = ReceptionInsertParam.builder()
            .lawsuitId(lawsuitId)
            .status(status)
            .category(category)
            .contents(contents)
            .receivedAt(receivedAt)
            .deadline(deadline)
            .build();

        receptionRepository.insert(insertParam);

        Long id = insertParam.getId();

        Boolean newStatus = true;
        String newCategory = "scheduled";
        String newContents = "새로운 내용";
        LocalDate newReceivedAt = LocalDate.of(2023, 10, 1);
        LocalDate newDeadline = LocalDate.of(2023, 11, 1);

        ReceptionUpdateParam updateParam = ReceptionUpdateParam.builder()
            .id(id)
            .status(newStatus)
            .category(newCategory)
            .contents(newContents)
            .receivedAt(newReceivedAt)
            .deadline(newDeadline)
            .build();

        // When
        receptionRepository.update(updateParam);
        ReceptionDto foundReception = receptionRepository.selectById(id);

        // Then
        assertThat(foundReception.getId()).isEqualTo(id);
        assertThat(foundReception.getLawsuitId()).isEqualTo(lawsuitId);
        assertThat(foundReception.getStatus()).isEqualTo(newStatus);
        assertThat(foundReception.getCategory()).isEqualTo(newCategory);
        assertThat(foundReception.getContents()).isEqualTo(newContents);
        assertThat(foundReception.getReceivedAt()).isEqualTo(newReceivedAt);
        assertThat(foundReception.getDeadline()).isEqualTo(newDeadline);
    }

    @Test
    void deleteTest() {
        // Given
        Long prevCount = receptionRepository.count();

        Long lawsuitId = 1L;
        Boolean status = false;
        String category = "fixed";
        String contents = "내용";
        LocalDate receivedAt = LocalDate.of(2023, 8, 1);
        LocalDate deadline = LocalDate.of(2023, 9, 1);

        ReceptionInsertParam param = ReceptionInsertParam.builder()
            .lawsuitId(lawsuitId)
            .status(status)
            .category(category)
            .contents(contents)
            .receivedAt(receivedAt)
            .deadline(deadline)
            .build();
        receptionRepository.insert(param);

        Long id = param.getId();

        // When
        receptionRepository.delete(id);
        ReceptionDto foundReception = receptionRepository.selectById(id);
        Long curCount = receptionRepository.count();

        // Then
        assertThat(foundReception).isNull();
        assertThat(prevCount).isEqualTo(curCount);
    }

    @Test
    void truncateTest() {
        // Given
        receptionRepository.truncate();

        // When
        Long count = receptionRepository.count();

        // Then
        assertThat(count).isEqualTo(0);
    }

    @Test
    void countTest() {
        // Given

        // When
        Long count = receptionRepository.count();

        // Then
        assertThat(count).isEqualTo(20);
    }

}