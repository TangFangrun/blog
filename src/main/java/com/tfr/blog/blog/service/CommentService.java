package com.tfr.blog.blog.service;

import com.tfr.blog.blog.pojo.Comment;

import java.util.List;

/**
 * @description:
 * @author: rain
 * @date: Created in 2020/5/4 10:11
 * @version:
 * @modified By:
 */
public interface CommentService {


    List<Comment> listCommentByBlogId(Long blogId);
    Comment saveComment(Comment comment);
}
