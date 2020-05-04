package com.tfr.blog.blog.dao;

import com.tfr.blog.blog.pojo.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @description:
 * @author: rain
 * @date: Created in 2020/5/4 10:16
 * @version:
 * @modified By:
 */
public interface CommentDao  extends JpaRepository<Comment,Long> {

    List<Comment> findByBlogIdAndParentCommentNull(Long BlogId, Sort sort);
}
