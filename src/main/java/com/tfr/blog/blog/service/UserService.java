package com.tfr.blog.blog.service;

import com.tfr.blog.blog.pojo.User;

/**
 * @description:
 * @author: rain
 * @date: Created in 2020/5/1 16:52
 * @version: 1.0
 * @modified By:
 */
public interface UserService {

    User checkUser(String username, String password);
}
