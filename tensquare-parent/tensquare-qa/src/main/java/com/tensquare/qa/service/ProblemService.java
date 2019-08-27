package com.tensquare.qa.service;

import com.tensquare.entity.PageResult;
import com.tensquare.qa.dao.ProblemDao;
import com.tensquare.qa.pojo.Problem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ProblemService {

    @Autowired
    private ProblemDao problemDao;

    //查询最新的问题列表，按创建时间降序排序，并分页
    public PageResult<Problem> getNewProblems(String labelId, int page, int size) { ;
        Page<Problem> problems = problemDao.getProblemsByLabelIdOrderByCreatetimeDesc(labelId, PageRequest.of(page - 1, size));
        return new PageResult<Problem>(problems.getTotalElements(),problems.getContent());
    }

    //查询热门问题列表，按回复数降序排序
    public PageResult<Problem> getHotProblems(String labelId, int page, int size) {
        Page<Problem> problems = problemDao.getProblemsByLabelIdOrderByReplyDesc(labelId, PageRequest.of(page - 1, size));
        return new PageResult<Problem>(problems.getTotalElements(),problems.getContent());
    }

    //发布问题
    public void add(Problem problem) {
        problemDao.save(problem);
    }
}
