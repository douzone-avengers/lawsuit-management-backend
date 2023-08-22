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
import com.avg.lawsuitmanagement.lawsuit.dto.GetLawsuitListDto;
import com.avg.lawsuitmanagement.lawsuit.dto.LawsuitBasicDto;
import com.avg.lawsuitmanagement.lawsuit.dto.LawsuitBasicRawDto;
import com.avg.lawsuitmanagement.lawsuit.dto.LawsuitDto;
import com.avg.lawsuitmanagement.lawsuit.repository.LawsuitMapperRepository;
import com.avg.lawsuitmanagement.lawsuit.repository.param.InsertLawsuitClientMemberIdParam;
import com.avg.lawsuitmanagement.lawsuit.repository.param.InsertLawsuitParam;
import com.avg.lawsuitmanagement.lawsuit.repository.param.LawsuitStatusUpdateParam;
import com.avg.lawsuitmanagement.lawsuit.repository.param.SelectClientLawsuitListParam;
import com.avg.lawsuitmanagement.lawsuit.repository.param.SelectEmployeeLawsuitListParam;
import com.avg.lawsuitmanagement.lawsuit.repository.param.UpdateLawsuitInfoParam;
import com.avg.lawsuitmanagement.lawsuit.type.LawsuitStatus;
import com.avg.lawsuitmanagement.member.dto.MemberDto;
import com.avg.lawsuitmanagement.member.dto.MemberDtoNonPass;
import com.avg.lawsuitmanagement.member.repository.MemberMapperRepository;
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

    public GetLawsuitListDto selectClientLawsuitList(long clientId, GetClientLawsuitForm form) {
        ClientDto clientDto = clientMapperRepository.selectClientById(clientId);

        // 해당 clientId의 의뢰인이 없을 경우
        if (clientDto == null) {
            throw new CustomRuntimeException(CLIENT_NOT_FOUND);
        }

        PagingDto pagingDto = PagingUtil.calculatePaging(form.getCurPage(), form.getRowsPerPage());
        SelectClientLawsuitListParam param = SelectClientLawsuitListParam.of(clientId, pagingDto,
            form.getSearchWord());

        if (form.getCurPage() == null || form.getRowsPerPage() == null) {
            param.setOffset(0);
            param.setLimit(0);
        }
        // 한 페이지에 나타나는 사건 리스트 목록
        List<LawsuitDto> lawsuitList = lawsuitMapperRepository.selectClientLawsuitList(param);
        int count = lawsuitMapperRepository.selectClientLawsuitCountBySearchWord(param);

        return GetLawsuitListDto.builder()
            .lawsuitList(lawsuitList)
            .count(count)
            .build();
    }

    public GetLawsuitListDto selectEmployeeLawsuitList(long employeeId, GetEmployeeLawsuitForm form) {

        MemberDtoNonPass memberDto = memberMapperRepository.selectMemberById(employeeId);

        if (memberDto == null) {
            throw new CustomRuntimeException(MEMBER_NOT_FOUND);
        }

        PagingDto pagingDto = PagingUtil.calculatePaging(form.getCurPage(), form.getRowsPerPage());
        SelectEmployeeLawsuitListParam param = SelectEmployeeLawsuitListParam.of(employeeId, pagingDto,
            form.getSearchWord());

        if (form.getCurPage() == null || form.getRowsPerPage() == null) {
            param.setOffset(0);
            param.setLimit(0);
        }
        // 한 페이지에 나타나는 사건 리스트 목록
        List<LawsuitDto> lawsuitList = lawsuitMapperRepository.selectEmployeeLawsuitList(param);
        int count = lawsuitMapperRepository.selectEmployeeLawsuitCountBySearchWord(param);

        return GetLawsuitListDto.builder()
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
            InsertLawsuitClientMemberIdParam.of(lawsuitId, clientIdList, memberIdList));
        lawsuitMapperRepository.insertLawsuitMemberMap(
            InsertLawsuitClientMemberIdParam.of(lawsuitId, clientIdList, memberIdList));

    }

    public void updateLawsuitInfo(long lawsuitId, UpdateLawsuitInfoForm form) {
        LawsuitDto lawsuitDto = lawsuitMapperRepository.selectLawsuitById(lawsuitId);

        // lawsuitId에 해당하는 사건이 없다면
        if (lawsuitDto == null) {
            throw new CustomRuntimeException(LAWSUIT_NOT_FOUND);
        }

        // 클라이언트에서 받아온 사건상태 정보를 enum 클래스의 사건 상태들과 비교
        LawsuitStatus lawsuitStatus = null;
        for (LawsuitStatus status : LawsuitStatus.values()) {
            if (status.getStatusKr().equals(form.getLawsuit_status())) {
                lawsuitStatus = status;
            }
        }

        if (lawsuitStatus == null) {
            throw new CustomRuntimeException(LAWSUIT_STATUS_NOT_FOUND);
        }

        lawsuitMapperRepository.updateLawsuitInfo(
            UpdateLawsuitInfoParam.of(lawsuitId, form, lawsuitStatus));
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

        List<Long> memberIdList = lawsuitMapperRepository.selectMemberByLawsuitId(lawsuitId);
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
}

