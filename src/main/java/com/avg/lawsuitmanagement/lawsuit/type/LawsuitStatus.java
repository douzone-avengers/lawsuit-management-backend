package com.avg.lawsuitmanagement.lawsuit.type;

import lombok.Getter;

@Getter
public enum LawsuitStatus {
    REGISTRATION("등록"),
    PROCEEDING("진행"),
    CLOSING("종결"),
    PLAINTIFF_WIN("원고승"),
    PLAINTIFF_LOSE("원고패");

    private final String statusKr;
    LawsuitStatus(String str) {
        this.statusKr = str;
    }
}
