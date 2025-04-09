# Agenda设计说明

​                                                                  ——22336073傅小桐

## 一、文件结构

![image-20250409195012060](C:\Users\lenovo\AppData\Roaming\Typora\typora-user-images\image-20250409195012060.png)

**doc**文件夹里是生成好的javadoc

**src\main**文件夹里是Agenda的实现。其中AgendaService是程序的入口，CommandProcessor负责处理输入的命令行，并给出反馈；Manager文件夹里是MeetingManager和UserManager，这两个类提供管理User和Meeting的接口；Model文件夹里是User和Meeting，这两个类提供了User和Meeting的基本属性。

**src\test**文件夹是测试板块，负责测试程序运行是否正确。

**agenda.bat**是Agenda项目的运行脚本，双击该文件即可运行程序。

**batch.txt**里提前写好了若干命令，在程序内输入$batch batch.txt即可自动运行txt文件里的命令。

**pom.xml**规定了maven项目所需要的插件等。

## 二、UML类图

![bac9415e8348a6af1a74066f0b1fe4e](C:\Users\lenovo\Documents\WeChat Files\wxid_uroqp7wdpnl912\FileStorage\Temp\bac9415e8348a6af1a74066f0b1fe4e.png)

由UML类图可得，Agenda涉及到了六个类，分别为：

**AgendaService：**议程服务类，提供命令行接口供用户交互。该类负责初始化用户管理器、会议管理器和命令处理器，并启动命令行交互式服务。

**CommandProcessor：**命令处理器类，用于解析和执行用户输入的命令。该类支持注册、添加会议、查询会议、删除会议、清空会议等操作，并能批量处理命令文件。

**MeetingManager：**会议管理类，用于管理用户的会议，包括添加、查询、删除会议等功能。本类提供了对会议的添加、查询、删除等操作，并处理会议冲突的检测。

**UserManager：**用户管理类，用于注册、认证和管理用户信息。本类提供了注册、身份认证、查询用户等功能。

**Meeting：**表示一个会议，包含会议的标题、发起人、参与人、开始时间和结束时间等信息。会议通过标题和当前系统时间生成唯一的 ID 来标识。

**User：**表示一个用户，包含用户的用户名、密码和已参与的会议 ID 列表。用户可以添加、删除和清空其参与的会议 ID。

## 三、类的详解

### AgendaService

```
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
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.print("$ "); // 打印提示符
                String line = scanner.nextLine(); // 读取用户输入
                processor.processLine(line); // 处理输入的命令
            }
        }
    }
}
```

`AgendaService` 类是本议程管理系统的主控制模块，承担系统的启动、初始化以及命令行交互逻辑的核心职责。其设计目标是为用户提供一个简洁高效的命令行界面（CLI），以便对议程系统中的用户信息与会议数据进行交互式管理。

程序通过 `main` 方法作为执行入口，采用交互式命令行界面实现用户操作。其主要流程如下：

1. **模块初始化**：依次创建 `UserManager`、`MeetingManager` 与 `CommandProcessor` 的实例。
2. **交互式循环**：通过 Java 标准输入类 `Scanner` 实现一个循环结构，持续监听用户输入。
3. **命令执行调度**：将读取到的用户指令传递给 `CommandProcessor`，由其完成后续解析与操作执行。
4. **资源管理**：使用 `try-with-resources` 结构自动释放输入流资源，保证程序健壮性与安全性。

### CommandProcessor

```
//核心函数
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
```

`processLine` 方法是命令行议程管理系统中的指令解析与调度核心，用于接收并处理来自用户的单条指令。该方法设计体现了面向对象中的**命令分发模型（Command Dispatch Model）**，通过结构化的字符串解析与模块化调用，实现对系统功能的动态控制。

### MeetingManager

```
public class MeetingManager {

    /** 存储会议ID和会议对象的映射 */
    private final Map<String, Meeting> meetings = new HashMap<>();
    /** 用户管理器，用于获取和管理用户信息 */
    private final UserManager userManager;

    public MeetingManager(UserManager userManager) {
        ......
    }
    
    public String addMeeting(String sponsor, String participant, LocalDateTime start, LocalDateTime end, String title) {
    ......
    }

    public List<Meeting> queryMeetings(String username, LocalDateTime start, LocalDateTime end) {
        ......
    }

    public boolean deleteMeeting(String username, String title) {
        ......
    }

 
    public void clearMeetings(String username) {
       ......
    }
}
```

