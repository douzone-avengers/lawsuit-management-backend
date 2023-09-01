package com.avg.lawsuitmanagement.lawsuit.service;

import com.avg.lawsuitmanagement.lawsuit.dto.IdNameDto;
import com.avg.lawsuitmanagement.lawsuit.dto.LawsuitPrintAdviceDto;
import com.avg.lawsuitmanagement.lawsuit.dto.LawsuitPrintAdvicePreDto;
import com.avg.lawsuitmanagement.lawsuit.dto.LawsuitPrintExpenseDto;
import com.avg.lawsuitmanagement.lawsuit.dto.LawsuitPrintLawsuitDto;
import com.avg.lawsuitmanagement.lawsuit.dto.LawsuitPrintRawDto;
import com.avg.lawsuitmanagement.lawsuit.dto.LawsuitPrintResponseDto;
import com.avg.lawsuitmanagement.lawsuit.repository.LawsuitMapperRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class LawsuitPdfService {

    private final LawsuitMapperRepository lawsuitMapperRepository;

    public LawsuitPrintResponseDto getPrintInfo(Long lawsuitId) {
        List<LawsuitPrintRawDto> raws = lawsuitMapperRepository.selectPrintInfo(
            lawsuitId);

        if (raws.isEmpty()) {
            throw new RuntimeException("");
        }
        LawsuitPrintRawDto lawsuit = raws.get(0);

        Map<Long, String> clientNameMap = new HashMap<>();
        Map<Long, String> memberNameMap = new HashMap<>();
        Map<Long, LawsuitPrintAdvicePreDto> adviceMap = new HashMap<>();
        Map<Long, LawsuitPrintExpenseDto> expenseMap = new HashMap<>();
        for (LawsuitPrintRawDto raw : raws) {
            Long clientId = raw.getClientId();
            if (clientId != null) {
                if (!clientNameMap.containsKey(clientId)) {
                    clientNameMap.put(clientId, raw.getClientName());
                }
            }

            Long memberId = raw.getMemberId();
            if (memberId != null) {
                if (!memberNameMap.containsKey(memberId)) {
                    memberNameMap.put(memberId, raw.getMemberName());
                }
            }

            Long adviceId = raw.getAdviceId();
            if (adviceId != null) {
                if (!adviceMap.containsKey(adviceId)) {
                    List<IdNameDto> memberIdNames = new ArrayList<>();
                    if (raw.getMemberName() != null) {
                        memberIdNames.add(IdNameDto.builder()
                            .id(raw.getMemberId())
                            .name(raw.getMemberName())
                            .build());
                    }
                    List<IdNameDto> clientIdNames = new ArrayList<>();
                    if (raw.getClientName() != null) {
                        clientIdNames.add(IdNameDto.builder()
                            .id(raw.getClientId())
                            .name(raw.getClientName())
                            .build());
                    }
                    adviceMap.put(adviceId, LawsuitPrintAdvicePreDto.builder()
                        .id(raw.getAdviceId())
                        .title(raw.getAdviceTitle())
                        .contents(raw.getAdviceContents())
                        .date(raw.getAdviceDate())
                        .memberIdNames(memberIdNames)
                        .clientIdNames(clientIdNames)
                        .build());
                } else {
                    LawsuitPrintAdvicePreDto lawsuitPrintAdvicePreDto = adviceMap.get(adviceId);

                    List<IdNameDto> memberIdNames = lawsuitPrintAdvicePreDto.getMemberIdNames();
                    IdNameDto memberIdName = IdNameDto.builder()
                        .id(raw.getMemberId())
                        .name(raw.getMemberName())
                        .build();
                    if (raw.getMemberName() != null && memberIdNames.stream()
                        .noneMatch(it -> it.getId()
                            .equals(memberIdName.getId()))) {
                        memberIdNames.add(memberIdName);
                    }

                    List<IdNameDto> clientIdNames = lawsuitPrintAdvicePreDto.getClientIdNames();
                    IdNameDto clientIdName = IdNameDto.builder()
                        .id(raw.getClientId())
                        .name(raw.getClientName())
                        .build();
                    if (raw.getClientName() != null && clientIdNames.stream()
                        .noneMatch(it -> it.getId()
                            .equals(clientIdName.getId()))) {
                        clientIdNames.add(clientIdName);
                    }
                }
            }

            Long expenseId = raw.getExpenseId();
            if (expenseId != null) {
                if (!expenseMap.containsKey(expenseId)) {
                    expenseMap.put(expenseId, LawsuitPrintExpenseDto.builder()
                        .id(raw.getExpenseId())
                        .contents(raw.getExpenseContents())
                        .amount(raw.getExpenseAmount())
                        .date(raw.getAdviceDate())
                        .build());
                }
            }
        }

        return LawsuitPrintResponseDto.builder()
            .lawsuit(LawsuitPrintLawsuitDto.builder()
                .id(lawsuit.getLawsuitId())
                .name(lawsuit.getLawsuitName())
                .num(lawsuit.getLawsuitNum())
                .type(lawsuit.getLawsuitType())
                .court(lawsuit.getCourtName())
                .commissionFee(lawsuit.getLawsuitCommissionFee())
                .contingentFee(lawsuit.getLawsuitContingentFee())
                .judgementResult(lawsuit.getLawsuitJudgementResult())
                .judgementDate(lawsuit.getLawsuitJudgementDate())
                .clients(clientNameMap.values().stream().toList())
                .members(memberNameMap.values().stream().toList())
                .build())
            .advices(adviceMap.values().stream().map(it -> LawsuitPrintAdviceDto.builder()
                    .id(it.getId())
                    .title(it.getTitle())
                    .contents(it.getContents())
                    .date(it.getDate())
                    .memberNames(it.getMemberIdNames().stream().map(IdNameDto::getName).toList())
                    .clientNames(it.getClientIdNames().stream().map(IdNameDto::getName).toList())
                    .build())
                .toList())
            .expenses(expenseMap.values().stream().toList())
            .build();
    }
}
