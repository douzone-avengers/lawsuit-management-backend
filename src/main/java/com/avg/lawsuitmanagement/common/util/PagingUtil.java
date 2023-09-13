package com.avg.lawsuitmanagement.common.util;

import com.avg.lawsuitmanagement.common.util.dto.PageRangeDto;
import com.avg.lawsuitmanagement.common.util.dto.PagingDto;

public class PagingUtil {
    // 페이지의 시작 번호와 해당 페이지에 나타낼 글 수
    public static PagingDto calculatePaging(int curPage, int dataPerPage) {
        return PagingDto.builder()
            .offset((curPage-1)*dataPerPage)
            .limit(dataPerPage)
            .build();
    }

    // 현재 선택한 페이지에 따른 시작페이지와 마지막 페이지
    public static PageRangeDto calculatePageRange(int curPage, long total) {
        int pageSize = 10;
        int startPage = ((curPage / pageSize) * pageSize) + 1;
        int endPage = (startPage - 1) + pageSize;
        int totalPage = (int) Math.ceil((double) total/pageSize);

        return PageRangeDto.builder()
            .startPage(startPage)
            .endPage(Math.min(totalPage, endPage))
            .build();
    }

}