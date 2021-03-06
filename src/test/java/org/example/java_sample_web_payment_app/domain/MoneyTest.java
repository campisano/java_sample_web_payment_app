package org.example.java_sample_web_payment_app.domain;

import java.math.BigDecimal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MoneyTest {

    @Test
    public void test_creation() throws Exception {
        Money money = new Money(new BigDecimal(1));

        Assertions.assertEquals(new BigDecimal(1), money.getValue());
    }

    @Test
    public void test_creation_integer() throws Exception {
        Money money = new Money(Integer.valueOf(1));

        Assertions.assertEquals(new BigDecimal(1), money.getValue());
    }

    @Test
    public void test_creation_null_number() throws Exception {
        Assertions.assertThrows(DomainValidationException.class, () -> {
            new Money(null);
        });
    }

    @Test
    public void test_equal_instance() throws Exception {
        Money money = new Money(new BigDecimal(1));

        Assertions.assertTrue(money.equals(money));
    }

    @Test
    public void test_equal_value() throws Exception {
        Money money1 = new Money(new BigDecimal(1));
        Money money2 = new Money(Integer.valueOf(1));

        Assertions.assertTrue(money1.equals(money2));
    }

    @Test
    public void test_not_equal_value() throws Exception {
        Money money1 = new Money(new BigDecimal(1));
        Money money2 = new Money(new BigDecimal(-1));

        Assertions.assertFalse(money1.equals(money2));
    }

    @Test
    public void test_not_equal_instance() throws Exception {
        Money money1 = new Money(new BigDecimal(1));
        Object money2 = new Object();

        Assertions.assertFalse(money1.equals(money2));
    }

    @Test
    public void test_is_positive() throws Exception {
        Money money = new Money(new BigDecimal(1));

        Assertions.assertTrue(money.isPositive());
        Assertions.assertFalse(money.isNegative());
    }

    @Test
    public void test_is_positive_zero() throws Exception {
        Money money = new Money(new BigDecimal(0));

        Assertions.assertTrue(money.isPositive());
    }

    @Test
    public void test_is_zero() throws Exception {
        Money money = new Money(new BigDecimal(0));

        Assertions.assertTrue(money.isZero());
    }

    @Test
    public void test_is_negative() throws Exception {
        Money money = new Money(new BigDecimal(-1));

        Assertions.assertTrue(money.isNegative());
        Assertions.assertFalse(money.isPositive());
    }
}
