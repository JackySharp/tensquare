package com.tensquare.friend.dao;

import com.tensquare.friend.pojo.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface FriendDao extends JpaRepository<Friend,String>,JpaSpecificationExecutor<Friend> {

    //依据用户id和好友id查询好友关系记录
    @Query(nativeQuery = true, value = "select * from tb_friend where userid = ?1 and friendid = ?2")
    Friend findByUseridAndFriendid(String userid, String friendid);

    @Modifying
    @Query(nativeQuery = true, value = "update tb_friend set islike = ?3 where userid = ?1 and friendid = ?2")
    void setLike(String userid, String friendid, String islike);

    @Modifying
    @Query(nativeQuery = true, value = "delete from tb_friend where userid = ?1 and friendid = ?2")
    void delete(String userid, String friendid);
}
