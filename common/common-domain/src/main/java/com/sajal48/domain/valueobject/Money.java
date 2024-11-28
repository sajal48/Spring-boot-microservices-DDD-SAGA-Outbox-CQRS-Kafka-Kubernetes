package com.sajal48.domain.valueobject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class Money {
    private final BigDecimal amount;

    public Money(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public boolean isGreaterThanZero(){
        return this.amount !=null && this.amount.compareTo(BigDecimal.ZERO) > 0;
    }
    public boolean isGreaterThan(Money otherMoney){
        return this.amount !=null && this.amount.compareTo(otherMoney.getAmount()) > 0;
    }

    public Money add(Money otherMoney){
        return new Money(setScale(this.amount.add(otherMoney.getAmount())));
    }
    public Money subtract(Money otherMoney){
        return new Money(setScale(this.amount.subtract(otherMoney.getAmount())));
    }
    public Money multiply(Money otherMoney){
        return new Money(setScale(this.amount.multiply(otherMoney.getAmount())));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return Objects.equals(amount, money.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(amount);
    }
    private BigDecimal setScale(BigDecimal amount) {
        return amount.setScale(2, RoundingMode.HALF_EVEN);
    }
}
