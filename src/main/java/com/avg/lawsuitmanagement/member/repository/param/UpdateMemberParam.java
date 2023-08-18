package com.avg.lawsuitmanagement.member.repository.param;

import com.avg.lawsuitmanagement.member.controller.form.MemberUpdateForm;
import com.avg.lawsuitmanagement.member.controller.form.PrivateUpdateForm;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UpdateMemberParam {

    private long id;
    private String email;
    private String name;
    private String phone;
    private String address;
    private long hierarchyId;
    private long roleId;

    public static UpdateMemberParam of(PrivateUpdateForm form, long id) {
        return UpdateMemberParam.builder()
            .id(id)
            .email(form.getEmail())
            .name(form.getName())
            .phone(form.getPhone())
            .address(form.getAddress())
            .hierarchyId(form.getHierarchyId())
            .roleId(form.getRoleId())
            .build();
    }

    public static UpdateMemberParam of(MemberUpdateForm form, long id) {
        return UpdateMemberParam.builder()
            .id(id)
            .email(form.getEmail())
            .name(form.getName())
            .phone(form.getPhone())
            .address(form.getAddress())
            .hierarchyId(form.getHierarchyId())
            .roleId(form.getRoleId())
            .build();
    }
}