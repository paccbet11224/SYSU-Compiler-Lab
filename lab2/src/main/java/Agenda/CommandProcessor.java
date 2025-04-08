package Agenda;

import Agenda.model.Meeting;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.io.IOException;
import java.nio.file.*;

public class CommandProcessor {
    private final UserManager userManager;
    private final MeetingManager meetingManager;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm");

    public CommandProcessor(UserManager um, MeetingManager mm) {
        this.userManager = um;
        this.meetingManager = mm;
    }

    public void processLine(String line) {
        try {
            if (line.isEmpty())
                return;

            String[] tokens = line.trim().split(" ");
            String command = tokens[0].toLowerCase();

            switch (command) {
                case "register":
                    if (tokens.length != 3) {
                        System.out.println("[Warning] Usage: register <username> <password>");
                        break;
                    }
                    System.out.println(userManager.register(tokens[1], tokens[2]) ? "Register success."
                            : "Username already exists.");
                    break;

                case "add":
                    if (tokens.length != 7) {
                        System.out.println(
                                "[Warning] Usage: add <username> <password> <participant> <start> <end> <title>");
                        break;
                    }
                    if (!userManager.authenticate(tokens[1], tokens[2])) {
                        System.out.println("Auth failed.");
                        break;
                    }
                    LocalDateTime s = LocalDateTime.parse(tokens[4], formatter);
                    LocalDateTime e = LocalDateTime.parse(tokens[5], formatter);
                    System.out.println(meetingManager.addMeeting(tokens[1], tokens[3], s, e, tokens[6]));
                    break;

                case "query":
                    if (tokens.length != 5) {
                        System.out.println("[Warning] Usage: query <username> <password> <start> <end>");
                        break;
                    }
                    if (!userManager.authenticate(tokens[1], tokens[2])) {
                        System.out.println("Auth failed.");
                        break;
                    }
                    LocalDateTime qs = LocalDateTime.parse(tokens[3], formatter);
                    LocalDateTime qe = LocalDateTime.parse(tokens[4], formatter);
                    List<Meeting> meetings = meetingManager.queryMeetings(tokens[1], qs, qe);
                    for (Meeting m : meetings) {
                        System.out.printf("%s %s %s %s\n", m.getStart(), m.getEnd(), m.getTitle(), m.getParticipant());
                    }
                    break;

                case "delete":
                    if (tokens.length != 4) {
                        System.out.println("[Warning] Usage: delete <username> <password> <title>");
                        break;
                    }
                    if (!userManager.authenticate(tokens[1], tokens[2])) {
                        System.out.println("Auth failed.");
                        break;
                    }
                    System.out.println(meetingManager.deleteMeeting(tokens[1], tokens[3]) ? "Delete success."
                            : "Meeting not found or unauthorized.");
                    break;

                case "clear":
                    if (tokens.length != 3) {
                        System.out.println("[Warning] Usage: clear <username> <password>");
                        break;
                    }
                    if (!userManager.authenticate(tokens[1], tokens[2])) {
                        System.out.println("Auth failed.");
                        break;
                    }
                    meetingManager.clearMeetings(tokens[1]);
                    System.out.println("All meetings cleared.");
                    break;

                case "batch":
                    if (tokens.length != 2) {
                        System.out.println("[Warning] Usage: batch <filename>");
                        break;
                    }
                    try {
                        List<String> lines = Files.readAllLines(Paths.get(tokens[1]));
                        for (String l : lines) {
                            processLine(l);
                        }
                    } catch (IOException e1) {
                        System.out.println("[Warning] Failed to read batch file: " + e1.getMessage());
                    }
                    break;

                case "quit":
                    System.out.println("Bye.");
                    System.exit(0);
                    break;

                default:
                    System.out.println("[Warning] Unknown command.");
            }
        } catch (Exception e) {
            System.out.println("[Warning] Invalid command or error: " + e.getMessage());
        }
    }
}
