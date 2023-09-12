package com.avg.lawsuitmanagement.expense.dto;

import com.avg.lawsuitmanagement.file.dto.FileMetaDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ExpenseBillDto {
    private List<FileMetaDto> expenseBill;
    private Long size;

    public static ExpenseBillDto of (List<FileMetaDto> expenseBill, Long size) {
        return ExpenseBillDto.builder()
                .expenseBill(expenseBill)
                .size(size)
                .build();
    }
}
