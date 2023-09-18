package com.avg.lawsuitmanagement.advice.repository.param;

import com.avg.lawsuitmanagement.advice.controller.form.AdviceListForm;
import com.avg.lawsuitmanagement.common.util.dto.PagingDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AdviceListParam {
    private Long lawsuitId;
    private String sortKey;
    private String sortOrder;

    private Integer offset;
    private Integer limit;

    public static AdviceListParam of(AdviceListForm form, long lawsuitId){
        return AdviceListParam.builder()
                .lawsuitId(lawsuitId)
                .sortKey(
                        form.getSortKey() != null ? form.getSortKey() : null)
                .sortOrder(
                        form.getSortOrder() != null ? form.getSortOrder() : null)
                .build();
    }
    public static AdviceListParam of(AdviceListForm form, PagingDto pagingDto, long lawsuitId){
        return AdviceListParam.builder()
                .lawsuitId(lawsuitId)
                .sortKey(
                        form.getSortKey() != null ? form.getSortKey() : null)
                .sortOrder(
                    form.getSortOrder() != null ? form.getSortOrder() : null)
                .offset(pagingDto.getOffset())
                .limit(pagingDto.getLimit())
                .build();

    }



}
