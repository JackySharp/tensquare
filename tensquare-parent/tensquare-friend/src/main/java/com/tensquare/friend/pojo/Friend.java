package com.tensquare.friend.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@IdClass(Friend.class)
@Slf4j
@Entity
@Table(name = "tb_friend")
@AllArgsConstructor
public class Friend implements Serializable {
    @Id
    private String friendid;
    @Id
    private String userid;
    private String islike;

    public Friend() {
    }

    public Friend(String userid, String friendid) {
        this.friendid = friendid;
        this.userid = userid;
    }
}
