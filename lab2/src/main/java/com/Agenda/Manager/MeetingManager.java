package com.Agenda.Manager;

import java.time.LocalDateTime;
import java.util.*;

import com.Agenda.Model.Meeting;
import com.Agenda.Model.User;

/**
 * 会议管理类，用于管理用户的会议，包括添加、查询、删除会议等功能。
 * 本类提供了对会议的添加、查询、删除等操作，并处理会议冲突的检测。
 */
public class MeetingManager {

    /** 存储会议ID和会议对象的映射 */
    private final Map<String, Meeting> meetings = new HashMap<>();

    /** 用户管理器，用于获取和管理用户信息 */
    private final UserManager userManager;

    /**
     * 构造函数，初始化会议管理器。
     * 
     * @param userManager 用户管理器，用于操作用户数据
     */
    public MeetingManager(UserManager userManager) {
        this.userManager = userManager;
    }

    /**
     * 添加一场会议，检查会议时间冲突和参与者是否注册。
     * 如果有冲突或参与者未注册，将返回相应的错误信息。
     * 
     * @param sponsor     会议发起人的用户名
     * @param participant 会议参与人的用户名
     * @param start       会议的开始时间
     * @param end         会议的结束时间
     * @param title       会议的标题
     * @return 会议添加成功或失败的消息
     */
    public String addMeeting(String sponsor, String participant, LocalDateTime start, LocalDateTime end, String title) {
        // 检查参与者是否注册
        if (!userManager.userExists(participant)) {
            return "Error: Participant not registered.";
        }

        User user1 = userManager.getUser(sponsor);
        User user2 = userManager.getUser(participant);

        // 检查发起人的会议是否有时间冲突
        for (String id : user1.getMeetingIds()) {
            Meeting m = meetings.get(id);
            if (m.overlap(start, end)) {
                return "Error: Sponsor has time conflict.";
            }
        }

        // 检查参与人的会议是否有时间冲突
        for (String id : user2.getMeetingIds()) {
            Meeting m = meetings.get(id);
            if (m.overlap(start, end)) {
                return "Error: Participant has time conflict.";
            }
        }

        // 创建新会议并将其加入到会议列表中
        Meeting meeting = new Meeting(title, sponsor, participant, start, end);
        meetings.put(meeting.getId(), meeting);
        user1.addMeetingId(meeting.getId());
        user2.addMeetingId(meeting.getId());

        return "Meeting added with ID: " + meeting.getId();
    }

    /**
     * 查询指定用户在指定时间段内的会议。
     * 
     * @param username 用户的用户名
     * @param start    查询的起始时间
     * @param end      查询的结束时间
     * @return 用户在该时间段内的所有会议
     */
    public List<Meeting> queryMeetings(String username, LocalDateTime start, LocalDateTime end) {
        List<Meeting> results = new ArrayList<>();
        User user = userManager.getUser(username);
        // 查找用户在指定时间段内的会议
        for (String id : user.getMeetingIds()) {
            Meeting m = meetings.get(id);
            if (m.overlap(start, end)) {
                results.add(m);
            }
        }
        return results;
    }

    /**
     * 删除指定用户的指定会议。
     * 只有发起人能够删除会议。
     * 
     * @param username 用户的用户名
     * @param title    会议的标题
     * @return 如果删除成功，返回 true；否则返回 false
     */
    public boolean deleteMeeting(String username, String title) {
        User user = userManager.getUser(username);
        // 查找并删除指定用户的会议
        for (String id : new ArrayList<>(user.getMeetingIds())) {
            Meeting m = meetings.get(id);
            if (m.getTitle().equals(title) && m.getSponsor().equals(username)) {
                // 删除与该会议相关的所有记录
                userManager.getUser(m.getParticipant()).removeMeetingId(id);
                user.removeMeetingId(id);
                meetings.remove(id);
                return true;
            }
        }
        return false;
    }

    /**
     * 清空指定用户的所有会议。
     * 只有发起人的会议才会被清除。
     * 
     * @param username 用户的用户名
     */
    public void clearMeetings(String username) {
        User user = userManager.getUser(username);
        // 清空用户作为发起人的所有会议
        for (String id : new ArrayList<>(user.getMeetingIds())) {
            Meeting m = meetings.get(id);
            if (m.getSponsor().equals(username)) {
                userManager.getUser(m.getParticipant()).removeMeetingId(id);
                meetings.remove(id);
                user.removeMeetingId(id);
            }
        }
    }
}
