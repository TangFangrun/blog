package com.tfr.blog.blog.service.impl;

import com.tfr.blog.blog.dao.UserDao;
import com.tfr.blog.blog.pojo.User;
import com.tfr.blog.blog.service.UserService;
import com.tfr.blog.blog.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: rain
 * @date: Created in 2020/5/1 16:54
 * @version: 1.0
 * @modified By:
 */
@Service
public class UserServiceImpl  implements UserService {

    @Autowired
    private UserDao userDao;


    @Override
    public User checkUser(String username, String password) {
        User user = userDao.findByUsernameAndPassword(username, MD5Utils.code(password));
        return user;
    }
}
