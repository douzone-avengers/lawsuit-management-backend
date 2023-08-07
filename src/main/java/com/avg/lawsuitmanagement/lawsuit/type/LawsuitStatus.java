package com.avg.lawsuitmanagement.lawsuit.type;

import lombok.Getter;

@Getter
public enum LawsuitStatus {
    REGISTRATION("등록"),
    PROCEEDING("진행"),
    CLOSING("종결");

    private final String statusKr;
    LawsuitStatus(String str) {
        this.statusKr = str;
    }
}
