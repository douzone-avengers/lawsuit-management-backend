package com.avg.lawsuitmanagement.member.controller;

import com.avg.lawsuitmanagement.member.dto.MemberDto;
import com.avg.lawsuitmanagement.member.dto.CreatePromotionKeyDto;
import com.avg.lawsuitmanagement.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("me")
    public ResponseEntity<MemberDto> getLoginUserInfo() {
        return ResponseEntity.ok(memberService.getLoginMemberInfo());
    }

    @PostMapping("/promotions/clients")
    public ResponseEntity<CreatePromotionKeyDto> getClientPromotionKey(long clientId) {
        return ResponseEntity.ok(memberService.getClientPromotionKey(clientId));
    }


}
