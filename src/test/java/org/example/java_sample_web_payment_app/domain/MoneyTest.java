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
}
