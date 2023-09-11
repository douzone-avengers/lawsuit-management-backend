package com.avg.lawsuitmanagement.expense.controller.form;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Builder
public class ExpenseFileDataForm {
    private MultipartFile fileData;
}
