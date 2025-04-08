package Agenda;

import java.util.Scanner;

public class AgendaService {
    public static void main(String[] args) {
        UserManager userManager = new UserManager();
        MeetingManager meetingManager = new MeetingManager(userManager);
        CommandProcessor processor = new CommandProcessor(userManager, meetingManager);

        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.print("$ ");
                String line = scanner.nextLine();
                processor.processLine(line);
            }
        }
    }
}