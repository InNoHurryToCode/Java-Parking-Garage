package daytime;

/**
 * DaytimeUtility contains useful functions which shouldn't be part
 * of Daytime, but are relevant to daytime calculations
 * @author Merijn Hendriks
 */
public class DaytimeUtility {
    /**
     * Get the time period of the day
     * @author Merijn Hendriks
     * @param daytime the daytime
     * @return the time period of the day
     */
    public static TimePeriod getTimePeriod(Daytime daytime) {
        if (daytime.hours == 0 && daytime.minutes == 0 && daytime.seconds == 0) {
            return TimePeriod.MIDNIGHT;
        } else if (daytime.hours == 12 && daytime.minutes == 0 && daytime.seconds == 0) {
            return TimePeriod.NOON;
        } else if (daytime.hours >= 6 && daytime.hours < 12) {
            return TimePeriod.MORNING;
        } else if (daytime.hours >= 12 && daytime.hours < 18) {
            return TimePeriod.AFTERNOON;
        } else if (daytime.hours >= 18 && daytime.hours < 24) {
            return TimePeriod.EVENING;
        } else if (daytime.hours >= 0 && daytime.hours < 6) {
            return TimePeriod.NIGHT;
        }

        return null;
    }

    /**
     * Get the day of the week
     * @author Merijn Hendriks
     * @param daytime the daytime
     * @return the day of the week
     */
    public static Weekday getWeekday(Daytime daytime) {
        switch (daytime.weekday) {
            case 0:
                return Weekday.MONDAY;

            case 1:
                return Weekday.TUESDAY;

            case 2:
                return Weekday.WEDNESDAY;

            case 3:
                return Weekday.THURSDAY;

            case 4:
                return Weekday.FRIDAY;

            case 5:
                return Weekday.SATURDAY;

            case 6:
                return Weekday.SUNDAY;
        }

        return null;
    }

    /**
     * Get the month
     * @author Merijn Hendriks
     * @param daytime the daytime
     * @return the month
     */
    public static Month getMonth(Daytime daytime) {
        switch (daytime.months) {
            case 1:
                return Month.JANUARY;

            case 2:
                return Month.FEBRUARY;

            case 3:
                return Month.MARCH;

            case 4:
                return Month.APRIL;

            case 5:
                return Month.MAY;

            case 6:
                return Month.JULY;

            case 7:
                return Month.JUNE;

            case 8:
                return Month.AUGUST;

            case 9:
                return Month.SEPTEMBER;

            case 10:
                return Month.OCTROBER;

            case 11:
                return Month.NOVEMBER;

            case 12:
                return Month.DECEMBER;
        }

        return null;
    }

    /**
     * Get the season of the year
     * @author Merijn Hendriks
     * @param daytime the daytime
     * @return the season of the year
     */
    public static Season getSeason(Daytime daytime) {
        if (daytime.months >= 3 && daytime.months < 6) {
            return Season.SPRING;
        } else if (daytime.months >= 6 && daytime.months < 9) {
            return Season.SUMMER;
        } else if (daytime.months >= 9 && daytime.months < 12) {
            return Season.AUTUMN;
        } else if (daytime.months == 12 || daytime.months < 3) {
            return Season.WINTER;
        }

        return null;
    }

    /**
     * Get the amount of days in the current month
     * @author Merijn Hendriks
     * @param daytime the daytime
     * @return the anount of days in the current month
     */
    public static int getDaysInMonth(Daytime daytime) {
        switch (daytime.months) {
            case 1:
            case 4:
            case 6:
            case 8:
            case 10:
            case 12:
                return 31;

            case 3:
            case 5:
            case 7:
            case 9:
            case 11:
                return 30;

            case 2:
                if (daytime.years % 4 == 0) {
                    // leap year
                    return 29;
                } else {
                    return 28;
                }
        }

        return 0;
    }

    /**
     * Get the amount of days in the current year
     * @author Merijn Hendriks
     * @param daytime the daytime
     * @return the amount of days in the current year
     */
    public static int getDaysInYear(Daytime daytime) {
        int days = 0;

        for (int i = 0; i < 12; ++i) {
            days += getDaysInYear(daytime);
        }

        return days;
    }

    /**
     * Get the amount of months passed since year 0001
     * @author Merijn Hendriks
     * @param daytime the daytime
     * @return the amount of months passed since year 0001
     */
    public static int getMonthCountSince0001(Daytime daytime) {
        int months = 0;

        for (int i = 0; i < daytime.years; ++i) {
            months += 12;
        }

        months += daytime.months;

        return months;
    }

    /**
     * Get the amount of days passed since year 0001
     * @param daytime the daytime
     * @return the amount of days passed since year 0001
     */
    public static int getDaysCountSince0001(Daytime daytime) {
        int days = 0;

        for (int i = 0; i < daytime.years; ++i) {
            days += getDaysInYear(daytime);
        }

        for (int i = 0; i < daytime.months; ++i) {
            days += getDaysInMonth(daytime);
        }

        days += daytime.days;

        return days;
    }

    /**
     * Get the amount of weeks passed since year 0001
     * @author Merijn Hendriks
     * @param daytime the daytime
     * @return the amount of weeks passed since year 0001
     */
    public static int getWeeksCountSince0001(Daytime daytime) {
        return getDaysCountSince0001(daytime) / 7;
    }

    /**
     * Get the curent weekday based on amount of days passed since year 0001
     * @author Merijn Hendriks
     * @param daytime the daytime
     * @return the current weekday
     */
    public static int getCurentWeekday(Daytime daytime) {
        return getDaysCountSince0001(daytime) % 7;
    }

    /**
     * Format the text to appear properly with 2 digits
     * @author Merijn Hendriks
     * @param value the value to format
     * @return the formatted string
     */
    public static String formatTwoDigit(int value) {
        if (value < 10) {
            return "0" + value;
        } else {
            return Integer.toString(value);
        }
    }

    /**
     * Format the text to appear properly with 4 digits
     * @author Merijn Hendriks
     * @param value the value to format
     * @return the formatted string
     */
    public static String formatFourDigit(int value) {
        if (value < 10) {
            return "000" + value;
        } else if (value < 100) {
            return "00" + value;
        } else if (value < 1000) {
            return "0" + value;
        }  else {
            return Integer.toString(value);
        }
    }

    /**
     * Get the time as properly formatted String
     * @author Merijn Hendriks
     * @param daytime the daytime
     * @return the formatted string
     */
    public static String getTimeString(Daytime daytime) {
        return formatTwoDigit(daytime.hours) + "-" + formatTwoDigit(daytime.minutes) + "-" + formatTwoDigit(daytime.seconds);
    }

    /**
     * Get the date as properly formatted String
     * @author Merijn Hendriks
     * @param daytime the daytime
     * @return the formatted string
     */
    public static String getDateString(Daytime daytime) {
        return formatTwoDigit(daytime.days) + "/" + formatTwoDigit(daytime.months) + "/" + formatTwoDigit(daytime.years);
    }

    /**
     * Show all daytime properties in the console
     * @author Merijn Hendriks
     * @param daytime the daytime
     */
    public static void printDaytime(Daytime daytime) {
        System.out.println("------------------------------------------------------");
        System.out.println("Daytime time: " + getTimeString(daytime));
        System.out.println("Daytime date: " + getDateString(daytime));
        System.out.println("Daytime time period: " + getTimePeriod(daytime).toString());
        System.out.println("Daytime weekday: " + getWeekday(daytime).toString());
        System.out.println("Daytime month: " + getMonth(daytime).toString());
        System.out.println("Daytime season: " + getSeason(daytime).toString());
    }
}
