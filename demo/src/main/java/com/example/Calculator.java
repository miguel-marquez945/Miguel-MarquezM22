package com.example;

import java.util.List;

public class Calculator {
    public static final String EMPTY = "empty";

    public static int multiply(int a, int b) { return a * b; }

    public static String concat(String a, String b) {
        if (a == null || b == null) return EMPTY;
        return a + b;
    }

    public static double sum(double a, double b) { return a + b; }

    public static double discount(double amount, double percent) {
        if (percent < 0 || percent > 100) throw new IllegalArgumentException("El porcentaje debe estar entre 0 y 100");
        return amount * (1.0 - percent / 100.0);
        }

    public static double calculateTotal(List<Double> amounts) {
        if (amounts == null || amounts.isEmpty()) return 0.0;
        double total = 0.0;
        for (Double d : amounts) total += (d == null ? 0.0 : d);
        return total;
    }
}
