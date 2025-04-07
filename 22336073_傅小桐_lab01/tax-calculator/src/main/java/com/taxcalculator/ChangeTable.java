package com.taxcalculator;

import java.util.Scanner;

/**
 * 该类提供了用于修改个人所得税计算器参数的方法，包括起征点、税收范围和税率。
 * 它与 TaxTable 和 TaxView 进行交互，允许用户动态修改税务配置。
 */
public class ChangeTable {

    /**
     * 构造方法。
     */
    public ChangeTable() {
        //
    }

    /**
     * 修改个人所得税的起征点。
     * 用户输入新的起征点，更新到 TaxTable 中。
     * 
     * @param taxtable 税务表，包含个人所得税的各项配置。
     * @param view     用户视图，提供获取用户输入的功能。
     */
    public void ChangeThreshold(TaxTable taxtable, TaxView view) {
        Scanner scanner = view.GetScanner();
        System.out.println("请修改您的起征值: ");
        int new_threshold = scanner.nextInt();
        taxtable.SetThreshold(new_threshold);
        System.out.println("起征值修改成功！");
    }

    /**
     * 修改个人所得税的税收范围。
     * 用户输入新的税收范围（以空格分隔），并确保第一个数字为 0。
     * 
     * @param taxtable 税务表，包含个人所得税的各项配置。
     * @param view     用户视图，提供获取用户输入的功能。
     */
    public void ChangeSplit(TaxTable taxtable, TaxView view) {
        Scanner scanner = view.GetScanner();
        System.out.println("请修改您的税收范围（以空格分隔，首位需为0）：");

        String line = scanner.nextLine().trim();

        // 处理用户输入为空的情况
        while (line.isEmpty()) {
            System.out.println("输入不能为空，请重新输入：");
            line = scanner.nextLine().trim();
        }

        String[] inputArray = line.split(" ");

        // 确保第一个数字为0
        while (!inputArray[0].equals("0")) {
            System.out.println("第一个数字必须为0，请重新输入：");
            line = scanner.nextLine().trim();
            inputArray = line.split(" ");
        }

        double[] newsplit = new double[inputArray.length];
        for (int i = 0; i < inputArray.length; i++) {
            newsplit[i] = Double.parseDouble(inputArray[i]);
        }

        taxtable.SetSplit(newsplit);
        System.out.println("税收范围修改成功！");
    }

    /**
     * 修改个人所得税的税率范围。
     * 用户输入新的税率范围（以空格分隔，且税率不得大于1）。
     * 
     * @param taxtable 税务表，包含个人所得税的各项配置。
     * @param view     用户视图，提供获取用户输入的功能。
     */
    public void ChangeRate(TaxTable taxtable, TaxView view) {
        Scanner scanner = view.GetScanner();
        System.out.println("请修改您的税率范围（以空格分隔，不能超过1）：");

        String line = scanner.nextLine().trim();

        // 处理用户输入为空的情况
        while (line.isEmpty()) {
            System.out.println("输入不能为空，请重新输入：");
            line = scanner.nextLine().trim();
        }

        String[] inputArray = line.split(" ");
        double[] new_rate = new double[inputArray.length];
        for (int i = 0; i < inputArray.length; i++) {
            new_rate[i] = Double.parseDouble(inputArray[i]); // 用Double.parseDouble
        }
        taxtable.SetRate(new_rate);
        System.out.println("税率修改成功！");
    }
}
