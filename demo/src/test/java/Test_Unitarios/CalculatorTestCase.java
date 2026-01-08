package Test_Unitarios;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import com.example.model.Calculator;

import java.util.Arrays;
import java.util.Collections;

public class CalculatorTestCase {

    @Test
    void testMultiplyNormal() {
        assertEquals(6.0, Calculator.multiply(2, 3.0));
    }

    @Test
    void testMultiplyConCero() {
        assertEquals(0.0, Calculator.multiply(0, 7.0));
        assertEquals(0.0, Calculator.multiply(9, 0.0));
    }

    @Test
    void testConcatNormal() {
        assertEquals("HolaMundo", Calculator.concat("Hola", "Mundo"));
    }

    @Test
    void testConcatConNull() {
        assertEquals(Calculator.EMPTY, Calculator.concat(null, "Mundo"));
        assertEquals(Calculator.EMPTY, Calculator.concat("Hola", null));
    }

    @Test
    void testSumNormal() {
        assertEquals(9.0, Calculator.sum(4, 5));
    }

    @Test
    void testSumConNegativos() {
        assertEquals(-1.0, Calculator.sum(4, -5));
    }

    @Test
    void testApplyDiscountValido() {
        assertEquals(80.0, Calculator.applyDiscount(100, 20));
    }

    @Test
    void testApplyDiscountLimites() {
        assertEquals(100.0, Calculator.applyDiscount(100, 0));
        assertEquals(0.0, Calculator.applyDiscount(100, 100));
    }

    @Test
    void testApplyDiscountInvalido() {
        assertThrows(IllegalArgumentException.class, () -> Calculator.applyDiscount(100, -5));
        assertThrows(IllegalArgumentException.class, () -> Calculator.applyDiscount(100, 120));
    }

    @Test
    void testCalculateTotalNormal() {
        assertEquals(60.0, Calculator.calculateTotal(Arrays.asList(10.0, 20.0, 30.0)));
    }

    @Test
    void testCalculateTotalListaVacia() {
        assertEquals(0.0, Calculator.calculateTotal(Collections.emptyList()));
    }

    @Test
    void testSubtract() {
        assertEquals(-1, Calculator.subtract(4, 5));
    }
}
