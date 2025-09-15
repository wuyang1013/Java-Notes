package com.wuyang.democustomannotation.mapper;

import com.wuyang.democustomannotation.domain.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select( "select * from user")
    List<UserVO> getAllUsers();
}
