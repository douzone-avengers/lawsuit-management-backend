package com.avg.lawsuitmanagement.file;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FileSaveDto {
    private String fileName;
    private String detailPath; //파일 세부 경로
    private String data;
}
