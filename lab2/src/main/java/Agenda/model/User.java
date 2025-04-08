package Agenda.model;

import java.util.*;

public class User {
    private final String username;
    private final String password;
    private final List<String> meetingIds;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.meetingIds = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public List<String> getMeetingIds() {
        return meetingIds;
    }

    public void addMeetingId(String id) {
        meetingIds.add(id);
    }

    public void removeMeetingId(String id) {
        meetingIds.remove(id);
    }

    public void clearMeetings() {
        meetingIds.clear();
    }
}