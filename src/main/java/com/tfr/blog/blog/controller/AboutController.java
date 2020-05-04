package com.tfr.blog.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @description:
 * @author: rain
 * @date: Created in 2020/5/4 17:13
 * @version: 1.0
 * @modified By:
 */
@Controller
public class AboutController {
    @GetMapping("/about")
    public  String about(){
        return "about";
    }
}
