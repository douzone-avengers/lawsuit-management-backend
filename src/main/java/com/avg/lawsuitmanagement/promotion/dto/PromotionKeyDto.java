package com.avg.lawsuitmanagement.promotion.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class PromotionKeyDto {

    private long id;
    private String value;
    private String createdAt;
    private String updatedAt;
    private long clientId;
    private boolean isActive;
}
