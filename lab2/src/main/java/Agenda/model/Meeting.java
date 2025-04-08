package Agenda.model;

import java.time.LocalDateTime;

public class Meeting {
    private final String id;
    private final String title;
    private final String sponsor;
    private final String participant;
    private final LocalDateTime start;
    private final LocalDateTime end;

    public Meeting(String title, String sponsor, String participant, LocalDateTime start, LocalDateTime end) {
        this.id = generateId(title);
        this.title = title;
        this.sponsor = sponsor;
        this.participant = participant;
        this.start = start;
        this.end = end;
    }

    private String generateId(String base) {
        return "MID-" + Math.abs((base + System.nanoTime()).hashCode());
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getSponsor() {
        return sponsor;
    }

    public String getParticipant() {
        return participant;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public boolean overlap(LocalDateTime otherStart, LocalDateTime otherEnd) {
        if (!start.isAfter(otherEnd) && !end.isBefore(otherStart)) {
            return true;
        }
        return false;
    }
}
