package com.Agenda;

import java.util.Scanner;

import com.Agenda.Manager.MeetingManager;
import com.Agenda.Manager.UserManager;

/**
 * 议程服务类，提供命令行接口供用户交互。
 * 该类负责初始化用户管理器、会议管理器和命令处理器，并启动命令行交互式服务。
 * 
 * @author Fu Xiaotong
 */
public class AgendaService {

    /**
     * 主方法，程序入口。
     * 启动用户命令行界面，接收用户输入并通过命令处理器处理命令。
     * 
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        // 创建用户管理器、会议管理器和命令处理器
        UserManager userManager = new UserManager();
        MeetingManager meetingManager = new MeetingManager(userManager);
        CommandProcessor processor = new CommandProcessor(userManager, meetingManager);

        // 启动命令行界面，等待用户输入
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.print("$ "); // 打印提示符
                String line = scanner.nextLine(); // 读取用户输入
                processor.processLine(line); // 处理输入的命令
            }
        }
    }
}
