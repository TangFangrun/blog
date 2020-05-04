package com.tfr.blog.blog.dao;

import com.tfr.blog.blog.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @description:
 * @author: rain
 * @date: Created in 2020/5/1 16:55
 * @version: 1.0
 * @modified By:
 */
public interface UserDao  extends JpaRepository<User,Long> {

    User findByUsernameAndPassword(String username,String password);
}
