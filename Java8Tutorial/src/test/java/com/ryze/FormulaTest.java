package com.ryze;

import org.junit.Test;

/**
 * Created by xueLai on 2019/8/1.
 *
 * 接口的默认方法(Default Methods for Interfaces)
 */

public class FormulaTest {

    @Test
    public void testSqrt() {


        // 通过匿名内部类方式访问接口
        Formula formula = new Formula() {
            @Override
            public double calculate(int a) {
                return sqrt(a * 100);
            }
        };

        System.out.println(formula.calculate(100));
        System.out.println(formula.sqrt(64));
    }


}
