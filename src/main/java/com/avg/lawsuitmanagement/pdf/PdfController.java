package com.avg.lawsuitmanagement.pdf;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pdfs")
public class PdfController {


    @PostMapping
    public ResponseEntity<?> save(
        @RequestBody PdfDto dto
    ) {
        byte[] pdfData = Base64.getDecoder().decode(dto.getData());
        try {
            String outputFilePath =
                "C:\\test\\" + dto.getName(); // Change this to your desired output path
            File outputFile = new File(outputFilePath);
            FileOutputStream outputStream = new FileOutputStream(outputFile);
            outputStream.write(pdfData);
            outputStream.close();
            System.out.println("PDF file has been created.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(null);
    }
}
