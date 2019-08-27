package com.tensquare.friend.service;

import com.tensquare.friend.client.UserFeignClient;
import com.tensquare.friend.dao.FriendDao;
import com.tensquare.friend.dao.NoFriendDao;
import com.tensquare.friend.pojo.Friend;
import com.tensquare.friend.pojo.NoFriend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FriendService {

    @Autowired
    private FriendDao friendDao;

    @Autowired
    private UserFeignClient userFeignClient;

    @Autowired
    private NoFriendDao noFriendDao;

    //依据用户id和朋友id查询好友关系记录
    public Friend findByUseridAndFriendid(String userid, String friendid) {
        return  friendDao.findByUseridAndFriendid(userid, friendid);
    }

    //添加好友
    public int addFriend(String userid, String friendid) {
        Friend friend = null;
        //查询自己是否已添加对方为好友
        friend = friendDao.findByUseridAndFriendid(userid, friendid);
        if (null != friend) {
            return 0;
        }
        friend = new Friend(userid, friendid);
        friend.setIslike("0");
        friendDao.save(friend);
        //自己的粉丝数加1
        userFeignClient.incFollows(userid, 1);
        //对方的粉丝数加1
        userFeignClient.incFans(friendid, 1);
        //查询对方是否也添加自己为好友
        Friend myFriend = friendDao.findByUseridAndFriendid(friendid, userid);
        if (null != myFriend) {
            //如果对方也添加自己为好友，则设置互加好友字段为“1”
            friendDao.setLike(userid,friendid,"1");
            //对方的互加好友字段也设置为“1”
            friendDao.setLike(friendid,userid,"1");
        }
        return 1;
    }

    //删除好友
    public int deleteFriend(String userid, String friendid) {
        if (null == findByUseridAndFriendid(userid,friendid)) {
            return 0;
        }
        //将对方的互加好友字段设置为“0”
        friendDao.setLike(friendid,userid,"0");
        NoFriend noFriend = noFriendDao.findByUseridAndFriendid(userid, friendid);
        if (null == noFriend) {
            //向非好友关系表添加记录
            noFriendDao.add(userid,friendid);
        }
        //删除好友关系记录
        friendDao.delete(userid,friendid);
        //自己的关注数减1
        userFeignClient.incFollows(userid,-1);
        //对方的粉丝数减1
        userFeignClient.incFans(friendid,-1);
        return 1;
    }

}
