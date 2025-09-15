package com.wuyang.democustomannotation.domain.po;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 持久化对象 (PO)
 * 与数据库表 t_user 映射
 */
@Data
class UserPO {
    /**
     * 用户唯一ID
     */
    private Long id;

    /**
     * 用户名，唯一
     */
    private String username;

    /**
     * 加密后的密码
     */
    private String password;

    /**
     * 性别：1-男, 0-女
     */
    private Integer gender;

    /**
     * 真实姓名
     */
    private String name;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 记录创建时间
     */
    private LocalDateTime createTime;

    /**
     * 记录更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 用户状态：1-正常, 0-未启用, -1-封禁
     */
    private Integer status;
}