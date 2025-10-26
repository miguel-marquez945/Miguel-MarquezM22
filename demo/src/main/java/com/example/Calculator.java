package com.example;

import java.util.List;
import java.util.Objects;

public class Calculator {

    // Multiplicación básica (admite negativos y cero)
    public static int multiply(int a, int b) {
        return a * b;
    }

    // Concatena dos cadenas; si alguna es null, devuelve "empty"
    public static String concat(String a, String b) {
        if (a == null || b == null) return "empty";
        return a + b;
    }

    // Suma de doubles (admite negativos)
    public static double sum(double a, double b) {
        return a + b;
    }

    /**
     * Aplica un descuento porcentual [0..100] a un importe.
     * @param amount  importe base
     * @param percent porcentaje de descuento (0..100)
     * @return importe con descuento aplicado
     * @throws IllegalArgumentException si percent < 0 o percent > 100
     */
    public static double discount(double amount, double percent) {
        if (percent < 0 || percent > 100) {
            throw new IllegalArgumentException("El porcentaje debe estar entre 0 y 100");
        }
        return amount * (1.0 - (percent / 100.0));
    }

    // Suma todos los importes de una lista. Lista null o vacía → 0.0
    public static double calculateTotal(List<Double> amounts) {
        if (amounts == null || amounts.isEmpty()) return 0.0;
        double total = 0.0;
        for (Double d : amounts) {
            total += (d == null ? 0.0 : d);
        }
        return total;
    }
}
