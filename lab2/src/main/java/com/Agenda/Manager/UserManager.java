package com.Agenda.Manager;

import java.util.*;

import com.Agenda.Model.User;

/**
 * 用户管理类，用于注册、认证和管理用户信息。
 * 本类提供了注册、身份认证、查询用户等功能。
 */
public class UserManager {

    /** 存储用户名和对应用户对象的映射 */
    private final Map<String, User> users = new HashMap<>();

    /**
     * 注册一个新用户。
     * 如果用户名已经存在，则无法注册新用户。
     *
     * @param username 用户的用户名
     * @param password 用户的密码
     * @return 如果注册成功，返回 true；如果用户名已存在，返回 false
     */
    public boolean register(String username, String password) {
        if (users.containsKey(username)) {
            return false; // 用户名已存在，注册失败
        }
        users.put(username, new User(username, password)); // 将新用户添加到用户列表
        return true;
    }

    /**
     * 验证用户的身份。
     * 如果用户名和密码匹配，则认证通过。
     *
     * @param username 用户的用户名
     * @param password 用户的密码
     * @return 如果用户名存在且密码匹配，返回 true；否则返回 false
     */
    public boolean authenticate(String username, String password) {
        return users.containsKey(username) && users.get(username).getPassword().equals(password);
    }

    /**
     * 获取指定用户名的用户对象。
     *
     * @param username 用户的用户名
     * @return 对应的用户对象，如果用户名不存在，则返回 null
     */
    public User getUser(String username) {
        return users.get(username);
    }

    /**
     * 检查指定用户名的用户是否存在。
     *
     * @param username 用户的用户名
     * @return 如果用户存在，返回 true；否则返回 false
     */
    public boolean userExists(String username) {
        return users.containsKey(username);
    }
}
