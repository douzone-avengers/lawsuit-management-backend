package com.avg.lawsuitmanagement.member.service;

import static com.avg.lawsuitmanagement.common.exception.type.ErrorCode.CLIENT_ALREADY_REGISTERED;
import static com.avg.lawsuitmanagement.common.exception.type.ErrorCode.MEMBER_EMAIL_ALREADY_EXIST;
import static com.avg.lawsuitmanagement.common.exception.type.ErrorCode.MEMBER_NOT_FOUND;

import com.avg.lawsuitmanagement.client.dto.ClientDto;
import com.avg.lawsuitmanagement.client.repository.ClientMapperRepository;
import com.avg.lawsuitmanagement.client.repository.param.UpdateClientMemberIdParam;
import com.avg.lawsuitmanagement.common.custom.CustomRuntimeException;
import com.avg.lawsuitmanagement.common.util.PagingUtil;
import com.avg.lawsuitmanagement.lawsuit.service.LawsuitService;
import com.avg.lawsuitmanagement.member.controller.form.ClientSignUpForm;
import com.avg.lawsuitmanagement.member.controller.form.EmployeeSignUpForm;
import com.avg.lawsuitmanagement.member.controller.form.MemberUpdateForm;
import com.avg.lawsuitmanagement.member.controller.form.PrivateUpdateForm;
import com.avg.lawsuitmanagement.member.controller.form.SearchEmployeeListForm;
import com.avg.lawsuitmanagement.member.dto.GetMemberListDto;
import com.avg.lawsuitmanagement.member.dto.MemberDto;
import com.avg.lawsuitmanagement.member.dto.MemberDtoNonPass;
import com.avg.lawsuitmanagement.member.repository.MemberMapperRepository;
import com.avg.lawsuitmanagement.member.repository.param.InsertMemberParam;
import com.avg.lawsuitmanagement.member.repository.param.SearchEmployeeListParam;
import com.avg.lawsuitmanagement.member.repository.param.UpdateMemberParam;
import com.avg.lawsuitmanagement.promotion.service.PromotionService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberMapperRepository memberMapperRepository;
    private final PromotionService promotionService;
    private final ClientMapperRepository clientMapperRepository;
    private final PasswordEncoder passwordEncoder;
    private final LoginUserInfoService loginUserInfoService;
    private final LawsuitService lawsuitService;

    public void updatePrivateInfo(PrivateUpdateForm form) {
        MemberDto me = loginUserInfoService.getLoginMemberInfo();
        memberMapperRepository.updateMember(UpdateMemberParam.of(form, me.getId()));
    }

    @Transactional
    public void clientSignUp(ClientSignUpForm form) {
        //1.가입키 검증
        ClientDto clientDto = promotionService.resolveClientPromotionKey(form.getPromotionKey());
        if (clientDto.getMemberId() != 0) {
            throw new CustomRuntimeException(CLIENT_ALREADY_REGISTERED);
        }
        //2. 프로모션 테이블 - 해당 키 비활성화
        promotionService.deactivateClientPromotion(form.getPromotionKey());

        //3. 회원 테이블 - 삽입
        long memberId = insertMember(InsertMemberParam.of(form, passwordEncoder));

        //4. 의뢰인 테이블 - 회원 id 갱신
        clientMapperRepository.updateClientMemberId(UpdateClientMemberIdParam.builder()
            .clientId(clientDto.getId())
            .memberId(memberId)
            .build());
    }

    @Transactional
    public void employeeSignUp(EmployeeSignUpForm form) {
        //1. 가입키 검증
        promotionService.validateEmployeePromotionKey(form.getPromotionKey());
        //2. 키 비활성화
        promotionService.deactivateEmployeePromotion(form.getPromotionKey());
        //3. 데이터 삽입
        insertMember(InsertMemberParam.of(form, passwordEncoder));
    }

    @Transactional
    public GetMemberListDto searchEmployeeList(SearchEmployeeListForm form) {

        SearchEmployeeListParam param;

        if (form.getPage() == null || form.getRowsPerPage() == null) {
            param = SearchEmployeeListParam.of(form);
        } else {
            param = SearchEmployeeListParam.of(form,
                PagingUtil.calculatePaging(form.getPage(), form.getRowsPerPage()));
        }

        List<MemberDtoNonPass> list =
            memberMapperRepository.selectEmployeeListBySearchCondition(param);
        int count = memberMapperRepository.selectEmployeeListBySearchConditionCount(param);

        return GetMemberListDto.builder()
            .count(count)
            .memberDtoNonPassList(list)
            .build();
    }

    public MemberDtoNonPass getMemberInfoById(long id) {
        MemberDtoNonPass dto = memberMapperRepository.selectMemberById(id);
        if(dto == null) {
            throw new CustomRuntimeException(MEMBER_NOT_FOUND);
        }
        return dto;
    }

    public void updateMemberInfo(long id, MemberUpdateForm form) {
        MemberDtoNonPass dto = memberMapperRepository.selectMemberById(id);
        if(dto == null) {
            throw new CustomRuntimeException(MEMBER_NOT_FOUND);
        }
        memberMapperRepository.updateMember(UpdateMemberParam.of(form, id));
    }

    public void deleteEmployeeFromLawsuit(long employeeId, long lawsuitId) {
        return;
    }

    private long insertMember(InsertMemberParam param) {
        //이메일 중복체크
        MemberDto member = memberMapperRepository.selectMemberByEmail(param.getEmail());
        if (member != null) {
            throw new CustomRuntimeException(MEMBER_EMAIL_ALREADY_EXIST);
        }
        memberMapperRepository.insertMember(param);
        return param.getId();
    }
}
