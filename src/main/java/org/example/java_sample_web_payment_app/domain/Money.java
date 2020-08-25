package org.example.java_sample_web_payment_app.domain;

import java.math.BigDecimal;
import java.util.Objects;

public class Money {

    private BigDecimal value;

    public Money(BigDecimal value) throws DomainValidationException {
        construct(value);
    }

    public Money(int value) throws DomainValidationException {
        BigDecimal bd = new BigDecimal(value);
        construct(bd);
    }

    public BigDecimal getValue() {
        return this.value;
    }

    public boolean isPositive() {
        return (value.compareTo(BigDecimal.ZERO) > 0);
    }

    public boolean isNegative() {
        return !isPositive();
    }

    private void construct(BigDecimal value) throws DomainValidationException {
        ensureCreable(value);
        this.value = value;
    }

    private static void ensureCreable(BigDecimal value) throws DomainValidationException {
        if (value == null) {
            throw new DomainValidationException("Value 'null' is invalid");
        }
        if (value.compareTo(BigDecimal.ZERO) == 0) {
            throw new DomainValidationException("Value 'zero' is invalid");
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Money)) {
            return false;
        }
        Money other = (Money) obj;
        return Objects.equals(value, other.value);
    }
}
