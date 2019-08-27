package com.tensquare.spit.service;

import com.tensquare.entity.PageResult;
import com.tensquare.spit.dao.SpitDao;
import com.tensquare.spit.pojo.Spit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SpitService {

    @Autowired
    private SpitDao spitDao;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private MongoTemplate mongoTemplate;

    //查询所有吐槽
    public List<Spit> findAll() {
        return spitDao.findAll();
    }

    //依据id查询吐槽
    public Spit findById(String spitId) {
        //依据id得到OPtional对象
        Optional<Spit> optional = spitDao.findById(spitId);
        //判断OPtional对象是否为空
        if (optional.isPresent()) {
            //不为空则返回吐槽对象
            return optional.get();
        }
        //为空则返回空
        return null;
    }

    //添加吐槽
    public void add(Spit spit) {
        spitDao.save(spit);
    }

    //依据id删除吐槽
    public void delete(String spitId) {
        spitDao.deleteById(spitId);
    }

    //依据id修改吐槽
    public void update(Spit spit) {
        spitDao.save(spit);
    }

    //给吐槽点赞
    public Boolean thumbup(String spitId) {
        //模拟一个用户id
        String userid = "1006";
        //从Redis缓存查询当前用户是否已给该吐槽点过赞
        Object isThumbuped = redisTemplate.opsForValue().get(userid + "_" + spitId + "isThumbuped");
        if (null != isThumbuped) {
            //如果当前用户已点赞该条吐槽，则提示不能重复点赞
            return false;
        } else {
            //如果当前用户没有给该条吐槽点过赞，则更新Redis缓存和MongoDB数据库的点赞记录
            redisTemplate.opsForValue().set(userid + "_" + spitId + "isThumbuped","yes");
            updateThumbup(spitId);
            return true;
        }
    }

    //修改MongoDB数据库吐槽记录（传统方法）
    private void updateThumbup_bak(String spitId) {
        //依据id查询吐槽记录
        Spit spit = spitDao.findById(spitId).get();
        spit.setThumbup(spit.getThumbup() + 1);
        spitDao.save(spit);
    }

    //修改MongoDB数据库吐槽记录（使用MongoDB模板对象）
    private void updateThumbup(String spitId) {
        //创建查询对象
        Query query = new Query();
        //添加查询条件
        query.addCriteria(Criteria.where("_id").is(spitId));
        //创建修改对象
        Update update = new Update();
        //定义修改内容
        update.inc("thumbup",1);
        //修改记录
        mongoTemplate.updateFirst(query,update,"spit");
    }

    //依据上一级id查询吐槽列表
    public PageResult<Spit> findByParentid(String parentid, int page, int size) {
        Page<Spit> spitList = spitDao.findByParentid(parentid, PageRequest.of(page - 1, size));
        return new PageResult<Spit>(spitList.getTotalElements(),spitList.getContent());
    }
}
