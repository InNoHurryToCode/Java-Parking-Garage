package daytime;

public class DaytimeSimulator {
    private Daytime daytime;

    public DaytimeSimulator(Daytime daytime) {
        this.daytime = daytime;
    }

    public void tick() {
        this.updateSeconds();
        this.updateMinutes();
        this.updateHours();
        this.updateDays();
        this.updateWeeks();
        this.updateMonths();
        this.updateYears();

        // debug
        DaytimeUtility.printDaytime(this.daytime);
    }

    public void updateSeconds() {
        ++this.daytime.seconds;
    }

    public void updateMinutes() {
        if (this.daytime.seconds >= 60) {
            ++this.daytime.minutes;
            this.daytime.seconds = 0;
        }
    }

    public void updateHours() {
        if (this.daytime.minutes >= 60) {
            ++this.daytime.hours;
            this.daytime.minutes = 0;
        }
    }

    public void updateDays() {
        if (this.daytime.hours >= 24) {
            ++this.daytime.days;
            ++this.daytime.weekday;
            this.daytime.hours = 0;
        }
    }

    public void updateWeeks() {
        if (this.daytime.weekday >= 7) {
            ++this.daytime.weeks;
            this.daytime.weekday = 0;
        }
    }

    public void updateMonths() {
        if (this.daytime.days > DaytimeUtility.getDaysInMonth(this.daytime)) {
            ++this.daytime.months;
            this.daytime.days = 1;
        }
    }

    public void updateYears() {
        if (this.daytime.months > 12) {
            ++this.daytime.years;
            this.daytime.weeks = 1;
            this.daytime.months = 1;
        }
    }

    public Daytime getDaytime() {
        return this.daytime;
    }
}
