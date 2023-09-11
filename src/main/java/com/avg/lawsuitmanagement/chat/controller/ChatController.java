package com.avg.lawsuitmanagement.chat.controller;

import com.avg.lawsuitmanagement.chat.dto.EmailBody;
import com.avg.lawsuitmanagement.chat.dto.MessageResponse;
import com.avg.lawsuitmanagement.chat.dto.RoomBasicResult;
import com.avg.lawsuitmanagement.chat.dto.RoomCreateForm;
import com.avg.lawsuitmanagement.chat.dto.RoomDetailResult;
import com.avg.lawsuitmanagement.chat.dto.UserBasicInfo;
import com.avg.lawsuitmanagement.chat.dto.UserWithLawsuitResult;
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
        UserBasicInfo body = chatService.searchUserByEmail(email);
        return ResponseEntity.ok().body(body);
    }

    @GetMapping("/users/employees")
    public ResponseEntity<?> searchEmployees() {
        List<UserBasicInfo> result = chatService.searchEmployees();
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/users/clients")
    public ResponseEntity<?> searchClients() {
        List<UserBasicInfo> result = chatService.searchClients();
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/users/detail")
    public ResponseEntity<?> searchUserDetailByEmail(
        @RequestParam String email
    ) {
        UserWithLawsuitResult body = chatService.searchUserDetailByEmail(email);
        return ResponseEntity.ok().body(body);
    }

    @GetMapping("/friends")
    public ResponseEntity<?> searchFriendsByEmail(
        @RequestParam String email
    ) {
        List<UserBasicInfo> body = chatService.searchFriendByEmail(email);
        return ResponseEntity.ok().body(body);
    }

    @GetMapping("/friends/check")
    public ResponseEntity<?> checkFriendByEmail(
        @RequestParam(value = "email") String friendEmail
    ) {
        String userEmail = SecurityUtil.getCurrentLoginEmail();
        Boolean body = chatService.checkFriendByEmail(userEmail, friendEmail);
        return ResponseEntity.ok().body(body);
    }


    @PostMapping("/friends")
    public ResponseEntity<?> addFriendByEmail(
        @RequestBody EmailBody body
    ) {
        String userEmail = SecurityUtil.getCurrentLoginEmail();
        String friendEmail = body.getEmail();
        chatService.addFriendByEmail(userEmail, friendEmail);
        return ResponseEntity.ok().body(null);
    }

    @PatchMapping("/friends/delete")
    public ResponseEntity<?> removeFriendByEmail(
        @RequestBody EmailBody body
    ) {
        String userEmail = SecurityUtil.getCurrentLoginEmail();
        String friendEmail = body.getEmail();
        chatService.removeFriendByEmail(userEmail, friendEmail);
        return ResponseEntity.ok().body(null);
    }

    @GetMapping("/rooms")
    public ResponseEntity<?> getAllRooms() {
        String email = SecurityUtil.getCurrentLoginEmail();
        List<RoomDetailResult> result = chatService.getAllRooms(email);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/rooms/one-to-one")
    public ResponseEntity<?> getOneToOneRoomByEmail(
        @RequestParam(value = "email") String friendEmail
    ) {
        String userEmail = SecurityUtil.getCurrentLoginEmail();
        RoomBasicResult result = chatService.getOneToOneRoomByEmail(userEmail,
            friendEmail);
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/rooms")
    public ResponseEntity<?> createRoom(
        @RequestBody RoomCreateForm form
    ) {
        RoomBasicResult result = chatService.createRoom(form);
        return ResponseEntity.ok().body(result);
    }
    
    @GetMapping("/messages")
    public ResponseEntity<?> getAllMessages(
        @RequestParam(value = "room") Long roomId
    ) {
        List<MessageResponse> result = chatService.getAllMessages(roomId);
        return ResponseEntity.ok().body(result);
    }

    @PatchMapping("/messages/read")
    public ResponseEntity<?> readAllMessage(
        @RequestParam(value = "room") Long roomId
    ) {
        String email = SecurityUtil.getCurrentLoginEmail();
        chatService.readAllMessage(email, roomId);
        return ResponseEntity.ok().body(null);
    }

    @GetMapping("/messages/unread/total")
    public ResponseEntity<?> countUnreadTotalCount() {
        String email = SecurityUtil.getCurrentLoginEmail();
        Long result = chatService.countUnreadTotalCount(email);
        return ResponseEntity.ok().body(result);
    }

}
