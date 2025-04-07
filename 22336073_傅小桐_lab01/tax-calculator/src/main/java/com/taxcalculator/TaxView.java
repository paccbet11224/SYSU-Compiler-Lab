package com.taxcalculator;

import java.util.Scanner;

/**
 * 个人所得税计算可视化页面
 * 
 * TaxView将税收表 与 交互命令可视化在终端，方便用户进行操作。
 * 
 * 主要功能有：
 * 1、显示欢迎信息。
 * 2、显示税收表。
 * 3、显示菜单选项并与用户进行交互。
 */

public class TaxView {
    private Scanner scanner;

    /**
     * 构造函数
     */
    public TaxView() {
        scanner = new Scanner(System.in);
    }

    /**
     * 获取当前用于读取用户输入的 Scanner 对象。
     * 
     * @return scanner
     */
    public Scanner GetScanner() {
        return scanner;
    }

    /**
     * 在终端向用户显示欢迎信息并提供交互的页面。
     */
    public void display() {
        TaxTable taxtable = new TaxTable();
        ChangeTable changetable = new ChangeTable();
        TaxView view = new TaxView();

        System.out.println("欢迎使用个人所得税计算器！");
        DisplayTaxtable(taxtable);
        DisplayOption();
        int op = GetOption();
        ChooseOption(op, taxtable, changetable, view);
    }

    /**
     * 向用户展示此时税收表的各项信息。
     * 
     * @param taxtable 用于获取税务相关数据的 TaxTable 实例
     */
    private void DisplayTaxtable(TaxTable taxtable) {
        System.out.println("个人所得税计算信息：\n");
        System.out.printf("起征点为：%d\n\n", taxtable.GetThreshold());

        // 表头
        System.out.println("+--------------------+------+--------------------+--------+");
        System.out.printf("| %-18s | %-4s | %-18s | %-6s |\n", "Income Start", "To", "Income End", "Rate");
        System.out.println("+--------------------+------+--------------------+--------+");

        // 表格内容
        for (int i = 0; i < taxtable.GetNum() - 1; i++) {
            System.out.printf("| %-18.2f | %-4s | %-18.2f | %5.2f%% |\n",
                    taxtable.GetSplit(i),
                    "至",
                    taxtable.GetSplit(i + 1),
                    taxtable.GetRate(i) * 100); // 转为百分比显示
        }

        // 底线
        System.out.println("+--------------------+------+--------------------+--------+");
    }

    /**
     * 向用户展示可以进行操作的选项。
     */
    private void DisplayOption() {
        System.out.println("1. 输入您的工资并进行计算");
        System.out.println("2. 更改起征点");
        System.out.println("3. 更改税收范围");
        System.out.println("4. 更改税率");
        System.out.println("5. 退出程序");
        System.out.println("请选择模式:");
    }

    /**
     * 获取用户的选项。
     * 
     * @return 将用户输入选项 Scanner 的实例
     */
    private int GetOption() {
        return scanner.nextInt();
    }

    /**
     * 根据用户的选择执行：计算税收、更改起征点、更改收税区间以及更改税率的操作。
     * 
     * @param option      用户的选项
     * @param taxtable    当前的税收表
     * @param changetable 更改税收表的入口
     * @param view        税收表可视化实例
     */
    private void ChooseOption(int option, TaxTable taxtable, ChangeTable changetable, TaxView view) {
        while (option != 5) {
            switch (option) {
                case 1:
                    System.out.println("请输入您的工资：");
                    double salary = scanner.nextDouble();
                    if (salary <= 0) {
                        System.out.println("请输入正确的工资！");
                    } else {
                        double tax = TaxCalculator.calculateTax(taxtable, salary);
                        System.out.printf("您需要支付的税收为： %.2f\n", tax);
                    }
                    System.out.println("------------------------------------------------------------------------\n");
                    break;

                case 2:
                    changetable.ChangeThreshold(taxtable, view);
                    System.out.println("------------------------------------------------------------------------\n");
                    DisplayTaxtable(taxtable);
                    System.out.println("------------------------------------------------------------------------\n");
                    break;

                case 3:
                    changetable.ChangeSplit(taxtable, view);
                    System.out.println("------------------------------------------------------------------------\n");
                    DisplayTaxtable(taxtable);
                    System.out.println("------------------------------------------------------------------------\n");
                    break;

                case 4:
                    changetable.ChangeRate(taxtable, view);
                    System.out.println("------------------------------------------------------------------------\n");
                    DisplayTaxtable(taxtable);
                    System.out.println("------------------------------------------------------------------------\n");
                    break;

                default:
                    System.out.println("请输入正确的数字！");
                    break;
            }
            DisplayOption();
            option = GetOption();
        }
        System.out.println("程序已退出，再见！");
    }
}
