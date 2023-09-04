package com.avg.lawsuitmanagement.chat.repository;

import com.avg.lawsuitmanagement.chat.dto.UserFriendParam;
import com.avg.lawsuitmanagement.chat.dto.UserSearchDetailRaw;
import com.avg.lawsuitmanagement.chat.dto.UserSearchRaw;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ChatRepository {

    UserSearchRaw searchUserByEmail(String email);

    List<UserSearchDetailRaw> searchUserDetailByEmail(String email);

    List<UserSearchRaw> searchFriendsById(Long id);

    boolean checkIsFriend(UserFriendParam param);

    void addFriend(UserFriendParam param);
}
