package com.avg.lawsuitmanagement.sample;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SampleMapperRepository {

    void save(SampleDto sampleDto);
    List<SampleDto> selectAll();

    SampleDto selectSampleByData(String data);
}