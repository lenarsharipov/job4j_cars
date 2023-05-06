package ru.job4j.cars.util;

import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

/**
 * Utility Timezone class.
 */
@NoArgsConstructor
public class Timezone {

    /**
     * Default Timezone used in app.
     */
    public static final String DEFAULT_TIMEZONE = "UTC";

    /**
     * TIMEZONES constant list persists all supported Timezones.
     */
    public static final List<TimeZone> TIMEZONES;

    static {
        TIMEZONES = new ArrayList<>();
        for (String timeId : TimeZone.getAvailableIDs()) {
            TIMEZONES.add(TimeZone.getTimeZone(timeId));
        }
    }

}
