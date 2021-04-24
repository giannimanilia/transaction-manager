package com.gmaniliapp.transactionmanager.utility.statistics;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collector;

public class BigDecimalSummaryStatistics implements Consumer<BigDecimal> {

    private BigDecimal sum = BigDecimal.ZERO;
    private BigDecimal min;
    private BigDecimal max;
    private long count;

    public static Collector<BigDecimal, ?, BigDecimalSummaryStatistics> calculateStatistics() {
        return Collector.of(BigDecimalSummaryStatistics::new,
            BigDecimalSummaryStatistics::accept, BigDecimalSummaryStatistics::merge);
    }

    public void accept(BigDecimal value) {
        if (count == 0) {
            Objects.requireNonNull(value);
            count = 1;
            sum = value;
            min = value;
            max = value;
        } else {
            sum = sum.add(value);
            if (min.compareTo(value) > 0) {
                min = value;
            }
            if (max.compareTo(value) < 0) {
                max = value;
            }
            count++;
        }
    }

    public BigDecimalSummaryStatistics merge(
        BigDecimalSummaryStatistics bigDecimalSummaryStatistics) {
        if (bigDecimalSummaryStatistics.count > 0) {
            if (count == 0) {
                count = bigDecimalSummaryStatistics.count;
                sum = bigDecimalSummaryStatistics.sum;
                min = bigDecimalSummaryStatistics.min;
                max = bigDecimalSummaryStatistics.max;
            } else {
                sum = sum.add(bigDecimalSummaryStatistics.sum);
                if (min.compareTo(bigDecimalSummaryStatistics.min) > 0) {
                    min = bigDecimalSummaryStatistics.min;
                }
                if (max.compareTo(bigDecimalSummaryStatistics.max) < 0) {
                    max = bigDecimalSummaryStatistics.max;
                }
                count += bigDecimalSummaryStatistics.count;
            }
        }
        return this;
    }

    public long getCount() {
        return count;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public BigDecimal getAverage() {
        return count < 2 ? sum : sum.divide(BigDecimal.valueOf(count), MathContext.DECIMAL128);
    }

    public BigDecimal getMin() {
        return min;
    }

    public BigDecimal getMax() {
        return max;
    }
}