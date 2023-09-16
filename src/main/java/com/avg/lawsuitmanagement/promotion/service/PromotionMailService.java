package com.avg.lawsuitmanagement.promotion.service;

import com.avg.lawsuitmanagement.common.custom.CustomRuntimeException;
import com.avg.lawsuitmanagement.common.exception.type.ErrorCode;
import com.avg.lawsuitmanagement.promotion.dto.ClientPromotionMailDto;
import com.avg.lawsuitmanagement.promotion.dto.EmployeePromotionMailDto;
import java.io.UnsupportedEncodingException;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PromotionMailService {

    private final String VALIDATE_URL = "https://lawsuit-management.online/validate";
    @Value("${spring.mail.username}")
    private String from;
    private final JavaMailSender javaMailSender;

    public void sendClientPromotionMail(ClientPromotionMailDto dto) {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            mimeMessage.addRecipients(RecipientType.TO, dto.getTo());
            mimeMessage.setSubject(
                "[더존 사건관리 서비스] " + dto.getIssuer() + " 님이 " + dto.getClientName()
                    + " 님을 사건관리 서비스로 초대합니다.");

            String text = "<div style='margin:100px;'>"
                + "<div align='center' style='border:1px solid black; font-family:verdana';>"
                + "<h2'>" + dto.getClientName()
                + "(" + dto.getClientPhone() + ")"
                + " 고객님 환영합니다!!</h2>"
                + "<h3 style='color:blue;'>회원가입 코드입니다.</h3>"
                + "<div style='font-size:130%'>"
                + "CODE : <strong>"
                + dto.getPromotionKey() + "</strong><div><br/> "
                + "<a href=" + VALIDATE_URL + ">가입페이지로 이동 </a>"
                + "</div>";

            mimeMessage.setText(text, "utf-8", "html");
            mimeMessage.setFrom(new InternetAddress(from, "더존 사건관리 서비스"));

            javaMailSender.send(mimeMessage);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new CustomRuntimeException(ErrorCode.CANNOT_SEND_MAIL);
        }
    }

    public void sendEmployeePromotionMail(EmployeePromotionMailDto dto) {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            mimeMessage.addRecipients(RecipientType.TO, dto.getTo());
            mimeMessage.setSubject(
                "[더존 사건관리 서비스] " + dto.getIssuer() + " 님이 직원초대 코드를 발급하였습니다.");

            String text = "<div style='margin:100px;'>"
                + "<div align='center' style='border:1px solid black; font-family:verdana';>"
                + "<h2'>" + dto.getIssuer()
                + " 님이 직원초대 코드를 발급하였습니다.</h2>"
                + "<h3 style='color:blue;'>회원가입 코드입니다.</h3>"
                + "<div style='font-size:130%'>"
                + "CODE : <strong>"
                + dto.getPromotionKey() + "</strong><div><br/> "
                + "<a href=" + VALIDATE_URL + ">가입페이지로 이동 </a>"
                + "</div>";

            mimeMessage.setText(text, "utf-8", "html");
            mimeMessage.setFrom(new InternetAddress(from, "더존 사건관리 서비스"));

            javaMailSender.send(mimeMessage);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new CustomRuntimeException(ErrorCode.CANNOT_SEND_MAIL);
        }
    }
}