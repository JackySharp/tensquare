package com.tensquare.base.service;

import com.tensquare.base.dao.LabelDao;
import com.tensquare.base.pojo.Label;
import com.tensquare.entity.PageResult;
import com.tensquare.util.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
public class LabelService {

    @Autowired
    private LabelDao labelDao;

    @Autowired
    private IdWorker idWorker;

    //新增一个标签
    public void addOne(Label label) {
        //数据库中的表tb_label的主键不是自增的，所以需要随机生成一个id
        label.setId(String.valueOf(idWorker.nextId()));
        labelDao.save(label);
    }

    //查询所有标签
    public List<Label> findAll() {
        return labelDao.findAll();
    }

    //依据id查询标签
    public Label findById(String labelId) {
        return labelDao.getOne(labelId);
    }

    //依据id修改标签
    public void updateById(Label label) {
        labelDao.save(label);
    }

    //依据id删除标签
    public void deleteById(String labelId) {
        labelDao.deleteById(labelId);
    }

    //多条件查询标签
    public List<Label> search(Label label) {

        //构造查询条件
        Specification<Label> specification = createSpecification(label);

        //查询符合查询条件的标签
        List<Label> labelList = labelDao.findAll(specification);
        return labelList;
    }

    //创建查询条件
    private Specification<Label> createSpecification(Label label) {
        return new Specification<Label>() {
                @Override
                public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    //将所有查询条件封装到集合中
                    List<Predicate> predicateList = new ArrayList<Predicate>();
                    //将标签名称添加到查询条件
                    if (!StringUtils.isEmpty(label.getLabelname())) {
                        predicateList.add(criteriaBuilder.like(root.get("labelname").as(String.class),"%" + label.getLabelname() + "%"));
                    }
                    //将标签状态添加到查询条件
                    if (!StringUtils.isEmpty(label.getState())) {
                        predicateList.add(criteriaBuilder.equal(root.get("state").as(String.class),label.getState()));
                    }
                    //架构推荐状态添加到查询条件
                    if (!StringUtils.isEmpty(label.getRecommend())) {
                        predicateList.add(criteriaBuilder.equal(root.get("recommend").as(String.class),label.getRecommend()));
                    }
                    //将标签使用量添加到查询条件
                    if (null != label.getCount() && label.getCount() > 0) {
                        predicateList.add(criteriaBuilder.greaterThanOrEqualTo(root.get("count").as(Long.class),label.getCount()));
                    }
                    //将粉丝数添加到查询条件
                    if (null != label.getFans() && label.getFans() > 0) {
                        predicateList.add(criteriaBuilder.greaterThanOrEqualTo(root.get("fans").as(Long.class),label.getFans()));
                    }
                    //构造条件数组
                    Predicate[] predicates = new Predicate[predicateList.size()];
                    //返回查询条件
                    return criteriaBuilder.and(predicateList.toArray(predicates));
                }
            };
    }

    //条件查询带分页
    public PageResult searchByPage(Label label, int page, int size) {
        //配置分页参数
        Pageable pageable = PageRequest.of(page - 1,size);
        //构造查询条件
        Specification<Label> specification = createSpecification(label);
        //执行条件查询并分页
        Page<Label> pageList = labelDao.findAll(specification, pageable);
        //将分页结果转换为自定义分页通用对象并返回
        return new PageResult(pageList.getTotalElements(),pageList.getContent());
    }
}
