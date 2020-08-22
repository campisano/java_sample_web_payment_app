package org.example.java_sample_web_payment_app.domain;

import java.math.BigDecimal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MoneyTest {

    @Test
    public void test_creation() {
        Money money = new Money(new BigDecimal(1));

        Assertions.assertEquals(new BigDecimal(1), money.getValue());
    }

    @Test
    public void test_creation_integer() {
        Money money = new Money(Integer.valueOf(1));

        Assertions.assertEquals(new BigDecimal(1), money.getValue());
    }

    @Test
    public void test_creation_zero_value() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Money(BigDecimal.ZERO);
        });
    }

    @Test
    public void test_creation_zero_integer_value() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Money(0);
        });
    }

    @Test
    public void test_creation_null_number() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new Money(null);
        });
    }

    @Test
    public void test_equal_instance() {
        Money money = new Money(new BigDecimal(1));

        Assertions.assertTrue(money.equals(money));
    }

    @Test
    public void test_equal_value() {
        Money money1 = new Money(new BigDecimal(1));
        Money money2 = new Money(Integer.valueOf(1));

        Assertions.assertTrue(money1.equals(money2));
    }

    @Test
    public void test_not_equal_value() {
        Money money1 = new Money(new BigDecimal(1));
        Money money2 = new Money(new BigDecimal(-1));

        Assertions.assertFalse(money1.equals(money2));
    }

    @Test
    public void test_not_equal_instance() {
        Money money1 = new Money(new BigDecimal(1));
        Object money2 = new Object();

        Assertions.assertFalse(money1.equals(money2));
    }
}
