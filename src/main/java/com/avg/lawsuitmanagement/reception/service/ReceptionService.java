package com.avg.lawsuitmanagement.reception.service;

import com.avg.lawsuitmanagement.lawsuit.service.LawsuitService;
import com.avg.lawsuitmanagement.lawsuit.type.LawsuitStatus;
import com.avg.lawsuitmanagement.member.dto.MemberDto;
import com.avg.lawsuitmanagement.member.service.MemberService;
import com.avg.lawsuitmanagement.reception.controller.form.ReceptionCreateForm;
import com.avg.lawsuitmanagement.reception.controller.form.ReceptionEditForm;
import com.avg.lawsuitmanagement.reception.controller.form.ReceptionSearchForm;
import com.avg.lawsuitmanagement.reception.dto.ReceptionDto;
import com.avg.lawsuitmanagement.reception.repository.ReceptionMapperRepository;
import com.avg.lawsuitmanagement.reception.repository.param.ReceptionInsertParam;
import com.avg.lawsuitmanagement.reception.repository.param.ReceptionSelectParam;
import com.avg.lawsuitmanagement.reception.repository.param.ReceptionUpdateParam;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReceptionService {

    private final ReceptionMapperRepository receptionRepository;
    private final MemberService memberService;
    private final LawsuitService lawsuitService;

    public List<ReceptionDto> search(ReceptionSearchForm form) {
        ReceptionSelectParam param = form.toParam();
        List<ReceptionDto> result = receptionRepository.select(param);
        return result;
    }

    public Long searchSize(ReceptionSearchForm form) {
        ReceptionSelectParam param = form.toParam();
        Long size = receptionRepository.searchCount(param);
        return size;
    }

    @Transactional
    public ReceptionDto create(ReceptionCreateForm form) {
        MemberDto member = memberService.getLoginMemberInfo();
        ReceptionInsertParam param = form.toParam(member.getId());
        receptionRepository.insert(param);
        Long id = param.getId();
        ReceptionDto result = receptionRepository.selectById(id);
        lawsuitService.updateStatus(form.getLawsuitId(), LawsuitStatus.PROCEEDING);
        return result;
    }

    public ReceptionDto edit(Long id, ReceptionEditForm form) {
        if ("incomplete".equals(form.getStatus())) {
            form.setReceivedAt(null);
        }
        ReceptionUpdateParam param = form.toParam(id);
        receptionRepository.update(param);
        ReceptionDto result = receptionRepository.selectById(id);
        return result;
    }

    public void remove(Long id) {
        receptionRepository.delete(id);
    }

    public Long size() {
        return receptionRepository.count();
    }

}
