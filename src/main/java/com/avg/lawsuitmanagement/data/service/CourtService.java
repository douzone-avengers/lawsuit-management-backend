package com.avg.lawsuitmanagement.data.service;

import com.avg.lawsuitmanagement.common.custom.CustomRuntimeException;
import com.avg.lawsuitmanagement.common.exception.type.ErrorCode;
import com.avg.lawsuitmanagement.data.dto.CourtDto;
import com.avg.lawsuitmanagement.data.repository.CourtMapperRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CourtService {
    private final CourtMapperRepository courtMapperRepository;

    public List<CourtDto> getCourtList() {
        return courtMapperRepository.selectCourtList();
    }

    public CourtDto getCourt(long id) {
        CourtDto courtDto = courtMapperRepository.selectCourt(id);
        if(courtDto == null) {
            throw new CustomRuntimeException(ErrorCode.COURT_NOT_FOUND);
        }
        return courtDto;
    }
}