**MeetingManager(UserManager)**	构造方法，初始化会议管理器与用户管理器
**addMeeting(...)**	添加会议，进行用户验证与时间冲突检查
**queryMeetings(...)**	查询指定用户在某一时间段内的会议
**deleteMeeting(...)**	删除用户发起的某个会议（按标题）
**clearMeetings(...)**	清空用户发起的所有会议记录

### UserManager

类似于MeetingManager：

**UserManager()** 构造方法，初始化用户映射表
**register(String, String)** 注册新用户，若用户名不存在则添加用户信息
**authenticate(String, String)** 验证用户名和密码是否匹配，实现身份认证

```
public boolean authenticate(String username, String password) {
        return users.containsKey(username) && users.get(username).getPassword().equals(password);
    }
```

**getUser(String)** 获取指定用户名对应的用户对象
**userExists(String)** 判断指定用户名是否已注册

### Meeting

**Meeting(String, String, String, LocalDateTime, LocalDateTime)** 构造方法，初始化会议对象并生成唯一ID
**generateId(String)** 私有方法，根据标题和系统时间生成会议ID

```
private String generateId(String base) {
        return "MID-" + Math.abs((base + System.nanoTime()).hashCode());
    }
```

**getId()** 获取会议的唯一ID
**getTitle()** 获取会议标题
**getSponsor()** 获取会议发起人
**getParticipant()** 获取会议参与人
**getStart()** 获取会议开始时间
**getEnd()** 获取会议结束时间
**overlap(LocalDateTime, LocalDateTime)** 判断会议时间是否与指定时间区间重叠

```
 public boolean overlap(LocalDateTime otherStart, LocalDateTime otherEnd) {
        if (!start.isAfter(otherEnd) && !end.isBefore(otherStart)) {
            return true;
        }
        return false;
    }
```

### User

**User(String, String)** 构造方法，创建用户并初始化用户名、密码和会议ID列表
**getUsername()** 获取用户的用户名
**getPassword()** 获取用户的密码
**getMeetingIds()** 获取用户参与的所有会议ID列表
**addMeetingId(String)** 向用户的会议ID列表中添加一个ID
**removeMeetingId(String)** 从用户的会议ID列表中移除指定ID
**clearMeetings()** 清空用户的会议ID列表

## 四、实现效果

双击agenda.bat启动程序：

![image-20250409204035608](C:\Users\lenovo\AppData\Roaming\Typora\typora-user-images\image-20250409204035608.png)

输入相应命令：

![image-20250409204310702](C:\Users\lenovo\AppData\Roaming\Typora\typora-user-images\image-20250409204310702.png)

我们创建了两个用户，分别为fxt和fff，并成功创建了一个会议。

在非会议时间查询该程序，是没有输出的。

最后我们清除了fxt的所有会议。

### 异常处理

不能重复创建名字相同的用户：

![image-20250409204515734](C:\Users\lenovo\AppData\Roaming\Typora\typora-user-images\image-20250409204515734.png)

命令行要输入合规的指令：

![image-20250409204607867](C:\Users\lenovo\AppData\Roaming\Typora\typora-user-images\image-20250409204607867.png)

会议时间不能冲突：

![image-20250409204626288](C:\Users\lenovo\AppData\Roaming\Typora\typora-user-images\image-20250409204626288.png)

### 批处理命令

batch.txt：

![image-20250409205257378](C:\Users\lenovo\AppData\Roaming\Typora\typora-user-images\image-20250409205257378.png)

![image-20250409205229519](C:\Users\lenovo\AppData\Roaming\Typora\typora-user-images\image-20250409205229519.png)

可见里面成功创建了用户，同时也对错误操作进行了正确的响应。

### 退出程序

输入quit会退出程序

![image-20250409205413262](C:\Users\lenovo\AppData\Roaming\Typora\typora-user-images\image-20250409205413262.png)

## 五、Test板块

![image-20250409205540947](C:\Users\lenovo\AppData\Roaming\Typora\typora-user-images\image-20250409205540947.png)

![image-20250409205556272](C:\Users\lenovo\AppData\Roaming\Typora\typora-user-images\image-20250409205556272.png)

## 六、Javadoc

在doc文件夹中

![image-20250409205805427](C:\Users\lenovo\AppData\Roaming\Typora\typora-user-images\image-20250409205805427.png)

![image-20250409205819738](C:\Users\lenovo\AppData\Roaming\Typora\typora-user-images\image-20250409205819738.png)

![image-20250409205831875](C:\Users\lenovo\AppData\Roaming\Typora\typora-user-images\image-20250409205831875.png)

![image-20250409205850337](C:\Users\lenovo\AppData\Roaming\Typora\typora-user-images\image-20250409205850337.png)