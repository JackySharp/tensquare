package com.tensquare.article.dao;

import com.tensquare.article.pojo.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
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
public interface ArticleDao extends JpaRepository<Article,String>, JpaSpecificationExecutor<Article> {

    //使用sql语句修改数据库，开发中使用较多
    @Modifying
    @Query(nativeQuery = true, value = "update from tb_article set state = '1' where id = ?1")
    void setExamineState(String articleId);

    //使用sql语句修改数据库，开发中使用较多
    @Modifying
    @Query(nativeQuery = true, value = "update from tb_article set thumbup = thumbup + 1 where id = ?1")
    void addThumbup(String articleId);

}
