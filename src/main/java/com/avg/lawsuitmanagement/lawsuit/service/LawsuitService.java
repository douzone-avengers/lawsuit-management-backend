package com.avg.lawsuitmanagement.lawsuit.service;

import static com.avg.lawsuitmanagement.common.exception.type.ErrorCode.LAWSUIT_NOT_FOUND;
import static com.avg.lawsuitmanagement.common.exception.type.ErrorCode.LAWSUIT_STATUS_NOT_FOUND;

import com.avg.lawsuitmanagement.client.dto.ClientDto;
import com.avg.lawsuitmanagement.client.repository.ClientMapperRepository;
import com.avg.lawsuitmanagement.common.custom.CustomRuntimeException;
import com.avg.lawsuitmanagement.common.exception.type.ErrorCode;
import com.avg.lawsuitmanagement.lawsuit.controller.form.InsertLawsuitForm;
import com.avg.lawsuitmanagement.lawsuit.controller.form.UpdateLawsuitInfoForm;
import com.avg.lawsuitmanagement.lawsuit.dto.LawsuitDto;
import com.avg.lawsuitmanagement.lawsuit.repository.LawsuitMapperRepository;
import com.avg.lawsuitmanagement.lawsuit.repository.param.InsertLawsuitParam;
import com.avg.lawsuitmanagement.lawsuit.repository.param.UpdateLawsuitInfoParam;
import com.avg.lawsuitmanagement.lawsuit.type.LawsuitStatus;
import com.avg.lawsuitmanagement.member.dto.MemberDto;
import com.avg.lawsuitmanagement.member.repository.MemberMapperRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class LawsuitService {
    private final ClientMapperRepository clientMapperRepository;
    private final MemberMapperRepository memberMapperRepository;
    private final LawsuitMapperRepository lawsuitMapperRepository;

    public void insertLawsuit(InsertLawsuitForm form) {
        List<Long> clientIdList = form.getClientId();
        if (clientIdList != null && !clientIdList.isEmpty()) {
            List<ClientDto> clientList = clientMapperRepository.selectClientListById(clientIdList);

            if (clientList.size() != form.getClientId().size()) {
                throw new CustomRuntimeException(ErrorCode.CLIENT_NOT_FOUND);
            }
        }

        List<Long> memberIdList = form.getMemberId();
        if (memberIdList != null && !memberIdList.isEmpty()) {
            List<MemberDto> memberList = memberMapperRepository.selectMemberListById(memberIdList);

            if (memberList.size() != form.getMemberId().size()) {
                throw new CustomRuntimeException(ErrorCode.MEMBER_NOT_FOUND);
            }
        }

        lawsuitMapperRepository.insertLawsuit(InsertLawsuitParam.of(form, LawsuitStatus.REGISTRATION));
    }

    public List<LawsuitDto> selectLawsuitList() {
        return lawsuitMapperRepository.selectLawsuitList();
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

        lawsuitMapperRepository.updateLawsuitInfo(UpdateLawsuitInfoParam.of(lawsuitId, form, lawsuitStatus));
    }

}
