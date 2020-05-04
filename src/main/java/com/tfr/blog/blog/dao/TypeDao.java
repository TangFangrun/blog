package com.tfr.blog.blog.dao;

import com.tfr.blog.blog.pojo.Type;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @description:
 * @author: rain
 * @date: Created in 2020/5/1 16:55
 * @version: 1.0
 * @modified By:
 */
public interface TypeDao extends JpaRepository<Type,Long> {

    Type findByName(String name);


    @Query("select t from Type t")
    List<Type> findTop(Pageable pageable);
}
