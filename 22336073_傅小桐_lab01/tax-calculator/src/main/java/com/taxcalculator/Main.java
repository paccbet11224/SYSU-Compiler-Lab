package com.taxcalculator;

/**
 * 程序的入口类，负责启动个人所得税计算器应用。
 * 在这个类中，创建了 TaxView 的实例并调用 display() 方法来展示用户界面。
 * 
 * @author Fu Xiaotong
 * @version 1.0
 */
public class Main {

    /**
     * 程序入口方法，启动税务计算器应用。
     * 该方法创建 TaxView 实例并调用 display() 方法显示主界面。
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        try {
            // 创建视图和计算器实例
            TaxView taxView = new TaxView();
            // 显示信息
            taxView.display();
        } catch (Exception e) {
            System.out.println("出现了错误：" + e.getMessage());
            e.printStackTrace(); // 输出错误信息，便于调试
        }
    }
}
