package daytime;

/**
 * Daytime contains date time values
 * @author Merijn Hendriks
 */
public class Daytime {
    public int seconds;
    public int minutes;
    public int hours;
    public int days;
    public int weekday;
    public int weeks;
    public int months;
    public int years;

    /**
     * The daytime constructor
     * @author Merijn Hendriks
     */
    public Daytime() {
        this.seconds = 0;
        this.minutes = 0;
        this.hours = 0;
        this.days = 1;
        this.weekday = 0;
        this.weeks = 1;
        this.months = 1;
        this.years = 1;
    }

    public Daytime getDaytime() {
        return this;
    }

    public void setDaytime(Daytime daytime) {
        this.seconds = daytime.seconds;
        this.minutes = daytime.minutes;
        this.hours = daytime.hours;
        this.days = daytime.days;
        this.weekday = daytime.weekday;
        this.weeks = daytime.weeks;
        this.months = daytime.months;
        this.years = daytime.years;
    }

    public void setDaytime(int seconds, int minutes, int hours, int days, int weekday, int weeks, int months, int years) {
        this.seconds = seconds;
        this.minutes = minutes;
        this.hours = hours;
        this.days = days;
        this.weekday = weekday;
        this.weeks = weeks;
        this.months = months;
        this.years = years;
    }

    public boolean equals(Object obj) {
        if (obj instanceof Daytime) {
            Daytime other = (Daytime)obj;

            if (this.seconds == other.seconds && this.minutes == other.minutes && this.hours == other.hours
                && this.days == other.days && this.weekday == other.weekday && this.weeks == other.weeks
                && this.months == other.months && this.years == other.years) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
