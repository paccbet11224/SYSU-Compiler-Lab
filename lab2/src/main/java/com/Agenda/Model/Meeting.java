package com.Agenda.Model;

import java.time.LocalDateTime;

/**
 * 表示一个会议，包含会议的标题、发起人、参与人、开始时间和结束时间等信息。
 * 会议通过标题和当前系统时间生成唯一的 ID 来标识。
 */
public class Meeting {

    /** 会议的唯一 ID */
    private final String id;

    /** 会议的标题 */
    private final String title;

    /** 会议的发起人 */
    private final String sponsor;

    /** 会议的参与人 */
    private final String participant;

    /** 会议的开始时间 */
    private final LocalDateTime start;

    /** 会议的结束时间 */
    private final LocalDateTime end;

    /**
     * 构造一个新的会议对象。
     *
     * @param title       会议的标题
     * @param sponsor     会议的发起人
     * @param participant 会议的参与人
     * @param start       会议的开始时间
     * @param end         会议的结束时间
     */
    public Meeting(String title, String sponsor, String participant, LocalDateTime start, LocalDateTime end) {
        this.id = generateId(title);
        this.title = title;
        this.sponsor = sponsor;
        this.participant = participant;
        this.start = start;
        this.end = end;
    }

    /**
     * 生成会议的唯一 ID。
     * 该 ID 由前缀 "MID-" 和基于标题及当前系统时间的哈希值组成。
     *
     * @param base 用于生成 ID 的基础字符串（通常为会议标题）
     * @return 生成的唯一会议 ID
     */
    private String generateId(String base) {
        return "MID-" + Math.abs((base + System.nanoTime()).hashCode());
    }

    /**
     * 获取会议的唯一 ID。
     *
     * @return 会议的 ID
     */
    public String getId() {
        return id;
    }

    /**
     * 获取会议的标题。
     *
     * @return 会议的标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 获取会议的发起人。
     *
     * @return 会议的发起人
     */
    public String getSponsor() {
        return sponsor;
    }

    /**
     * 获取会议的参与人。
     *
     * @return 会议的参与人
     */
    public String getParticipant() {
        return participant;
    }

    /**
     * 获取会议的开始时间。
     *
     * @return 会议的开始时间
     */
    public LocalDateTime getStart() {
        return start;
    }

    /**
     * 获取会议的结束时间。
     *
     * @return 会议的结束时间
     */
    public LocalDateTime getEnd() {
        return end;
    }

    /**
     * 判断当前会议是否与另一个会议的时间范围重叠。
     * 会议时间重叠的定义是：两个会议的时间区间存在交集。
     *
     * @param otherStart 另一个会议的开始时间
     * @param otherEnd   另一个会议的结束时间
     * @return 如果两个会议的时间重叠，返回 true；否则返回 false
     */
    public boolean overlap(LocalDateTime otherStart, LocalDateTime otherEnd) {
        if (!start.isAfter(otherEnd) && !end.isBefore(otherStart)) {
            return true;
        }
        return false;
    }
}
