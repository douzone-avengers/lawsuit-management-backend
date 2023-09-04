package com.avg.lawsuitmanagement.chat.controller;

import com.avg.lawsuitmanagement.chat.dto.FriendAddForm;
import com.avg.lawsuitmanagement.chat.dto.UserSearchDetailResult;
import com.avg.lawsuitmanagement.chat.dto.UserSearchRaw;
import com.avg.lawsuitmanagement.chat.service.ChatService;
import com.avg.lawsuitmanagement.common.util.SecurityUtil;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chats")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @GetMapping("/user")
    public ResponseEntity<?> searchUserByEmail(
        @RequestParam String email
    ) {
        UserSearchRaw body = chatService.searchUserByEmail(email);
        return ResponseEntity.ok(body);
    }

    @GetMapping("/user/detail")
    public ResponseEntity<?> searchUserDetailByEmail(
        @RequestParam String email
    ) {
        UserSearchDetailResult body = chatService.searchUserDetailByEmail(email);
        return ResponseEntity.ok(body);
    }

    @GetMapping("/friends")
    public ResponseEntity<?> searchFriendsByEmail(
        @RequestParam String email
    ) {
        List<UserSearchRaw> body = chatService.searchFriendByEmail(email);
        return ResponseEntity.ok(body);
    }

    @PostMapping("/friend/add")
    public ResponseEntity<?> addFriend(
        @RequestBody FriendAddForm form
    ) {
        String userEmail = SecurityUtil.getCurrentLoginEmail();
        String friendEmail = form.getEmail();
        chatService.addFriend(userEmail, friendEmail);
        return ResponseEntity.ok(null);
    }

}
