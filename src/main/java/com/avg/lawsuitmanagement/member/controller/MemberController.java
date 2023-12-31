package com.avg.lawsuitmanagement.member.controller;

import com.avg.lawsuitmanagement.member.controller.form.ClientSignUpForm;
import com.avg.lawsuitmanagement.member.controller.form.EmployeeSignUpForm;
import com.avg.lawsuitmanagement.member.controller.form.MemberUpdateForm;
import com.avg.lawsuitmanagement.member.controller.form.PrivateUpdateForm;
import com.avg.lawsuitmanagement.member.controller.form.SearchEmployeeListForm;
import com.avg.lawsuitmanagement.member.dto.GetMemberListDto;
import com.avg.lawsuitmanagement.member.dto.MemberDto;
import com.avg.lawsuitmanagement.member.dto.MemberDtoNonPass;
import com.avg.lawsuitmanagement.member.service.LoginUserInfoService;
import com.avg.lawsuitmanagement.member.service.MemberService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;
    private final LoginUserInfoService loginUserInfoService;

    @GetMapping("/me")
    public ResponseEntity<MemberDto> getLoginUserInfo() {
        return ResponseEntity.ok(loginUserInfoService.getLoginMemberInfo());
    }

    @PutMapping("/me")
    public ResponseEntity<Void> updateLoginUserInfo(@RequestBody @Valid PrivateUpdateForm form) {
        memberService.updatePrivateInfo(form);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/clients")
    public ResponseEntity<Void> clientSignUp(@RequestBody @Valid ClientSignUpForm form) {
        memberService.clientSignUp(form);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/employees")
    public ResponseEntity<Void> employeeSignUp(@RequestBody @Valid EmployeeSignUpForm form) {
        memberService.employeeSignUp(form);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/employees")
    public ResponseEntity<GetMemberListDto> searchEmployee(
        @ModelAttribute SearchEmployeeListForm form) {
        if (form.getHierarchyId() == null) {
            form.setHierarchyId(0L);
        }
        if (form.getRoleId() == null) {
            form.setRoleId(0L);
        }
        return ResponseEntity.ok(memberService.searchEmployeeList(form));
    }

    @GetMapping("/employees/{employeeId}")
    public ResponseEntity<MemberDtoNonPass> getEmployee(@PathVariable long employeeId) {
        return ResponseEntity.ok(memberService.getMemberInfoById(employeeId));
    }

    @PutMapping("/employees/{employeeId}")
    public ResponseEntity<Void> updateEmployee(@PathVariable long employeeId, @RequestBody @Valid
    MemberUpdateForm form) {
        memberService.updateMemberInfo(employeeId, form);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/employees/{employeeId}/lawsuits/{lawsuitId}")
    public ResponseEntity<Void> deleteEmployeeFromLawsuit(@PathVariable long employeeId,
        @PathVariable long lawsuitId) {
        memberService.deleteEmployeeFromLawsuit(employeeId, lawsuitId);
        return ResponseEntity.ok().build();
    }
}
