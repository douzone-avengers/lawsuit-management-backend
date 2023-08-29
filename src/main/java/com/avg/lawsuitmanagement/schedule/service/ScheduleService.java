package com.avg.lawsuitmanagement.schedule.service;

import com.avg.lawsuitmanagement.member.dto.MemberDto;
import com.avg.lawsuitmanagement.member.service.LoginUserInfoService;
import com.avg.lawsuitmanagement.schedule.controller.form.ScheduleSearchForm;
import com.avg.lawsuitmanagement.schedule.dto.ScheduleClientInfoDto;
import com.avg.lawsuitmanagement.schedule.dto.ScheduleDto;
import com.avg.lawsuitmanagement.schedule.dto.ScheduleInfoDto;
import com.avg.lawsuitmanagement.schedule.dto.ScheduleInfoRawDto;
import com.avg.lawsuitmanagement.schedule.dto.ScheduleLawsuitInfoDto;
import com.avg.lawsuitmanagement.schedule.dto.ScheduleMemberInfoDto;
import com.avg.lawsuitmanagement.schedule.dto.ScheduleRawDto;
import com.avg.lawsuitmanagement.schedule.dto.ScheduleReceptionInfoDto;
import com.avg.lawsuitmanagement.schedule.repository.ScheduleMapperRepository;
import com.avg.lawsuitmanagement.schedule.repository.param.ScheduleSelectParam;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleMapperRepository scheduleRepository;
    private final LoginUserInfoService loginUserInfoService;

    public List<ScheduleDto> search(ScheduleSearchForm form) {
        MemberDto member = loginUserInfoService.getLoginMemberInfo();
        ScheduleSelectParam param = form.toParam(member.getId());

        List<ScheduleRawDto> rawDto = scheduleRepository.select(param);

        Map<Long, List<String>> employeeNamesMap = rawDto.stream()
            .collect(Collectors.groupingBy(ScheduleRawDto::getReceptionId,
                Collectors.mapping(ScheduleRawDto::getEmployeeName, Collectors.toList())));

        List<ScheduleDto> result = rawDto.stream()
            .map(item -> ScheduleDto.builder()
                .receptionId(item.getReceptionId())
                .receptionStatus(item.getReceptionStatus())
                .deadline(item.getDeadline())
                .lawsuitId(item.getLawsuitId())
                .lawsuitType(item.getLawsuitType())
                .lawsuitName(item.getLawsuitName())
                .employeeNames(
                    employeeNamesMap.getOrDefault(item.getReceptionId(), Collections.emptyList()))
                .build())
            .collect(Collectors.toSet())
            .stream()
            .toList();

        return result;
    }

    public ScheduleInfoDto searchInfo(Long receptionId) {
        List<ScheduleInfoRawDto> rawDtos = scheduleRepository.selectInfo(receptionId);

        Map<Long, ScheduleClientInfoDto> clientMap = new HashMap<>();
        Map<Long, ScheduleMemberInfoDto> memberMap = new HashMap<>();

        for (ScheduleInfoRawDto rawDto : rawDtos) {
            clientMap.putIfAbsent(rawDto.getClientId(), ScheduleClientInfoDto.builder()
                .id(rawDto.getClientId())
                .name(rawDto.getClientName())
                .build());
            memberMap.putIfAbsent(rawDto.getMemberId(), ScheduleMemberInfoDto.builder()
                .id(rawDto.getMemberId())
                .name(rawDto.getMemberName())
                .build());
        }

        List<ScheduleClientInfoDto> clients = new ArrayList<>(clientMap.values());
        List<ScheduleMemberInfoDto> members = new ArrayList<>(memberMap.values());

        ScheduleInfoDto result = null;
        if (!rawDtos.isEmpty()) {
            ScheduleInfoRawDto item = rawDtos.get(0);
            result = ScheduleInfoDto.builder()
                .lawsuit(ScheduleLawsuitInfoDto.builder()
                    .id(item.getLawsuitId())
                    .num(item.getLawsuitNum())
                    .name(item.getLawsuitName())
                    .type(item.getLawsuitType())
                    .courtName(item.getCourtName())
                    .status(item.getLawsuitStatus())
                    .commissionFee(item.getLawsuitCommissionFee())
                    .contingentFee(item.getLawsuitContingentFee())
                    .build())
                .members(members)
                .clients(clients)
                .reception(ScheduleReceptionInfoDto.builder()
                    .id(item.getReceptionId())
                    .status(item.getReceptionStatus())
                    .category(item.getReceptionCategory())
                    .contents(item.getReceptionContents())
                    .receivedAt(item.getReceptionReceivedAt())
                    .deadline(item.getReceptionDeadline())
                    .build())
                .build();
        }

        return result;
    }

}
