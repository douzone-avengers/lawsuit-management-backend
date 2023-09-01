package com.avg.lawsuitmanagement.lawsuit.service;

import static com.avg.lawsuitmanagement.common.exception.type.ErrorCode.CLIENT_NOT_FOUND;
import static com.avg.lawsuitmanagement.common.exception.type.ErrorCode.LAWSUIT_NOT_FOUND;
import static com.avg.lawsuitmanagement.common.exception.type.ErrorCode.LAWSUIT_STATUS_NOT_FOUND;
import static com.avg.lawsuitmanagement.common.exception.type.ErrorCode.MEMBER_NOT_ASSIGNED_TO_LAWSUIT;
import static com.avg.lawsuitmanagement.common.exception.type.ErrorCode.MEMBER_NOT_FOUND;

import com.avg.lawsuitmanagement.client.dto.ClientDto;
import com.avg.lawsuitmanagement.client.repository.ClientMapperRepository;
import com.avg.lawsuitmanagement.common.custom.CustomRuntimeException;
import com.avg.lawsuitmanagement.common.util.PagingUtil;
import com.avg.lawsuitmanagement.common.util.SecurityUtil;
import com.avg.lawsuitmanagement.common.util.dto.PagingDto;
import com.avg.lawsuitmanagement.lawsuit.controller.form.GetClientLawsuitForm;
import com.avg.lawsuitmanagement.lawsuit.controller.form.GetEmployeeLawsuitForm;
import com.avg.lawsuitmanagement.lawsuit.controller.form.InsertLawsuitForm;
import com.avg.lawsuitmanagement.lawsuit.controller.form.UpdateLawsuitInfoForm;
import com.avg.lawsuitmanagement.lawsuit.dto.BasicLawsuitDto;
import com.avg.lawsuitmanagement.lawsuit.dto.BasicUserDto;
import com.avg.lawsuitmanagement.lawsuit.dto.GetClientLawsuitListDto;
import com.avg.lawsuitmanagement.lawsuit.dto.GetEmployeeLawsuitListDto;
import com.avg.lawsuitmanagement.lawsuit.dto.IdNameDto;
import com.avg.lawsuitmanagement.lawsuit.dto.LawsuitBasicDto;
import com.avg.lawsuitmanagement.lawsuit.dto.LawsuitBasicRawDto;
import com.avg.lawsuitmanagement.lawsuit.dto.LawsuitCountDto;
import com.avg.lawsuitmanagement.lawsuit.dto.LawsuitDto;
import com.avg.lawsuitmanagement.lawsuit.dto.LawsuitPrintAdviceDto;
import com.avg.lawsuitmanagement.lawsuit.dto.LawsuitPrintAdvicePreDto;
import com.avg.lawsuitmanagement.lawsuit.dto.LawsuitPrintExpenseDto;
import com.avg.lawsuitmanagement.lawsuit.dto.LawsuitPrintLawsuitDto;
import com.avg.lawsuitmanagement.lawsuit.dto.LawsuitPrintRawDto;
import com.avg.lawsuitmanagement.lawsuit.dto.LawsuitPrintResponseDto;
import com.avg.lawsuitmanagement.lawsuit.repository.LawsuitMapperRepository;
import com.avg.lawsuitmanagement.lawsuit.repository.param.LawsuitClientMemberIdParam;
import com.avg.lawsuitmanagement.lawsuit.repository.param.InsertLawsuitParam;
import com.avg.lawsuitmanagement.lawsuit.repository.param.LawsuitStatusUpdateParam;
import com.avg.lawsuitmanagement.lawsuit.repository.param.SelectClientLawsuitListParam;
import com.avg.lawsuitmanagement.lawsuit.repository.param.SelectEmployeeLawsuitListParam;
import com.avg.lawsuitmanagement.lawsuit.repository.param.UpdateLawsuitInfoParam;
import com.avg.lawsuitmanagement.lawsuit.type.LawsuitStatus;
import com.avg.lawsuitmanagement.member.dto.MemberDto;
import com.avg.lawsuitmanagement.member.dto.MemberDtoNonPass;
import com.avg.lawsuitmanagement.member.repository.MemberMapperRepository;
import com.avg.lawsuitmanagement.member.service.LoginUserInfoService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class LawsuitService {

    private final ClientMapperRepository clientMapperRepository;
    private final MemberMapperRepository memberMapperRepository;
    private final LawsuitMapperRepository lawsuitMapperRepository;
    private final LoginUserInfoService loginUserInfoService;

    public GetClientLawsuitListDto selectClientLawsuitList(long clientId,
        GetClientLawsuitForm form) {
        ClientDto clientDto = clientMapperRepository.selectClientById(clientId);

        // 해당 clientId의 의뢰인이 없을 경우
        if (clientDto == null) {
            throw new CustomRuntimeException(CLIENT_NOT_FOUND);
        }

        SelectClientLawsuitListParam param = getParam(form, clientId);

        // 한 페이지에 나타나는 사건 리스트 목록
        List<LawsuitDto> lawsuitList = lawsuitMapperRepository.selectClientLawsuitList(param);
        LawsuitCountDto countDto = lawsuitMapperRepository.countLawsuitsStatusByClientId(param);

        return GetClientLawsuitListDto.builder()
            .lawsuitList(lawsuitList)
            .countDto(countDto)
            .build();
    }

    public GetEmployeeLawsuitListDto selectEmployeeLawsuitList(long employeeId,
        GetEmployeeLawsuitForm form) {

        MemberDtoNonPass memberDto = memberMapperRepository.selectMemberById(employeeId);

        if (memberDto == null) {
            throw new CustomRuntimeException(MEMBER_NOT_FOUND);
        }

        PagingDto pagingDto = PagingUtil.calculatePaging(form.getCurPage(), form.getRowsPerPage());
        SelectEmployeeLawsuitListParam param = SelectEmployeeLawsuitListParam.of(employeeId,
            pagingDto,
            form.getSearchWord());

        if (form.getCurPage() == null || form.getRowsPerPage() == null) {
            param.setOffset(0);
            param.setLimit(0);
        }
        // 한 페이지에 나타나는 사건 리스트 목록
        List<LawsuitDto> lawsuitList = lawsuitMapperRepository.selectEmployeeLawsuitList(param);
        int count = lawsuitMapperRepository.selectEmployeeLawsuitCountBySearchWord(param);

        return GetEmployeeLawsuitListDto.builder()
            .lawsuitList(lawsuitList)
            .count(count)
            .build();
    }

    @Transactional
    public void insertLawsuit(InsertLawsuitForm form) {
        // clientIdList에 입력한 의뢰인의 id가 하나라도 없을 때
        List<Long> clientIdList = form.getClientId();
        if (clientIdList != null && !clientIdList.isEmpty()) {
            List<ClientDto> clientList = clientMapperRepository.selectClientListById(clientIdList);

            if (clientList.size() != form.getClientId().size()) {
                throw new CustomRuntimeException(CLIENT_NOT_FOUND);
            }
        }

        // memberIdList에 입력한 직원의 id가 하나라도 없을 때
        List<Long> memberIdList = form.getMemberId();
        if (memberIdList != null && !memberIdList.isEmpty()) {
            List<MemberDto> memberList = memberMapperRepository.selectMemberListById(memberIdList);

            if (memberList.size() != form.getMemberId().size()) {
                throw new CustomRuntimeException(MEMBER_NOT_FOUND);
            }
        }
        lawsuitMapperRepository.insertLawsuit(
            InsertLawsuitParam.of(form, LawsuitStatus.REGISTRATION));

        // 등록한 사건의 id값
        long lawsuitId = lawsuitMapperRepository.getLastInsertedLawsuitId();
        LawsuitDto lawsuitDto = lawsuitMapperRepository.selectLawsuitById(lawsuitId);

        // 사건 id에 해당하는 사건이 없을 경우
        if (lawsuitDto == null) {
            throw new CustomRuntimeException(LAWSUIT_NOT_FOUND);
        }

        lawsuitMapperRepository.insertLawsuitClientMap(
            LawsuitClientMemberIdParam.of(lawsuitId, clientIdList, memberIdList));
        lawsuitMapperRepository.insertLawsuitMemberMap(
            LawsuitClientMemberIdParam.of(lawsuitId, clientIdList, memberIdList));

    }

    // 사건 수정
    @Transactional
    public void updateLawsuitInfo(long lawsuitId, UpdateLawsuitInfoForm form) {
        // 기존에 등록된 멤버 id 리스트
        List<Long> originMemberIdList = memberMapperRepository.selectMemberIdListByLawsuitId(lawsuitId);
        List<Long> originClientIdList = clientMapperRepository.selectClientIdListByLawsuitId(lawsuitId);

        // 로그인한 사용자가 해당 사건의 담당자 또는 관리자일 때
        if (isUserAuthorizedForLawsuit(loginUserInfoService.getLoginMemberInfo().getId(), originMemberIdList)) {
            LawsuitDto lawsuitDto = lawsuitMapperRepository.selectLawsuitById(lawsuitId);

            // lawsuitId에 해당하는 사건이 없다면
            if (lawsuitDto == null) {
                throw new CustomRuntimeException(LAWSUIT_NOT_FOUND);
            }

            // 클라이언트에서 받아온 사건상태 정보를 enum 클래스의 사건 상태들과 비교
            LawsuitStatus lawsuitStatus = null;
            for (LawsuitStatus status : LawsuitStatus.values()) {
                if (status.getStatusKr().equals(form.getLawsuitStatus())) {
                    lawsuitStatus = status;
                }
            }

            if (lawsuitStatus == null) {
                throw new CustomRuntimeException(LAWSUIT_STATUS_NOT_FOUND);
            }

            lawsuitMapperRepository.updateLawsuitInfo(
                UpdateLawsuitInfoParam.of(lawsuitId, form, lawsuitStatus));

            List<Long> insertMemberIdList = new ArrayList<Long>(form.getMemberId());
            List<Long> deleteMemberIdList = new ArrayList<Long>();
            List<Long> insertClientIdList = new ArrayList<Long>(form.getClientId());
            List<Long> deleteClientIdList = new ArrayList<Long>();

            // 수정된 멤버 id에 기존에 등록된 멤버 id가 없으면 해당 id delete
            for (long memberId : originMemberIdList) {
                if (!form.getMemberId().contains(memberId)) {
                    deleteMemberIdList.add(memberId);
                }
            }

            // 수정된 의뢰인 id에 기존에 등록된 의뢰인 id가 없으면 해당 id delete
            for (long clientId : originClientIdList) {
                if (!form.getClientId().contains(clientId)) {
                    deleteClientIdList.add(clientId);
                }
            }

            LawsuitClientMemberIdParam deleteParam = LawsuitClientMemberIdParam.of(lawsuitId, deleteClientIdList, deleteMemberIdList);
            lawsuitMapperRepository.deleteMemberLawsuitMemberMap(deleteParam);
            lawsuitMapperRepository.deleteClientLawsuitClientMap(deleteParam);

            LawsuitClientMemberIdParam insertParam = LawsuitClientMemberIdParam.of(lawsuitId, insertClientIdList, insertMemberIdList);
            lawsuitMapperRepository.insertLawsuitMemberMap(insertParam);
            lawsuitMapperRepository.updateLawsuitMemberMap(insertParam);
            lawsuitMapperRepository.insertLawsuitClientMap(insertParam);
            lawsuitMapperRepository.updateLawsuitClientMap(insertParam);
        } else {
            throw new CustomRuntimeException(MEMBER_NOT_ASSIGNED_TO_LAWSUIT);
        }
    }

    @Transactional
    public void deleteLawsuitInfo(long lawsuitId) {
        LawsuitDto lawsuitDto = lawsuitMapperRepository.selectLawsuitById(lawsuitId);

        // lawsuitId에 해당하는 사건이 없다면
        if (lawsuitDto == null) {
            throw new CustomRuntimeException(LAWSUIT_NOT_FOUND);
        }

        // 해당 사건의 담당자가 아니라면
        String email = SecurityUtil.getCurrentLoginEmail();
        MemberDto loginMemberInfo = memberMapperRepository.selectMemberByEmail(email);

        List<Long> memberIdList = memberMapperRepository.selectMemberIdListByLawsuitId(lawsuitId);
        if (!memberIdList.contains(loginMemberInfo.getId())) {
            throw new CustomRuntimeException(MEMBER_NOT_ASSIGNED_TO_LAWSUIT);
        }

        lawsuitMapperRepository.deleteLawsuitInfo(lawsuitId);
        lawsuitMapperRepository.deleteLawsuitClientMap(lawsuitId);
        lawsuitMapperRepository.deleteLawsuitMemberMap(lawsuitId);
    }


    public LawsuitBasicDto getBasicLawsuitInfo(Long lawsuitId) {
        List<LawsuitBasicRawDto> raws = lawsuitMapperRepository.selectBasicLawInfo(
            lawsuitId);

        if (raws.isEmpty()) {
            throw new RuntimeException("");
        }

        LawsuitBasicRawDto lawsuitItem = raws.get(0);
        BasicLawsuitDto lawsuit = BasicLawsuitDto.builder()
            .lawsuitId(lawsuitItem.getLawsuitId())
            .lawsuitNum(lawsuitItem.getLawsuitNum())
            .lawsuitName(lawsuitItem.getLawsuitName())
            .lawsuitType(lawsuitItem.getLawsuitType())
            .lawsuitCommissionFee(lawsuitItem.getLawsuitCommissionFee())
            .lawsuitContingentFee(lawsuitItem.getLawsuitContingentFee())
            .lawsuitStatus(lawsuitItem.getLawsuitStatus())
            .courtName(lawsuitItem.getCourtName())
            .build();

        Map<Long, BasicUserDto> clientMap = new HashMap<>();
        Map<Long, BasicUserDto> employeeMap = new HashMap<>();
        for (LawsuitBasicRawDto raw : raws) {
            Long clientId = raw.getClientId();
            BasicUserDto client = BasicUserDto.builder()
                .id(clientId)
                .name(raw.getClientName())
                .email(raw.getClientEmail())
                .build();
            clientMap.put(clientId, client);

            Long employeeId = raw.getEmployeeId();
            BasicUserDto employee = BasicUserDto.builder()
                .id(employeeId)
                .name(raw.getEmployeeName())
                .email(raw.getEmployeeEmail())
                .build();
            employeeMap.put(employeeId, employee);
        }

        LawsuitBasicDto result = LawsuitBasicDto.builder()
            .lawsuit(lawsuit)
            .employees(employeeMap.values().stream().toList())
            .clients(clientMap.values().stream().toList())
            .build();

        return result;
    }

    public void updateStatus(Long id, LawsuitStatus status) {
        lawsuitMapperRepository.updateLawsuitStatus(LawsuitStatusUpdateParam.builder()
            .id(id)
            .status(status.toString())
            .build());
    }

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
                            .id(raw.getAdviceMemberId())
                            .name(raw.getAdviceMemberName())
                            .build());
                    }
                    List<IdNameDto> clientIdNames = new ArrayList<>();
                    if (raw.getClientName() != null) {
                        clientIdNames.add(IdNameDto.builder()
                            .id(raw.getAdviceClientId())
                            .name(raw.getAdviceClientName())
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
                        .id(raw.getAdviceMemberId())
                        .name(raw.getAdviceMemberName())
                        .build();
                    if (raw.getMemberName() != null && memberIdNames.stream()
                        .noneMatch(it -> it.getId()
                            .equals(memberIdName.getId()))) {
                        memberIdNames.add(memberIdName);
                    }

                    List<IdNameDto> clientIdNames = lawsuitPrintAdvicePreDto.getClientIdNames();
                    IdNameDto clientIdName = IdNameDto.builder()
                        .id(raw.getAdviceClientId())
                        .name(raw.getAdviceClientName())
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

        LawsuitPrintResponseDto result = LawsuitPrintResponseDto.builder()
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

        return result;
    }

    private SelectClientLawsuitListParam getParam(GetClientLawsuitForm form, long clientId) {

        if (form.getCurPage() == null || form.getRowsPerPage() == null) {
            return SelectClientLawsuitListParam.of(clientId, form.getSearchWord(),
                form.getLawsuitStatus());
        }

        return SelectClientLawsuitListParam.of(clientId,
            PagingUtil.calculatePaging(form.getCurPage(), form.getRowsPerPage()),
            form.getSearchWord(), form.getLawsuitStatus(), form.getSortKey(), form.getSortOrder());
    }

    private boolean isUserAuthorizedForLawsuit(long userId, List<Long> memberIds) {
        if (SecurityUtil.getCurrentLoginRoleList().contains("ROLE_ADMIN")) {
            return true;
        }

        // 로그인한 사용자가 해당 사건의 담당자가 아니라면
        return memberIds.contains(userId);
    }
}
