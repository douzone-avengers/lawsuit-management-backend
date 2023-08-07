package com.avg.lawsuitmanagement.common.util.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class PagingDto {

    private int offset; // 시작점
    private int limit;  // 한 페이지에 나타낼 개수
}
