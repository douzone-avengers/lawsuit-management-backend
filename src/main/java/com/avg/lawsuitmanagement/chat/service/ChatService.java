package com.avg.lawsuitmanagement.chat.service;

import com.avg.lawsuitmanagement.chat.dto.LawsuitBasicInfo;
import com.avg.lawsuitmanagement.chat.dto.UserFriendParam;
import com.avg.lawsuitmanagement.chat.dto.UserSearchDetailRaw;
import com.avg.lawsuitmanagement.chat.dto.UserSearchDetailResult;
import com.avg.lawsuitmanagement.chat.dto.UserSearchRaw;
import com.avg.lawsuitmanagement.chat.repository.ChatRepository;
import com.avg.lawsuitmanagement.common.custom.CustomRuntimeException;
import com.avg.lawsuitmanagement.common.exception.type.ErrorCode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;

    public UserSearchRaw searchUserByEmail(String email) {
        if (email == null) {
            throw new CustomRuntimeException(ErrorCode.CHAT_INVALID_REQUEST);
        }

        UserSearchRaw result = chatRepository.searchUserByEmail(email);

        return result;
    }

    public UserSearchDetailResult searchUserDetailByEmail(String email) {
        if (email == null) {
            throw new CustomRuntimeException(ErrorCode.CHAT_INVALID_REQUEST);
        }

        List<UserSearchDetailRaw> raws = chatRepository.searchUserDetailByEmail(email);
        Map<Long, LawsuitBasicInfo> lawsuitMap = new HashMap<>();

        for (UserSearchDetailRaw raw : raws) {
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

        if (raws.isEmpty()) {
            throw new CustomRuntimeException(ErrorCode.CHAT_INVALID_REQUEST);

        }
        UserSearchDetailRaw user = raws.get(0);

        UserSearchDetailResult result = UserSearchDetailResult.builder()
            .id(user.getId())
            .name(user.getName())
            .hierarchy(user.getHierarchy())
            .email(user.getEmail())
            .lawsuits(lawsuitMap.values().stream().toList())
            .build();

        return result;
    }

    public List<UserSearchRaw> searchFriendByEmail(String email) {
        UserSearchRaw user = chatRepository.searchUserByEmail(email);
        List<UserSearchRaw> result = chatRepository.searchFriendsById(user.getId());
        return result;
    }

    public void addFriend(String userEmail, String friendEmail) {
        if (userEmail.equals(friendEmail)) {
            throw new CustomRuntimeException(ErrorCode.CHAT_ADD_FRIEND_MY_SELF);
        }

        UserSearchRaw user = chatRepository.searchUserByEmail(userEmail);
        UserSearchRaw friend = chatRepository.searchUserByEmail(friendEmail);
        if (chatRepository.checkIsFriend(UserFriendParam.builder()
            .userId(user.getId())
            .friendId(friend.getId())
            .build())) {
            throw new CustomRuntimeException(ErrorCode.CHAT_ALREADY_FRIEND);
        }

        chatRepository.addFriend(UserFriendParam.builder()
            .userId(user.getId())
            .friendId(friend.getId())
            .build());
    }

}
