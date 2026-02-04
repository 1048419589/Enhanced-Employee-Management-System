package com.empManagement.empManagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

//登录网页和主页的跳转

@Controller
public class LoginController {

    @RequestMapping("/login")
    public String loginPage(){
        return "pages/auth_login";
    }
    @RequestMapping("/home")
    public String loginSubmit(){
        return "/pages/landing_page";
    }
}
