package com.avg.lawsuitmanagement.file.repository.param;

import com.avg.lawsuitmanagement.file.dto.FileDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FileInsertParam {
    private String showFileName;
    private String originFileName;
    private String path;
    private String extension;

    public static FileInsertParam of (FileDto dto) {
        return FileInsertParam.builder()
                .showFileName(dto.getShowFileName())
                .originFileName(dto.getOriginFileName())
                .path(dto.getPath())
                .extension(dto.getExtension())
                .build();
    }
}
