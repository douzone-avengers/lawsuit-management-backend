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

    public static FileDto of(ExpenseFileDataForm form, String path, String uuid) {
        String showFileName = FilenameUtils.getBaseName(form.getFileData().getOriginalFilename());

        return FileDto.builder()
                .fileData(form.getFileData())
                .showFileName(showFileName)
                .originFileName(showFileName + uuid)
                .path(path)
                .extension(FilenameUtils.getExtension(form.getFileData().getOriginalFilename()))
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
