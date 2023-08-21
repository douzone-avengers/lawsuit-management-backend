package com.avg.lawsuitmanagement.mail;

import com.avg.lawsuitmanagement.promotion.dto.ClientPromotionMailDto;
import com.avg.lawsuitmanagement.promotion.service.MailService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MailServiceTest {

    @Autowired
    MailService mailService;

    @Test
    @DisplayName("메일 서비스 테스트")
    void mailingSuccess() {

        //given
        ClientPromotionMailDto clientPromotionMailDto = ClientPromotionMailDto.builder()
            .to("ksj2083@naver.com")
            .issuer("김네종")
            .promotionKey("vafefeasr2")
            .clientName("김길동")
            .clientPhone("010-4848-1212")
            .build();

        //when then
        Assertions.assertThatCode(() -> mailService.sendClientPromotionMail(clientPromotionMailDto))
            .doesNotThrowAnyException();
    }
}
