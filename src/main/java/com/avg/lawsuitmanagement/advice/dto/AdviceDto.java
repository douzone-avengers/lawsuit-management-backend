package com.avg.lawsuitmanagement.advice.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@Builder
@ToString
public class AdviceDto {

    private long id;
    private String title; //상담 제목
    private String contents; //상담 내용
    private String adviceAt; // 상담 일시
    private String createdAt; // 생성일
    private String updatedAt; // 수정일
    private boolean isDeleted; //삭제여부
    private long lawsuitId;
    private List<Long> memberId;
    private List<Long> clientId;




}
