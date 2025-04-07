package com.taxcalculator;

import org.junit.Test;
import static org.junit.Assert.*;

public class TaxCalculatorTest {

    @Test
    public void testTaxCalculator() {
        TaxTable taxtable = new TaxTable();
        assertEquals(0, TaxCalculator.calculateTax(taxtable, 300), 0.1);
        assertEquals(0, TaxCalculator.calculateTax(taxtable, 1600), 0.1);
        assertEquals(0, TaxCalculator.calculateTax(taxtable, 1599), 0.1);
        assertEquals(280, TaxCalculator.calculateTax(taxtable, 4300), 0.1);
        assertEquals(310, TaxCalculator.calculateTax(taxtable, 4500), 0.1);
    }
}
