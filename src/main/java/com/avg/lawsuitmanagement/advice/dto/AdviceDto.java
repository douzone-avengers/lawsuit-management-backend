package com.avg.lawsuitmanagement.advice.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
public class AdviceDto {

    private long id;
    private String title; //상담 제목
    private String contents; //상담 내용
    private String advicedAt; // 상담 일시
    private LocalDate createdAt; // 생성일
    private LocalDate updatedAt; // 수정일
    private boolean isDeleted; //삭제여부
    private long lawsuitId;
    private List<Long> memberId;
    private List<Long> clientId;




}
