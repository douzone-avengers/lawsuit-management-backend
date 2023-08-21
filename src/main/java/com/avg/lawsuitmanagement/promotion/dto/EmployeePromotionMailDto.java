package com.avg.lawsuitmanagement.promotion.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class EmployeePromotionMailDto {
    private String to;
    private String issuer;
    private String promotionKey;
}