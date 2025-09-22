import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.Calculator;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CalculatorTestCase {

    private Calculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new Calculator();
    }

    // 🔹 multiply
    @Test
    @DisplayName("Multiplicación normal (2 * 3 = 6)")
    void testMultiplyNormal() {
        assertEquals(6, calculator.multiply(2, 3));
    }

    @Test
    @DisplayName("Multiplicación con cero")
    void testMultiplyWithZero() {
        assertEquals(0, calculator.multiply(0, 5));
    }

    @Test
    @DisplayName("Multiplicación con números negativos")
    void testMultiplyWithNegatives() {
        assertEquals(-15, calculator.multiply(-3, 5));
    }

    // 🔹 concat
    @Test
    @DisplayName("Concatenación de dos cadenas normales")
    void testConcatNormal() {
        assertEquals("Miguel, Marquez", calculator.concat("Miguel, ", "Marquez"));
    }

    @Test
    @DisplayName("Concatenación con parámetro null devuelve 'empty'")
    void testConcatWithNull() {
        assertEquals("empty", calculator.concat(null, "Mundo"));
        assertEquals("empty", calculator.concat("Hola", null));
    }

    // 🔹 sum
    @Test
    @DisplayName("Suma normal")
    void testSumNormal() {
        assertEquals(9.0, calculator.sum(4.0, 5.0));
    }

    @Test
    @DisplayName("Suma con valores negativos")
    void testSumWithNegatives() {
        assertEquals(-1.0, calculator.sum(3.0, -4.0));
    }

    // 🔹 discount
    @Test
    @DisplayName("Se aplica un descuento válido")
    void testDiscountValid() {
        assertEquals(80.0, calculator.discount(100.0, 0.2)); // 20% descuento
    }

    @Test
    @DisplayName("Descuentos del 0% y 100%")
    void testDiscountZeroAndFull() {
        assertEquals(100.0, calculator.discount(100.0, 0.0));
        assertEquals(0.0, calculator.discount(100.0, 1.0));
    }

    @Test
    @DisplayName("Porcentaje inválido (<0 o >1) lanza IllegalArgumentException")
    void testDiscountInvalid() {
        assertThrows(IllegalArgumentException.class,
                () -> calculator.discount(100.0, -0.5));
        assertThrows(IllegalArgumentException.class,
                () -> calculator.discount(100.0, 1.5));
    }

    // 🔹 calculateTotal
    @Test
    @DisplayName("Lista de importes devuelve la suma correcta")
    void testCalculateTotalValid() {
        List<Double> amounts = Arrays.asList(10.0, 20.0, 30.0);
        assertEquals(60.0, calculator.calculateTotal(amounts));
    }

    @Test
    @DisplayName("Lista vacía devuelve 0.0")
    void testCalculateTotalEmpty() {
        List<Double> amounts = Collections.emptyList();
        assertEquals(0.0, calculator.calculateTotal(amounts));
    }
}
