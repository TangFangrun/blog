package com.tfr.blog.blog.service;

import com.tfr.blog.blog.pojo.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @description:
 * @author: rain
 * @date: Created in 2020/5/1 18:49
 * @version: 1.0
 * @modified By:
 */
public interface TypeService {

    Type saveType(Type type);

    Type getType(Long id);

    Page<Type> listType(Pageable pageable);

    Type updateType(Long id, Type type);

    List<Type> listType();

    List<Type> listTypeTop(Integer size);

    void deleteType(Long id);

    Type getTypeByName(String name);
}
