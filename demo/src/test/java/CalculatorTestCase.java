import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.Calculator;

import java.util.Arrays;
import java.util.Collections;

public class CalculatorTestCase {

    private Calculator calculadora;

    @BeforeEach
    void setup() {
        calculadora = new Calculator();
    }

    @Test
    void testMultiplyNormal() {
        assertEquals(6, calculadora.multiply(2, 3));
    }

    @Test
    void testMultiplyConCero() {
        assertEquals(0, calculadora.multiply(0, 7));
        assertEquals(0, calculadora.multiply(9, 0));
    }

    @Test
    void testMultiplyConNegativos() {
        assertEquals(-12, calculadora.multiply(-3, 4));
        assertEquals(12, calculadora.multiply(-3, -4));
    }

    @Test
    void testConcatNormal() {
        assertEquals("HolaMundo", calculadora.concat("Hola", "Mundo"));
    }

    @Test
    void testConcatConNull() {
        assertEquals(Calculator.EMPTY, calculadora.concat(null, "Mundo"));
        assertEquals(Calculator.EMPTY, calculadora.concat("Hola", null));
    }

    @Test
    void testSumNormal() {
        assertEquals(9.0, calculadora.sum(4, 5));
    }

    @Test
    void testSumConNegativos() {
        assertEquals(-1.0, calculadora.sum(4, -5));
    }

    @Test
    void testDiscountValido() {
        assertEquals(80.0, calculadora.discount(100, 20));
    }

    @Test
    void testDiscountLimites() {
        assertEquals(100.0, calculadora.discount(100, 0));
        assertEquals(0.0, calculadora.discount(100, 100));
    }

    @Test
    void testDiscountInvalido() {
        assertThrows(IllegalArgumentException.class, () -> calculadora.discount(100, -5));
        assertThrows(IllegalArgumentException.class, () -> calculadora.discount(100, 120));
    }

    @Test
    void testCalculateTotalNormal() {
        assertEquals(60.0, calculadora.calculateTotal(Arrays.asList(10.0, 20.0, 30.0)));
    }

    @Test
    void testCalculateTotalListaVacia() {
        assertEquals(0.0, calculadora.calculateTotal(Collections.emptyList()));
    }
}