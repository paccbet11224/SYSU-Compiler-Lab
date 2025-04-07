package com.taxcalculator;

/**
 * TaxTable 类用于存储个人所得税计算所需的基本信息，
 * 包括起征点、税率分级数量、税收区间及对应税率。
 * 可用于查询和修改这些税收参数，以支持个性化税率设置。
 */
public class TaxTable {
    // 起征点
    private int threshold;
    // 税率等级数量（区间数量）
    private int num;
    // 收入区间分割点数组（单位：元）
    private double[] split;
    // 每个收入区间对应的税率数组
    private double[] rate;

    /**
     * 默认构造函数，初始化默认的税收参数。
     * 默认起征点为1600元，分为5个税率等级。
     */
    public TaxTable() {
        threshold = 1600;
        num = 5;
        split = new double[] { 0, 500, 2000, 5000, 20000 };
        rate = new double[] { 0.05, 0.1, 0.15, 0.2, 0.25 };
    }

    /**
     * 获取当前起征点。
     * 
     * @return 当前起征点（单位：元）
     */
    public int GetThreshold() {
        return threshold;
    }

    /**
     * 获取当前的税率等级数量。
     * 
     * @return 当前税率分级数量
     */
    public int GetNum() {
        return num;
    }

    /**
     * 获取指定索引的收入区间分割点。
     * 
     * @param i 分割点索引
     * @return 第 i 个分割点的值
     */
    public double GetSplit(int i) {
        return split[i];
    }

    /**
     * 获取指定索引的税率。
     * 
     * @param i 税率索引
     * @return 第 i 个税率的值
     */
    public double GetRate(int i) {
        return rate[i];
    }

    /**
     * 设置新的起征点。
     * 
     * @param newthreshold 新的起征点（单位：元）
     */
    public void SetThreshold(int newthreshold) {
        threshold = newthreshold;
    }

    /**
     * 设置新的税率等级数量。
     * 
     * @param newnum 新的税率分级数量
     */
    public void SetRank(int newnum) {
        num = newnum;
    }

    /**
     * 设置新的税率数组。仅在所有税率为 0~1 之间时生效。
     * 
     * @param newrate 新的税率数组
     */
    public void SetRate(double[] newrate) {
        for (double i : newrate) {
            if (i > 0 && i < 1) {
                rate = newrate;
            }
        }
    }

    /**
     * 设置新的收入区间分割点数组。仅在所有值为正数时生效。
     * 
     * @param newsplit 新的分割点数组
     */
    public void SetSplit(double[] newsplit) {
        for (double i : newsplit) {
            if (i > 0) {
                split = newsplit;
            }
        }
    }
}
