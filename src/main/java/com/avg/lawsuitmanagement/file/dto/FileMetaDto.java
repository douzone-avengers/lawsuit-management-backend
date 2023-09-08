package com.avg.lawsuitmanagement.file.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileMetaDto {
    private Long id;
    private String showFileName;
    private String originFileName;
    private String path;
    private String extension;
}
