package com.avg.lawsuitmanagement.chat.service;

import com.avg.lawsuitmanagement.chat.dto.LawsuitBasicInfo;
import com.avg.lawsuitmanagement.chat.dto.MessageCreateParam;
import com.avg.lawsuitmanagement.chat.dto.MessageRaw;
import com.avg.lawsuitmanagement.chat.dto.MessageRawWithRead;
import com.avg.lawsuitmanagement.chat.dto.MessageResponse;
import com.avg.lawsuitmanagement.chat.dto.MessageUserId;
import com.avg.lawsuitmanagement.chat.dto.RoomBasicRaw;
import com.avg.lawsuitmanagement.chat.dto.RoomBasicResult;
import com.avg.lawsuitmanagement.chat.dto.RoomBasicTemp;
import com.avg.lawsuitmanagement.chat.dto.RoomCreateForm;
import com.avg.lawsuitmanagement.chat.dto.RoomCreateParam;
import com.avg.lawsuitmanagement.chat.dto.RoomDetailResult;
import com.avg.lawsuitmanagement.chat.dto.RoomMemberMapParam;
import com.avg.lawsuitmanagement.chat.dto.RoomUserId;
import com.avg.lawsuitmanagement.chat.dto.UserBasicInfo;
import com.avg.lawsuitmanagement.chat.dto.UserFriendIdParam;
import com.avg.lawsuitmanagement.chat.dto.UserWithLawsuitInfo;
import com.avg.lawsuitmanagement.chat.dto.UserWithLawsuitResult;
import com.avg.lawsuitmanagement.chat.repository.ChatRepository;
import com.avg.lawsuitmanagement.common.custom.CustomRuntimeException;
import com.avg.lawsuitmanagement.common.exception.type.ErrorCode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;

    public UserBasicInfo searchUserByEmail(String email) {
        if (email == null) {
            throw new CustomRuntimeException(ErrorCode.CHAT_INVALID_REQUEST);
        }
        UserBasicInfo result = chatRepository.searchUserByEmail(email);
        return result;
    }

    public List<UserBasicInfo> searchEmployees() {
        List<UserBasicInfo> result = chatRepository.searchEmployees();
        return result;
    }

    public List<UserBasicInfo> searchClients() {
        List<UserBasicInfo> result = chatRepository.searchClients();
        return result;
    }

    public UserWithLawsuitResult searchUserDetailByEmail(String email) {
        if (email == null) {
            throw new CustomRuntimeException(ErrorCode.CHAT_INVALID_REQUEST);
        }
        UserBasicInfo user = searchUserByEmail(email);
        List<UserWithLawsuitInfo> raws =
            "의뢰인".equals(user.getRole()) ? chatRepository.searchClientUserDetailByEmail(email)
                : chatRepository.searchUserDetailByEmail(email);
        List<UserWithLawsuitInfo> filteredRaws = raws.stream()
            .peek(item -> {
                if ("CLOSING".equals(item.getLawsuitStatus())) {
                    item.setLawsuitId(null);
                    item.setLawsuitType(null);
                    item.setLawsuitNum(null);
                    item.setLawsuitName(null);
                    item.setLawsuitStatus(null);
                }
            })
            .toList();

        Map<Long, LawsuitBasicInfo> lawsuitMap = new HashMap<>();

        for (UserWithLawsuitInfo raw : filteredRaws) {
            Long lawsuitId = raw.getLawsuitId();
            if (lawsuitId == null) {
                continue;
            }
            lawsuitMap.putIfAbsent(lawsuitId, LawsuitBasicInfo.builder()
                .id(lawsuitId)
                .name(raw.getLawsuitName())
                .num(raw.getLawsuitNum())
                .type(raw.getLawsuitType())
                .build());
        }

        UserWithLawsuitInfo userData = filteredRaws.get(0);

        UserWithLawsuitResult result = UserWithLawsuitResult.builder()
            .id(userData.getId())
            .name(userData.getName())
            .role(userData.getRole())
            .hierarchy(userData.getHierarchy())
            .email(userData.getEmail())
            .lawsuits(lawsuitMap.values().stream().toList())
            .build();

        return result;
    }

    public Boolean checkFriendByEmail(String userEmail, String friendEmail) {
        UserBasicInfo user = searchUserByEmail(userEmail);
        UserBasicInfo friend = searchUserByEmail(friendEmail);

        boolean result = chatRepository.checkFriendById(UserFriendIdParam.builder()
            .userId(user.getId())
            .friendId(friend.getId())
            .build());

        return result;
    }

    public List<UserBasicInfo> searchFriendByEmail(String email) {

        if (!chatRepository.isEmployeeByEmail(email)) {
            List<String> employeeEmails = chatRepository.searchMemberEmailsByClientEmail(email);
            for (String employeeEmail : employeeEmails) {
                try {
                    addFriendByEmail(email, employeeEmail);
                } catch (CustomRuntimeException e) {
                    if (!e.getErrorCode().equals(ErrorCode.CHAT_ALREADY_FRIEND)) {
                        throw e;
                    }
                }
            }
        }

        UserBasicInfo user = searchUserByEmail(email);
        List<UserBasicInfo> result = chatRepository.searchFriendsById(user.getId());
        return result;
    }

    public void addFriendByEmail(String userEmail, String friendEmail) {
        if (userEmail.equals(friendEmail)) {
            throw new CustomRuntimeException(ErrorCode.CHAT_ADD_FRIEND_MY_SELF);
        }

        if (checkFriendByEmail(userEmail, friendEmail)) {
            throw new CustomRuntimeException(ErrorCode.CHAT_ALREADY_FRIEND);
        }

        UserBasicInfo user = searchUserByEmail(userEmail);
        UserBasicInfo friend = searchUserByEmail(friendEmail);

        UserFriendIdParam param = UserFriendIdParam.builder()
            .userId(user.getId())
            .friendId(friend.getId())
            .build();

        if (chatRepository.checkPreviousFriendById(param)) {
            chatRepository.restoreFriendById(param);
            return;
        }

        chatRepository.addFriend(param);
    }

    public void updateClientFriends(String email) {

    }

    public void removeFriendByEmail(String userEmail, String friendEmail) {
        UserBasicInfo user = searchUserByEmail(userEmail);
        UserBasicInfo friend = searchUserByEmail(friendEmail);

        chatRepository.removeFriendById(UserFriendIdParam.builder()
            .userId(user.getId())
            .friendId(friend.getId())
            .build());
    }

    public List<RoomBasicTemp> searchRoomsByUserId(Long userId) {
        List<RoomBasicRaw> raws = chatRepository.selectRoomsByUserId(userId);
        Map<Long, RoomBasicTemp> roomMap = new HashMap<>();
        for (RoomBasicRaw raw : raws) {
            Long key = raw.getRoomId();
            if (key == null) {
                continue;
            }
            if (roomMap.containsKey(key)) {
                List<UserBasicInfo> users = roomMap.get(key).getUsers();
                users.add(UserBasicInfo.builder()
                    .id(raw.getUserId())
                    .name(raw.getUserName())
                    .email(raw.getUserEmail())
                    .role(raw.getUserRole())
                    .hierarchy(raw.getUserHierarchy())
                    .build());
            } else {
                List<UserBasicInfo> users = new ArrayList<>();
                users.add(UserBasicInfo.builder()
                    .id(raw.getUserId())
                    .name(raw.getUserName())
                    .email(raw.getUserEmail())
                    .role(raw.getUserRole())
                    .hierarchy(raw.getUserHierarchy())
                    .build());
                RoomBasicTemp value = RoomBasicTemp.builder()
                    .id(raw.getRoomId())
                    .type(raw.getRoomType())
                    .name(raw.getRoomName())
                    .isShow(raw.getRoomIsShow())
                    .users(users)
                    .build();
                roomMap.put(key, value);
            }
        }
        List<RoomBasicTemp> result = roomMap.values().stream().toList();
        return result;
    }

    public List<RoomDetailResult> getAllRooms(String email) {
        UserBasicInfo user = searchUserByEmail(email);
        List<RoomBasicTemp> temp = searchRoomsByUserId(user.getId());
        List<RoomBasicResult> roomResult = temp.stream()
            .filter(RoomBasicTemp::getIsShow)
            .map(RoomBasicTemp::toResult)
            .toList();
        List<RoomDetailResult> result = new ArrayList<>();
        for (RoomBasicResult room : roomResult) {
            MessageRaw recentMessage = chatRepository.searchLatestMessageByRoomId(room.getId());
            Long unreadMessageCount = chatRepository.countUnreadMessageByRoomIdAndUserId(
                RoomUserId.builder()
                    .roomId(room.getId())
                    .userId(user.getId())
                    .build());
            result.add(RoomDetailResult.builder()
                .id(room.getId())
                .type(room.getType())
                .name(room.getName())
                .users(room.getUsers())
                .recentMessage(recentMessage)
                .unreadMessageCount(unreadMessageCount)
                .build());
        }

        return result;
    }

    public RoomBasicResult getOneToOneRoomByEmail(String userEmail, String friendEmail) {
        UserBasicInfo user = searchUserByEmail(userEmail);
        UserBasicInfo friend = searchUserByEmail(friendEmail);

        List<RoomBasicTemp> temps = searchRoomsByUserId(user.getId());

        RoomBasicResult result = temps.stream()
            .filter(item -> "oneToOne".equals(item.getType())
                && item.getUsers().stream()
                .anyMatch(item2 -> item2.getId().equals(friend.getId())))
            .map(RoomBasicTemp::toResult)
            .findFirst()
            .orElse(null);

        return result;
    }

    public RoomBasicResult getRoomById(Long id) {
        List<RoomBasicRaw> raws = chatRepository.selectRoomById(id);
        if (raws == null) {
            return null;
        }

        List<UserBasicInfo> users = raws.stream().map(item -> UserBasicInfo.builder()
                .id(item.getUserId())
                .name(item.getUserName())
                .email(item.getUserEmail())
                .role(item.getUserRole())
                .hierarchy(item.getUserHierarchy())
                .build())
            .toList();

        RoomBasicRaw room = raws.get(0);
        RoomBasicResult result = RoomBasicTemp.builder()
            .id(room.getRoomId())
            .type(room.getRoomType())
            .name(room.getRoomName())
            .isShow(room.getRoomIsShow())
            .users(users)
            .build()
            .toResult();

        return result;
    }

    @Transactional
    public RoomBasicResult createRoom(RoomCreateForm form) {
        RoomCreateParam param1 = RoomCreateParam.builder()
            .type(form.getType())
            .name(form.getName())
            .isShow(form.getIsShow())
            .build();
        chatRepository.createRoom(param1);

        Long roomId = param1.getId();
        List<Long> userIds = form.getEmails().stream()
            .map(this::searchUserDetailByEmail)
            .map(UserWithLawsuitResult::getId)
            .toList();
        if (userIds.isEmpty()) {
            throw new CustomRuntimeException(ErrorCode.CHAT_INVALID_REQUEST);
        }

        for (Long userId : userIds) {
            chatRepository.createRoomMemberMap(RoomMemberMapParam.builder()
                .roomId(roomId)
                .userId(userId)
                .build());
        }

        RoomBasicResult result = getRoomById(roomId);

        return result;
    }


    public void showRoomById(Long roomId) {
        if (!chatRepository.isShowRoomById(roomId)) {
            chatRepository.enableIsShowRoomById(roomId);
        }
    }

    @Transactional
    public List<MessageRawWithRead> saveMessage(MessageCreateParam param) {
        List<Long> roomUserIds = chatRepository.searchRoomUserIdsById(param.getRoomId());
        chatRepository.saveMessage(param);
        Long messageId = param.getId();
        for (Long userId : roomUserIds) {
            chatRepository.saveMessageIsRead(MessageUserId.builder()
                .messageId(messageId)
                .userId(userId)
                .build());
        }
        List<MessageRawWithRead> result = chatRepository.searchMessageById(messageId);
        return result;
    }

    public List<MessageResponse> getAllMessages(Long roomId) {
        List<MessageResponse> result = chatRepository.getAllMessagesByRoomId(roomId)
            .stream().map(item -> MessageResponse.builder()
                .id(item.getId())
                .roomId(item.getRoomId())
                .senderId(item.getSenderId())
                .content(item.getContent())
                .createdAt(item.getCreatedAt())
                .build())
            .toList();
        return result;
    }

    public void readAllMessage(String email, Long roomId) {
        UserBasicInfo user = searchUserByEmail(email);
        Long userId = user.getId();
        chatRepository.readAllMessageByRoomIdAndUserId(RoomUserId.builder()
            .userId(userId)
            .roomId(roomId)
            .build());

    }

    public Long countUnreadTotalCount(String email) {
        UserBasicInfo user = searchUserByEmail(email);
        Long result = chatRepository.countUnreadTotalCount(user.getId());
        return result;
    }


}
