package com.avg.lawsuitmanagement.lawsuit.service;

import com.avg.lawsuitmanagement.common.custom.CustomRuntimeException;
import com.avg.lawsuitmanagement.common.exception.type.ErrorCode;
import com.avg.lawsuitmanagement.lawsuit.dto.mail.BillMailDto;
import com.avg.lawsuitmanagement.lawsuit.dto.mail.LawsuitBookMailDto;
import java.io.File;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class LawsuitMailService {

    @Value("${spring.mail.username}")
    private String from;
    private final JavaMailSender javaMailSender;

    public void sendLawsuitBook(LawsuitBookMailDto dto) {

        if(dto.getFullFilePath() == null || dto.getFullFilePath().isEmpty()) {
            throw new CustomRuntimeException(ErrorCode.CANNOT_SEND_MAIL);
        }

        //메일 전송
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            helper.setTo(dto.getToList().toArray(new String[0]));
            helper.setSubject("[더존 사건관리 서비스] <" + dto.getLawsuitDto().getName() + "> 사건 기록책 입니다.");
            helper.setFrom(new InternetAddress(from, "더존 사건관리 서비스"));

            StringBuilder text = new StringBuilder();
            text.append("<div style='margin:100px;'>");
            text.append("<div align='center' style='border:1px solid black; font-family:verdana';>");
            text.append("<h2'> 사건명 : ").append(dto.getLawsuitDto().getName())
                .append("</h2>");
            text.append("<h2'> 사건번호 : ").append(dto.getLawsuitDto().getLawsuitNum())
                .append("</h2>");
            text.append("해당 사건에 대한 사건기록책을 첨부합니다.").append("</br>").append(" 감사합니다.");
            text.append("</div>");

            helper.setText(text.toString(), true);

            FileSystemResource file = new FileSystemResource(new File(dto.getFullFilePath()));
            String attachFileName = "[" + dto.getLawsuitDto().getLawsuitNum() + "] 사건 기록책";
            helper.addAttachment(attachFileName, file);

            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new CustomRuntimeException(ErrorCode.CANNOT_SEND_MAIL);
        }
    }

    public void sendBill(BillMailDto dto) {

        if(dto.getFullFilePath() == null || dto.getFullFilePath().isEmpty()) {
            throw new CustomRuntimeException(ErrorCode.CANNOT_SEND_MAIL);
        }

        //메일 전송
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            helper.setTo(dto.getToList().toArray(new String[0]));
            helper.setSubject("[더존 사건관리 서비스] <" + dto.getLawsuitDto().getName() + "> 사건의 청구서입니다.");
            helper.setFrom(new InternetAddress(from, "더존 사건관리 서비스"));

            StringBuilder text = new StringBuilder();
            text.append("<div style='margin:100px;'>");
            text.append("<div align='center' style='border:1px solid black; font-family:verdana';>");
            text.append("<h2'> 사건명 : ").append(dto.getLawsuitDto().getName())
                .append("</h2>");
            text.append("<h2'> 사건번호 : ").append(dto.getLawsuitDto().getLawsuitNum())
                .append("</h2>");
            text.append("해당 사건에 대한 청구서를 첨부합니다.").append("</br>").append(" 감사합니다.");
            text.append("</div>");

            helper.setText(text.toString(), true);

            FileSystemResource file = new FileSystemResource(new File(dto.getFullFilePath()));
            String attachFileName = "[" + dto.getLawsuitDto().getLawsuitNum() + "] 사건 청구서";
            helper.addAttachment(attachFileName, file);

            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new CustomRuntimeException(ErrorCode.CANNOT_SEND_MAIL);
        }
    }

}
