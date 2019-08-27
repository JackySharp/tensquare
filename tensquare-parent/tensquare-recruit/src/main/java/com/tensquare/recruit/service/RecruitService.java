package com.tensquare.recruit.service;

import com.tensquare.recruit.dao.RecruitDao;
import com.tensquare.recruit.pojo.Recruit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecruitService {

    @Autowired
    private RecruitDao recruitDao;

    //查询推荐职位并以创建日期降序排序
    public List<Recruit> findRecruitsByRecommend() {
        return recruitDao.findRecruitsByStateIsOrderByCreatetimeDesc("2");
    }

    //查询最新职位并以创建日期降序排序
    public List<Recruit> findLastestRecruits() {
        return recruitDao.findTop12ByStateIsNotOrderByCreatetimeDesc("0");
    }
}
