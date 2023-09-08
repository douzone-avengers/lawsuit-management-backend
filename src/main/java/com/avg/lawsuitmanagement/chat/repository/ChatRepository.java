package com.avg.lawsuitmanagement.chat.repository;

import com.avg.lawsuitmanagement.chat.dto.MessageCreateParam;
import com.avg.lawsuitmanagement.chat.dto.MessageRaw;
import com.avg.lawsuitmanagement.chat.dto.MessageRawWithRead;
import com.avg.lawsuitmanagement.chat.dto.MessageUserId;
import com.avg.lawsuitmanagement.chat.dto.RoomBasicRaw;
import com.avg.lawsuitmanagement.chat.dto.RoomCreateParam;
import com.avg.lawsuitmanagement.chat.dto.RoomMemberMapParam;
import com.avg.lawsuitmanagement.chat.dto.RoomUserId;
import com.avg.lawsuitmanagement.chat.dto.UserBasicInfo;
import com.avg.lawsuitmanagement.chat.dto.UserFriendIdParam;
import com.avg.lawsuitmanagement.chat.dto.UserWithLawsuitInfo;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ChatRepository {

    UserBasicInfo searchUserByEmail(String email);

    List<UserWithLawsuitInfo> searchUserDetailByEmail(String email);

    List<UserBasicInfo> searchFriendsById(Long id);

    boolean checkFriendById(UserFriendIdParam param);

    boolean checkPreviousFriendById(UserFriendIdParam param);

    void addFriend(UserFriendIdParam param);

    void restoreFriendById(UserFriendIdParam param);

    void removeFriendById(UserFriendIdParam param);

    List<RoomBasicRaw> selectRoomsByUserId(Long id);

    List<RoomBasicRaw> selectRoomById(Long id);

    void createRoom(RoomCreateParam param);

    void createRoomMemberMap(RoomMemberMapParam param);

    Boolean isShowRoomById(Long id);

    void enableIsShowRoomById(Long id);

    List<Long> searchRoomUserIdsById(Long id);

    void saveMessage(MessageCreateParam param);

    void saveMessageIsRead(MessageUserId param);

    List<MessageRawWithRead> searchMessageById(Long id);

    Long countUnreadMessageByRoomIdAndUserId(RoomUserId param);

    MessageRaw searchLatestMessageByRoomId(Long roomId);

    List<MessageRaw> getAllMessagesByRoomId(Long roomId);

    void readAllMessageByRoomIdAndUserId(RoomUserId param);

    Long countUnreadTotalCount(Long userId);
}
