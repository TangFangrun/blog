package com.tfr.blog.blog.service.impl;

import com.tfr.blog.blog.NotFoundException;
import com.tfr.blog.blog.dao.TypeDao;
import com.tfr.blog.blog.pojo.Type;
import com.tfr.blog.blog.service.TypeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @description:
 * @author: rain
 * @date: Created in 2020/5/1 19:03
 * @version: 1.0
 * @modified By:
 */
@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeDao typeDao;

    @Transactional
    @Override
    public Type saveType(Type type) {

        return typeDao.save(type);
    }

    @Transactional
    @Override
    public Type getType(Long id) {

        return typeDao.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public Page<Type> listType(Pageable pageable) {
        return typeDao.findAll(pageable);
    }

    @Transactional
    @Override
    public Type updateType(Long id, Type type) {
        Type t = typeDao.findById(id).orElse(null);
        if (t == null) {
            throw new NotFoundException("没有此条博客");
        }
        BeanUtils.copyProperties(type, t);
        typeDao.save(t);
        return null;
    }

    @Override
    public List<Type> listTypeTop(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "blogs.size");
        Pageable pageable = PageRequest.of(0, size, sort);
        return typeDao.findTop(pageable);
    }

    @Override
    public List<Type> listType() {
        return typeDao.findAll();
    }

    @Transactional
    @Override
    public void deleteType(Long id) {
        typeDao.deleteById(id);
    }

    @Override
    public Type getTypeByName(String name) {
        return typeDao.findByName(name);
    }
}
