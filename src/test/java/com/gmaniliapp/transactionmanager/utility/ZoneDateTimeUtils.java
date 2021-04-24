package com.gmaniliapp.transactionmanager.utility;

import java.time.ZoneId;
import java.time.ZonedDateTime;

public class ZoneDateTimeUtils {

    private static final ZoneId UTC_ZONE_ID = ZoneId.of("UTC");

    public static ZonedDateTime generateCurrentZoneDateTime() {
        return ZonedDateTime.now(UTC_ZONE_ID);
    }

    public static ZonedDateTime generateOneMinuteAndOneSecondInThePastZoneDateTime() {
        return generateCurrentZoneDateTime().minusSeconds(61);
    }

}
