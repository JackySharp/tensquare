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
@IdClass(NoFriend.class)
@Slf4j
@Entity
@Table(name = "no_friend")
@AllArgsConstructor
@NoArgsConstructor
public class NoFriend implements Serializable {
    @Id
    private String userid;
    @Id
    private String friendid;
}
