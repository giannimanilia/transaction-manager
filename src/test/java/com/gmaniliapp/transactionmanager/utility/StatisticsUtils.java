package com.gmaniliapp.transactionmanager.utility;

import com.gmaniliapp.transactionmanager.model.Statistics;
import java.math.BigDecimal;

public class StatisticsUtils {

    public static Statistics generateEmptyStatistics() {
        return new Statistics(BigDecimal.ZERO, BigDecimal.ZERO, null, null, 0);
    }

}
