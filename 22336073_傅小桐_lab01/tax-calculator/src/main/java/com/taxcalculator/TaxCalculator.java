package com.taxcalculator;

/**
 * TaxCalculator 类用于根据指定的税收参数（TaxTable）和收入金额，
 * 计算个人应缴纳的税款。
 */
public class TaxCalculator {

    /**
     * 根据提供的 TaxTable 和用户工资，计算应缴纳的个人所得税。
     *
     * @param taxtable 税收参数表，包含起征点、收入区间和税率等信息
     * @param salary   用户的工资收入（单位：元）
     * @return 应缴纳的税额（单位：元）
     */
    public static double calculateTax(TaxTable taxtable, double salary) {
        double tax = 0;

        double after = salary - taxtable.GetThreshold();
        if (after <= 0) {
            return 0; // 收入未达到起征点，无需缴税
        }

        for (int i = 0; i < taxtable.GetNum() - 1; i++) {
            double lower = taxtable.GetSplit(i);
            double upper = taxtable.GetSplit(i + 1);

            if (after > upper) {
                tax += (upper - lower) * taxtable.GetRate(i);
            } else {
                tax += (after - lower) * taxtable.GetRate(i);
                break;
            }
        }

        return tax;
    }
}
