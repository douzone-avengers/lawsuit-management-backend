package com.avg.lawsuitmanagement.member.repository.param;

import com.avg.lawsuitmanagement.member.controller.form.ClientSignUpForm;
import com.avg.lawsuitmanagement.member.controller.form.EmployeeSignUpForm;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Setter
@Builder
public class InsertMemberParam {

    private long id; //for selectKey
    private String email;
    private String password;
    private String name;
    private String phone;
    private long hierarchyId;
    private String address;
    private String addressDetail;
    private long roleId;

    public static InsertMemberParam of(ClientSignUpForm form, PasswordEncoder passwordEncoder) {
        return InsertMemberParam.builder()
            .email(form.getEmail())
            .password(passwordEncoder.encode(form.getPassword()))
            .name(form.getName())
            .phone(form.getPhone())
            .hierarchyId(1) //고민 필요
            .address(form.getAddress())
            .addressDetail(form.getAddressDetail())
            .roleId(1) //고민필요
            .build();
    }

    public static InsertMemberParam of(EmployeeSignUpForm form, PasswordEncoder passwordEncoder) {
        return InsertMemberParam.builder()
            .email(form.getEmail())
            .password(passwordEncoder.encode(form.getPassword()))
            .name(form.getName())
            .phone(form.getPhone())
            .hierarchyId(form.getHierarchyId())
            .address(form.getAddress())
            .addressDetail(form.getAddressDetail())
            .roleId(form.getRoleId())
            .build();
    }
}