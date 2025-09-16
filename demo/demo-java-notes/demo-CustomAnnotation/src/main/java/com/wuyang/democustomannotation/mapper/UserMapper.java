package com.wuyang.democustomannotation.mapper;

import com.wuyang.democustomannotation.domain.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select( "select id, username, name, gender, age, status from user")
    @Results({
            @Result( property = "statusDesc", column = "status" ),
            @Result(property = "genderDesc", column = "gender"),
    })
    List<UserVO> getAllUsers();
}