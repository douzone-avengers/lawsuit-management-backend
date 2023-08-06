package com.avg.lawsuitmanagement.common.util.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PageRangeDto {
    private int startPage;
    private int endPage;
}
