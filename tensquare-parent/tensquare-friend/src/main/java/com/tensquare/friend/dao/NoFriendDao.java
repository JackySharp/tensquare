package com.tensquare.friend.dao;

import com.tensquare.friend.pojo.NoFriend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface NoFriendDao extends JpaRepository<NoFriend,String>,JpaSpecificationExecutor<NoFriend> {

    @Query(nativeQuery = true, value = "select * from tb_nofriend where userid = ?1 and friendid = ?2")
    NoFriend findByUseridAndFriendid(String userid, String friendid);

    @Modifying
    @Query(nativeQuery = true, value = "insert into tb_nofriend values(?1,?2)")
    void add(String userid, String friendid);
}
