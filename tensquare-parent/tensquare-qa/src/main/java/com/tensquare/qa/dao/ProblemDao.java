package com.tensquare.qa.dao;

import com.tensquare.qa.pojo.Problem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;


/**
 * 此接口继承了JpaRepository和JpaSpecificationExecutor接口，作用类似于MyBatis的通用Mapper
 * JpaRepository:
 *     JpaRepository支持接口规范方法名查询。意思是如果在接口中定义的查询方法符合它的命名规则，就可以不用写实现。
 * JpaSpecificationExecutor:
 *     spring-data-jpa 通过创建方法名来做查询，只能做简单的查询，那如果我们要做复杂一些的查询呢，多条件分页怎么办？
 *     这里，spring data jpa为我们提供了JpaSpecificationExecutor接口，只要简单实现toPredicate方法就可以实现复杂的查询。
 *     它不属于Repository体系，实现一组 JPA Criteria 查询相关的方法
 *     Specification：封装 JPA Criteria 查询条件，通常使用匿名内部类的方式来创建该接口的对象
 */
public interface ProblemDao extends JpaRepository<Problem,String>, JpaSpecificationExecutor<Problem> {

    //通过sql语句查询，平时开发使用较多
    @Query(nativeQuery = true, value = "select * from tb_pl pl, tb_problem p where pl.problemid = p.id and pl.labelid = ?1 order by p.createtime desc")
    Page<Problem> getProblemsByLabelIdOrderByCreatetimeDesc(String labelId, Pageable pageable);

    //通过sql语句查询，开发中使用较多
    @Query(nativeQuery = true, value = "select * from tb_pl pl, tb_problem p where pl.problemid = p.id and pl.labelid = ?1 order by reply desc")
    Page<Problem> getProblemsByLabelIdOrderByReplyDesc(String labelId, Pageable pageable);
}
