package com.example.model;

import java.util.List;

public class Calculator {

    private Calculator() {
        // Evita instanciación
    }

    public static final String EMPTY = "empty";

    public static double multiply(int cantidad, double precio) {
        return cantidad * precio;
    }

    public static double applyDiscount(double amount, double discountPercent) {
        if (discountPercent < 0 || discountPercent > 100) {
            throw new IllegalArgumentException("El porcentaje debe estar entre 0 y 100");
        }
        return amount - (amount * discountPercent / 100.0);
    }

    public static double sum(double a, double b) {
        return a + b;
    }

    public static int subtract(int a, int b) {
        return a - b;
    }

    public static String concat(String a, String b) {
        if (a != null && b != null) {
            return a + b;
        }
        return EMPTY;
    }

    public static double calculateTotal(List<Double> amounts) {
        return amounts.stream().mapToDouble(Double::doubleValue).sum();
    }
}
