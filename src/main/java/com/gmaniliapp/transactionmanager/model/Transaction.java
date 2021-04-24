package com.gmaniliapp.transactionmanager.model;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Objects;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

public class Transaction {

    @NotNull(message = "amount is mandatory")
    private final BigDecimal amount;

    @NotNull(message = "timestamp is mandatory")
    @PastOrPresent(message = "timestamp must be in the past or in the present")
    private final ZonedDateTime timestamp;

    public Transaction(BigDecimal amount, ZonedDateTime timestamp) {
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "{ amount = " + amount + " - timestamp = " + timestamp + " }";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj.getClass() != this.getClass()) {
            return false;
        }

        final Transaction other = (Transaction) obj;
        return Objects.equals(this.amount, other.amount) && Objects
            .equals(this.timestamp, other.timestamp);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + (this.amount != null ? this.amount.hashCode() : 0);
        hash = 53 * hash + (this.timestamp != null ? this.timestamp.hashCode() : 0);
        return hash;
    }

}
