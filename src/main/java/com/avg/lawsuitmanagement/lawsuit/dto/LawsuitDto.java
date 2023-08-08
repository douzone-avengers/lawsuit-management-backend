package com.avg.lawsuitmanagement.lawsuit.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.RequiredArgsConstructor;

@Getter
@Setter
@RequiredArgsConstructor
public class LawsuitDto {
    private Long id;
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
