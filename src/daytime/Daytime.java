package daytime;

public class Daytime {
    private int seconds;
    private int minutes;
    private int hours;
    private int days;
    private int weekday;
    private int weeks;
    private int months;
    private int years;

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
}
