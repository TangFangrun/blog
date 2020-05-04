package com.tfr.blog.blog.service.impl;

import com.tfr.blog.blog.NotFoundException;
import com.tfr.blog.blog.dao.BlogDao;
import com.tfr.blog.blog.pojo.Blog;
import com.tfr.blog.blog.pojo.Type;
import com.tfr.blog.blog.service.BlogService;
import com.tfr.blog.blog.util.MarkdownUtils;
import com.tfr.blog.blog.util.MyBeanUtils;
import com.tfr.blog.blog.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.*;

/**
 * @description:
 * @author: rain
 * @date: Created in 2020/5/2 1:08
 * @version: 1.0
 * @modified By:
 */
@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogDao blogDao;

    @Override
    public Blog getBlog(Long id) {
        return blogDao.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public Blog getAndConvert(Long id) {
        Blog blog = blogDao.findById(id).orElse(null);
        if (blog == null) {
            throw new NotFoundException("该博客不存在");
        }
        Blog b = new Blog();
        BeanUtils.copyProperties(blog, b);
        String content = blog.getContent();
        String s = MarkdownUtils.markdownToHtmlExtensions(content);
        b.setContent(s);
        int views = blogDao.updateViews(id);
        return b;
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {
        return blogDao.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if (!"".equals(blog.getTitle()) && blog.getTitle() != null) {
                    predicates.add(cb.like(root.<String>get("title"), "%"+blog.getTitle()+"%"));
                }
                if (blog.getTypeId() != null) {
                    predicates.add(cb.equal(root.<Type>get("type").get("id"), blog.getTypeId()));
                }
                if (blog.isRecommend()) {
                    predicates.add(cb.equal(root.<Boolean>get("recommend"), blog.isRecommend()));
                }
                cq.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        }, pageable);
    }

    @Override
    public List<Blog> listRecommendBlogTop(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "updateTime");
        Pageable pageable = PageRequest.of(0, size, sort);
        return blogDao.findTop(pageable);
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable) {
        return blogDao.findAll(pageable);
    }

    @Override
    public Page<Blog> listBlog(Long tagId, Pageable pageable) {
        return blogDao.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {

                Join join=root.join("tags");
                return cb.equal(join.get("id"),tagId);
            }
        },pageable);
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable, String query) {


        return blogDao.findByQuery(pageable, query);
    }

    @Override
    public Blog saveBlog(Blog blog) {
        if (blog.getId() == null) {
            blog.setCreateTime(new Date());
            blog.setUpdateTime(new Date());
            blog.setViews(0);
        } else {
            blog.setUpdateTime(new Date());
        }
        return blogDao.save(blog);
    }

    @Override
    public Blog updateBlog(Long id, Blog blog) {

        Blog byId = blogDao.findById(id).orElse(null);
        if (byId == null) {
            throw new NotFoundException("没有此条博客");
        }
        BeanUtils.copyProperties(blog, byId, MyBeanUtils.getNullPropertyNames(blog));
        BeanUtils.copyProperties(blog, byId);
        return blogDao.save(byId);
    }

    @Override
    public Map<String, List<Blog>> archiveBlog() {

        List<String> years=blogDao.findGroupYear();
        Map<String, List<Blog>> map=new LinkedHashMap<>();
        for (String year : years) {
            map.put(year,blogDao.findByYear(year));
        }
        return map;
    }

    @Override
    public Long countBlog() {
        return blogDao.count();
    }

    @Override
    public void deleteBlog(Long id) {
        blogDao.deleteById(id);
    }
}
