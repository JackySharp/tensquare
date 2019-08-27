package com.tensquare.friend.service;

import com.tensquare.friend.dao.NoFriendDao;
import com.tensquare.friend.pojo.NoFriend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoFriendService {

    @Autowired
    private NoFriendDao noFriendDao;

    //添加非好友记录
    public void add(NoFriend noFriend) {
        noFriendDao.save(noFriend);
    }

    //依据用户id和好友id查询非好友记录
    public NoFriend findByUseridAndFriendid(String userid, String friendid) {
        return noFriendDao.findByUseridAndFriendid(userid,friendid);
    }
}
