package com.avg.lawsuitmanagement.file.dto;

import com.avg.lawsuitmanagement.expense.controller.form.ExpenseFileDataForm;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
public class FileDto {
    @NotNull
    private MultipartFile fileData;
    @NotNull
    private String showFileName;
    @NotNull
    private String originFileName;
    @NotNull
    private String path;
    @NotNull
    private String extension;

    public static FileDto of(MultipartFile file, String path, String uuid) {
        String showFileName = FilenameUtils.getBaseName(file.getOriginalFilename());

        return FileDto.builder()
                .fileData(file)
                .showFileName(showFileName)
                .originFileName(showFileName + uuid)
                .path(path)
                .extension(FilenameUtils.getExtension(file.getOriginalFilename()))
                .build();
    }

    public static FileDto of(FileMetaDto dto) {
        return FileDto.builder()
                .showFileName(dto.getShowFileName())
                .originFileName(dto.getOriginFileName())
                .path(dto.getPath())
                .extension(dto.getExtension())
                .build();
    }
}
