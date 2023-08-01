package com.avg.lawsuitmanagement.promotion.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class CreatePromotionKeyDto {
    private String value; //키값
}
