package com.Agenda.Model;

import java.util.*;

/**
 * 表示一个用户，包含用户的用户名、密码和已参与的会议 ID 列表。
 * 用户可以添加、删除和清空其参与的会议 ID。
 */
public class User {

    /** 用户的用户名 */
    private final String username;

    /** 用户的密码 */
    private final String password;

    /** 用户参与的会议 ID 列表 */
    private final List<String> meetingIds;

    /**
     * 创建一个新的用户对象。
     *
     * @param username 用户的用户名
     * @param password 用户的密码
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.meetingIds = new ArrayList<>();
    }

    /**
     * 获取用户的用户名。
     *
     * @return 用户的用户名
     */
    public String getUsername() {
        return username;
    }

    /**
     * 获取用户的密码。
     *
     * @return 用户的密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 获取用户已参与的会议 ID 列表。
     *
     * @return 用户参与的会议 ID 列表
     */
    public List<String> getMeetingIds() {
        return meetingIds;
    }

    /**
     * 向用户的会议 ID 列表中添加一个新的会议 ID。
     *
     * @param id 要添加的会议 ID
     */
    public void addMeetingId(String id) {
        meetingIds.add(id);
    }

    /**
     * 从用户的会议 ID 列表中移除指定的会议 ID。
     *
     * @param id 要移除的会议 ID
     */
    public void removeMeetingId(String id) {
        meetingIds.remove(id);
    }

    /**
     * 清空用户的会议 ID 列表。
     */
    public void clearMeetings() {
        meetingIds.clear();
    }
}
