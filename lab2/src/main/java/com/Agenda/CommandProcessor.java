package com.Agenda;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.Agenda.Manager.MeetingManager;
import com.Agenda.Manager.UserManager;
import com.Agenda.Model.Meeting;

import java.io.IOException;
import java.nio.file.*;

/**
 * 命令处理器类，用于解析和执行用户输入的命令。
 * 该类支持注册、添加会议、查询会议、删除会议、清空会议等操作，并能批量处理命令文件。
 */
public class CommandProcessor {

    /** 用户管理器，用于处理用户相关操作 */
    private final UserManager userManager;

    /** 会议管理器，用于处理会议相关操作 */
    private final MeetingManager meetingManager;

    /** 日期时间格式化器，用于解析用户输入的日期时间字符串 */
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm");

    /**
     * 构造函数，初始化命令处理器。
     * 
     * @param um 用户管理器
     * @param mm 会议管理器
     */
    public CommandProcessor(UserManager um, MeetingManager mm) {
        this.userManager = um;
        this.meetingManager = mm;
    }

    /**
     * 处理用户输入的一行命令，根据不同的命令类型执行相应的操作。
     * 
     * @param line 用户输入的命令行
     */
    public void processLine(String line) {
        try {
            // 如果输入为空，直接返回
            if (line.isEmpty())
                return;

            // 按空格分割命令行，并提取第一个命令
            String[] tokens = line.trim().split(" ");
            String command = tokens[0].toLowerCase();

            // 根据不同的命令执行相应操作
            switch (command) {
                case "register":
                    // 注册新用户
                    if (tokens.length != 3) {
                        System.out.println("[Warning] Usage: register <username> <password>");
                        break;
                    }
                    System.out.println(userManager.register(tokens[1], tokens[2]) ? "Register success."
                            : "Username already exists.");
                    break;

                case "add":
                    // 添加一场会议
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
                    // 查询用户的会议
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
                    // 删除会议
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
                    // 清除用户的所有会议
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
                    // 批处理命令文件
                    if (tokens.length != 2) {
                        System.out.println("[Warning] Usage: batch <filename>");
                        break;
                    }
                    try {
                        // 读取文件中的每一行并逐行处理
                        List<String> lines = Files.readAllLines(Paths.get(tokens[1]));
                        for (String l : lines) {
                            processLine(l);
                        }
                    } catch (IOException e1) {
                        System.out.println("[Warning] Failed to read batch file: " + e1.getMessage());
                    }
                    break;

                case "quit":
                    // 退出系统
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
