package com.avg.lawsuitmanagement.reception.service;

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

@Service
@RequiredArgsConstructor
public class ReceptionService {

    private final ReceptionMapperRepository receptionRepository;
    private final MemberService memberService;

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

    public ReceptionDto create(ReceptionCreateForm form) {
        MemberDto member = memberService.getLoginMemberInfo();
        ReceptionInsertParam param = form.toParam(member.getId());
        receptionRepository.insert(param);
        Long id = param.getId();
        ReceptionDto result = receptionRepository.selectById(id);
        return result;
    }

    public ReceptionDto edit(Long id, ReceptionEditForm form) {
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
