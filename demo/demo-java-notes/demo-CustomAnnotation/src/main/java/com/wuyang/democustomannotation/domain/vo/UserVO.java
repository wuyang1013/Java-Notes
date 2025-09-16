package com.wuyang.democustomannotation.domain.vo;

import lombok.Data;
import org.apache.ibatis.annotations.Result;

/**
 * 视图对象 (VO)
 * 用于向前端展示数据
 */
@Data
public class UserVO {
    /**
     * 用户ID
     */
    private Long id;
    /**
     * 用户名
     */
    private String username;

    /**
     * 真实姓名
     */
    private String name;

    /**
     * 性别描述 (例如: 男, 女)
     * 这是一个附加的描述性字段，便于前端展示
     */
    private String genderDesc;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 状态描述 (例如: 正常, 未启用, 封禁)
     * 这是一个附加的描述性字段，便于前端展示
     */
    private String statusDesc;
}

