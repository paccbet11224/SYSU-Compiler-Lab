package Agenda;

import Agenda.model.Meeting;
import Agenda.model.User;
import java.time.LocalDateTime;
import java.util.*;

public class MeetingManager {
    private final Map<String, Meeting> meetings = new HashMap<>();
    private final UserManager userManager;

    public MeetingManager(UserManager userManager) {
        this.userManager = userManager;
    }

    public String addMeeting(String sponsor, String participant, LocalDateTime start, LocalDateTime end, String title) {
        if (!userManager.userExists(participant)) {
            return "Error: Participant not registered.";
        }

        User user1 = userManager.getUser(sponsor);
        User user2 = userManager.getUser(participant);

        for (String id : user1.getMeetingIds()) {
            Meeting m = meetings.get(id);
            if (m.overlap(start, end)) {
                return "Error: Sponsor has time conflict.";
            }
        }

        for (String id : user2.getMeetingIds()) {
            Meeting m = meetings.get(id);
            if (m.overlap(start, end)) {
                return "Error: Participant has time conflict.";
            }
        }

        Meeting meeting = new Meeting(title, sponsor, participant, start, end);
        meetings.put(meeting.getId(), meeting);
        user1.addMeetingId(meeting.getId());
        user2.addMeetingId(meeting.getId());

        return "Meeting added with ID: " + meeting.getId();
    }

    public List<Meeting> queryMeetings(String username, LocalDateTime start, LocalDateTime end) {
        List<Meeting> results = new ArrayList<>();
        User user = userManager.getUser(username);
        for (String id : user.getMeetingIds()) {
            Meeting m = meetings.get(id);
            if (m.overlap(start, end)) {
                results.add(m);
            }
        }
        return results;
    }

    public boolean deleteMeeting(String username, String title) {
        User user = userManager.getUser(username);
        for (String id : new ArrayList<>(user.getMeetingIds())) {
            Meeting m = meetings.get(id);
            if (m.getTitle().equals(title) && m.getSponsor().equals(username)) {
                userManager.getUser(m.getParticipant()).removeMeetingId(id);
                user.removeMeetingId(id);
                meetings.remove(id);
                return true;
            }
        }
        return false;
    }

    public void clearMeetings(String username) {
        User user = userManager.getUser(username);
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
