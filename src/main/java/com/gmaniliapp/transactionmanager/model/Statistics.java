package com.gmaniliapp.transactionmanager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gmaniliapp.transactionmanager.utility.statistics.BigDecimalSummaryStatistics;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class Statistics {

    private final BigDecimal sum;
    private final BigDecimal avg;
    private final BigDecimal max;
    private final BigDecimal min;
    private final long count;

    public Statistics(BigDecimalSummaryStatistics bigDecimalSummaryStatistics) {
        this(bigDecimalSummaryStatistics.getSum(), bigDecimalSummaryStatistics.getAverage(),
            bigDecimalSummaryStatistics.getMax(), bigDecimalSummaryStatistics.getMin(),
            bigDecimalSummaryStatistics.getCount());
    }

    public Statistics(BigDecimal sum, BigDecimal avg, BigDecimal max, BigDecimal min, long count) {
        this.sum = setDefaultScale(sum);
        this.avg = setDefaultScale(avg);
        this.max = setDefaultScale(max);
        this.min = setDefaultScale(min);
        this.count = count;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public BigDecimal getAvg() {
        return avg;
    }

    public BigDecimal getMax() {
        return max;
    }

    public BigDecimal getMin() {
        return min;
    }

    public long getCount() {
        return count;
    }

    @JsonIgnore
    private BigDecimal setDefaultScale(BigDecimal value) {
        if (value != null) {
            value = value.setScale(2, RoundingMode.HALF_UP);
        }
        return value;
    }

    @Override
    public String toString() {
        return "{ sum = " + sum + " - avg = " + avg + " - max = " + max + " - min = " + min
            + " - count = " + count +
            " }";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj.getClass() != this.getClass()) {
            return false;
        }

        final Statistics other = (Statistics) obj;
        return Objects.equals(this.sum, other.sum) && Objects.equals(this.avg, other.avg) &&
            Objects.equals(this.max, other.max) &&
            Objects.equals(this.min, other.min) && Objects.equals(this.count, other.count);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + (this.sum != null ? this.sum.hashCode() : 0);
        hash = 53 * hash + (this.avg != null ? this.avg.hashCode() : 0);
        hash = 53 * hash + (this.max != null ? this.max.hashCode() : 0);
        hash = 53 * hash + (this.min != null ? this.min.hashCode() : 0);
        return hash;
    }
}
