package com.avg.lawsuitmanagement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.avg.lawsuitmanagement.sample.SampleDto;
import com.avg.lawsuitmanagement.sample.SampleMapperRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
//@TestPropertySource(locations = "classpath:application-test.properties")
public class JunitTestSample {

    @Autowired
    SampleMapperRepository sampleMapperRepository;

    @Test
    @Transactional
    @DisplayName("sample 테이블 조회 테스트")
    public void selectSampleTest() {

        //given
        sampleMapperRepository.save(new SampleDto(null, "myData2083"));

        //when
        SampleDto dto = sampleMapperRepository.selectSampleByData("myData2083");


        //then
        assertNotNull(dto);
        assertEquals("myData2083", dto.getData());


    }

}
