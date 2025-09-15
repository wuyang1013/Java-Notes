package com.wuyang.democustomannotation.service;

import com.wuyang.democustomannotation.domain.vo.UserVO;
import org.springframework.stereotype.Service;

import java.util.List;

//@Service
public interface UserService {

    // 获取所有用户信息
    List<UserVO> getAllUsers();
}
