package com.wuyang.democustomannotation.controller;

import com.wuyang.democustomannotation.annotation.CustomAnnotation;
import com.wuyang.democustomannotation.domain.vo.UserVO;
import com.wuyang.democustomannotation.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class User {

    @Resource
    private UserService userService;

    @CustomAnnotation(field = "name", value = "二")
    @GetMapping("/getAllUsers")
    public List<UserVO> getAllUsers() {
        return userService.getAllUsers();
    }
    
    @CustomAnnotation(field = "name", value = "二")
    @GetMapping("/getFilteredUsers")
    public List<UserVO> getFilteredUsers() {
        return userService.getAllUsers();
    }
}