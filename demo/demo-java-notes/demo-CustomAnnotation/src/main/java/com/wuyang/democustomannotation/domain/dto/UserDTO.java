package com.wuyang.democustomannotation.domain.dto;

import lombok.Data;

/**
 * 数据传输对象 (DTO)
 * 用于服务层之间或与外部系统交互
 */
@Data
class UserDTO {
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
     * 性别 (1: 男, 0: 女)
     */
    private Integer gender;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 状态描述 (例如: 正常, 未启用, 封禁)
     * 这是一个附加的描述性字段，不直接对应数据库
     */
    private String statusDesc;
}