package com.Agenda;

import org.junit.jupiter.api.Test;

import com.Agenda.Manager.MeetingManager;
import com.Agenda.Manager.UserManager;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;

public class AgendaTest {
    @Test
    public void testUserRegistrationAndAuthentication() {
        UserManager um = new UserManager();
        assertTrue(um.register("alice", "123"));
        assertFalse(um.register("alice", "456"));
        assertTrue(um.authenticate("alice", "123"));
        assertFalse(um.authenticate("alice", "wrong"));
    }

    @Test
    public void testMeetingAddAndConflict() {
        UserManager um = new UserManager();
        um.register("a", "p1");
        um.register("b", "p2");
        MeetingManager mm = new MeetingManager(um);
        LocalDateTime s1 = LocalDateTime.parse("2025-04-08T14:00");
        LocalDateTime e1 = LocalDateTime.parse("2025-04-08T15:00");
        assertTrue(mm.addMeeting("a", "b", s1, e1, "meet1").contains("Meeting added"));
        assertTrue(mm.queryMeetings("a", s1, e1).size() == 1);
        assertFalse(mm.addMeeting("a", "b", s1, e1, "meet2").contains("Meeting added"));
        assertTrue(mm.deleteMeeting("a", "meet1"));
    }
}
