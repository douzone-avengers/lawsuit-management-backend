package com.avg.lawsuitmanagement.chat.service;

import com.avg.lawsuitmanagement.chat.dto.LawsuitBasicInfo;
import com.avg.lawsuitmanagement.chat.dto.UserIdFriendIdParam;
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
        List<UserSearchDetailRaw> filteredRaws = raws.stream()
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

        for (UserSearchDetailRaw raw : filteredRaws) {
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

        UserSearchDetailRaw user = filteredRaws.get(0);

        UserSearchDetailResult result = UserSearchDetailResult.builder()
            .id(user.getId())
            .name(user.getName())
            .hierarchy(user.getHierarchy())
            .email(user.getEmail())
            .lawsuits(lawsuitMap.values().stream().toList())
            .build();

        return result;
    }

    public Boolean checkFriend(String userEmail, String friendEmail) {
        UserSearchRaw user = searchUserByEmail(userEmail);
        UserSearchRaw friend = searchUserByEmail(friendEmail);

        boolean result = chatRepository.checkFriend(UserIdFriendIdParam.builder()
            .userId(user.getId())
            .friendId(friend.getId())
            .build());

        return result;
    }

    public List<UserSearchRaw> searchFriendByEmail(String email) {
        UserSearchRaw user = searchUserByEmail(email);
        List<UserSearchRaw> result = chatRepository.searchFriendsById(user.getId());
        return result;
    }

    public void addFriend(String userEmail, String friendEmail) {
        if (userEmail.equals(friendEmail)) {
            throw new CustomRuntimeException(ErrorCode.CHAT_ADD_FRIEND_MY_SELF);
        }

        if (checkFriend(userEmail, friendEmail)) {
            throw new CustomRuntimeException(ErrorCode.CHAT_ALREADY_FRIEND);
        }

        UserSearchRaw user = searchUserByEmail(userEmail);
        UserSearchRaw friend = searchUserByEmail(friendEmail);

        UserIdFriendIdParam param = UserIdFriendIdParam.builder()
            .userId(user.getId())
            .friendId(friend.getId())
            .build();

        if (chatRepository.checkPreviousFriend(param)) {
            chatRepository.restoreFriend(param);
            return;
        }

        chatRepository.addFriend(param);
    }

    public void removeFriend(String userEmail, String friendEmail) {
        UserSearchRaw user = searchUserByEmail(userEmail);
        UserSearchRaw friend = searchUserByEmail(friendEmail);

        chatRepository.removeFriend(UserIdFriendIdParam.builder()
            .userId(user.getId())
            .friendId(friend.getId())
            .build());
    }

    public Long searchOneToOneRoomId(String userEmail, String friendEmail) {
        UserSearchRaw user = searchUserByEmail(userEmail);
        UserSearchRaw friend = searchUserByEmail(friendEmail);

        UserIdFriendIdParam param = UserIdFriendIdParam.builder()
            .userId(user.getId())
            .friendId(friend.getId())
            .build();

        Long id = chatRepository.searchOneToOneRoomId(param);
        if (id == null) {
            param.setUserId(friend.getId());
            param.setFriendId(user.getId());
            id = chatRepository.searchOneToOneRoomId(param);
        }

        return id;
    }

}
