package com.wuyang.democustomannotation.service.impl;

import com.wuyang.democustomannotation.domain.vo.UserVO;
import com.wuyang.democustomannotation.mapper.UserMapper;
import com.wuyang.democustomannotation.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public List<UserVO> getAllUsers() {
        return userMapper.getAllUsers();
    }
}
