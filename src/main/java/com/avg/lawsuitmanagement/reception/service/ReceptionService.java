package com.avg.lawsuitmanagement.reception.service;

import com.avg.lawsuitmanagement.reception.repository.ReceptionMapperRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReceptionService {

    private ReceptionMapperRepository receptionRepository;

}
