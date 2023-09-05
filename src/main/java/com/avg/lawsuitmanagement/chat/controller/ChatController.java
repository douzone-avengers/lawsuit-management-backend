package com.avg.lawsuitmanagement.chat.controller;

import com.avg.lawsuitmanagement.chat.dto.EmailBody;
import com.avg.lawsuitmanagement.chat.dto.UserSearchDetailResult;
import com.avg.lawsuitmanagement.chat.dto.UserSearchRaw;
import com.avg.lawsuitmanagement.chat.service.ChatService;
import com.avg.lawsuitmanagement.common.util.SecurityUtil;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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

    @GetMapping("/users")
    public ResponseEntity<?> searchUserByEmail(
        @RequestParam String email
    ) {
        UserSearchRaw body = chatService.searchUserByEmail(email);
        return ResponseEntity.ok(body);
    }

    @GetMapping("/users/detail")
    public ResponseEntity<?> searchUserDetailByEmail(
        @RequestParam String email
    ) {
        UserSearchDetailResult body = chatService.searchUserDetailByEmail(email);
        return ResponseEntity.ok(body);
    }

    @GetMapping("/friends/check")
    public ResponseEntity<?> checkFriend(
        @RequestParam(value = "user")
        String userEmail,
        @RequestParam(value = "friend")
        String friendEmail
    ) {
        Boolean body = chatService.checkFriend(userEmail, friendEmail);
        return ResponseEntity.ok(body);
    }

    @GetMapping("/friends")
    public ResponseEntity<?> searchFriendsByEmail(
        @RequestParam String email
    ) {
        List<UserSearchRaw> body = chatService.searchFriendByEmail(email);
        return ResponseEntity.ok(body);
    }


    @PostMapping("/friends")
    public ResponseEntity<?> addFriend(
        @RequestBody EmailBody body
    ) {
        String userEmail = SecurityUtil.getCurrentLoginEmail();
        String friendEmail = body.getEmail();
        chatService.addFriend(userEmail, friendEmail);
        return ResponseEntity.ok(null);
    }

    @PatchMapping("/friends/delete")
    public ResponseEntity<?> removeFriend(
        @RequestBody EmailBody body
    ) {
        String userEmail = SecurityUtil.getCurrentLoginEmail();
        String friendEmail = body.getEmail();
        chatService.removeFriend(userEmail, friendEmail);
        return ResponseEntity.ok(null);
    }

    @GetMapping("/rooms/one-to-one")
    public ResponseEntity<?> searchOneToOneRoomId(
        @RequestParam(value = "email") String friendEmail
    ) {
        String userEmail = SecurityUtil.getCurrentLoginEmail();
        Long id = chatService.searchOneToOneRoomId(userEmail, friendEmail);
        return ResponseEntity.ok(id);
    }

}
