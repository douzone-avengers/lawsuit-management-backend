package com.avg.lawsuitmanagement.promotion.repository.param;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class InsertPromotionKeyParam {
    private String value;
    private long clientId;

}
