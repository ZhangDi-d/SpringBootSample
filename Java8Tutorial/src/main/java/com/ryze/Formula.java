package com.ryze;

/**
 * Created by xueLai on 2019/8/1.
 * @author xuelai
 */
public interface Formula {
    double calculate(int a);

    default double sqrt(int b) {
        return Math.sqrt(b);
    }
}
