package org.mfr.commons;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {
    public static final int COLUMN_WIDTH = 7000;

    public static final int HEADER_HEIGHT = 600;
    public static final int HEADER_CALENDAR_MONTH_HEIGHT = 750;
    public static final int HEADER_CALENDAR_DAYS_WEEK_HEIGHT = 750;
    public static final int COLUMN_HEIGHT = 500;

    public static final int HEADER_FONT_SIZE = 14;
    public static final int HEADER_CALENDAR_MONTH_FONT_SIZE = 20;
    public static final int HEADER_CALENDAR_DAYS_WEEK_SIZE = 16;
    public static final int FONT_SIZE = 12;

    public static final boolean HEADER_FONT_BOLD = true;
    public static final boolean HEADER_CALENDAR_MONTH_FONT_BOLD = true;
    public static final boolean HEADER_CALENDAR_DAYS_WEEK_BOLD = false;
    public static final boolean FONT_BOLD = false;

    public static final String DEFAULT_FONT = "Arial";
    public static final String HEADER_FONT = "Arial";
    public static final String FONT = "Arial";
}
