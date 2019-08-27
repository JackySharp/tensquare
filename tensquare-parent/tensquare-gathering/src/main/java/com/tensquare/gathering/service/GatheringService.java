package com.tensquare.gathering.service;

import com.tensquare.gathering.dao.GatheringDao;
import com.tensquare.gathering.pojo.Gathering;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional  //加入事务支持
public class GatheringService {

    @Autowired
    private GatheringDao gatheringDao;

    //存放方式：以id为 key，以方法的返回结果（活动）为值放到缓存
    @Cacheable(value = "gathering", key = "#gatheringId")
    public Gathering findById(String gatheringId) {
        return gatheringDao.findById(gatheringId).get();
    }

    //更新活动的同时清理缓存
    @Cacheable(key = "#gathering.id")
    public void update(Gathering gathering) {
        gatheringDao.save(gathering);
    }

    //删除活动的同时清理缓存
    @Cacheable(key = "#gatheringId")
    public void delete(String gatheringId) {
        gatheringDao.deleteById(gatheringId);
    }
}
