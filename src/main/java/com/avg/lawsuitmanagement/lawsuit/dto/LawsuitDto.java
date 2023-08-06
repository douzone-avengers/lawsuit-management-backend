package com.avg.lawsuitmanagement.lawsuit.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@RequiredArgsConstructor
public class LawsuitDto {
    private String lawsuit_type;
    private String name;
    private int court_id;
    private int commission_fee;
    private int contingent_fee;
    private String lawsuit_status;
    private String lawsuit_num;
    private String result;
    private String judgement_date;
    private String created_at;
    private String updated_at;
}
