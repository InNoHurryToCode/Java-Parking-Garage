package daytime;

/**
 * DaytimeSimulator contains logic to simulate date and time
 * @author Merijn Hendriks
 */
public class DaytimeSimulator {
    private Daytime daytime;

    /**
     * The DaytimeSimulator constructor
     * @author Merijn Hendriks
     * @param daytime
     */
    public DaytimeSimulator(Daytime daytime) {
        this.daytime = daytime;
    }

    /**
     * Update the simulation
     * @author Merijn Hendriks
     */
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

    /**
     * Updates the second
     * @author Merijn Hendriks
     */
    public void updateSeconds() {
        ++this.daytime.seconds;
    }

    /**
     * Updates the minute
     * @author Merijn Hendriks
     */
    public void updateMinutes() {
        if (this.daytime.seconds >= 60) {
            ++this.daytime.minutes;
            this.daytime.seconds = 0;
        }
    }

    /**
     * Updates the hour
     * @author Merijn Hendriks
     */
    public void updateHours() {
        if (this.daytime.minutes >= 60) {
            ++this.daytime.hours;
            this.daytime.minutes = 0;
        }
    }

    /**
     * Updates the day
     * @author Merijn Hendriks
     */
    public void updateDays() {
        if (this.daytime.hours >= 24) {
            ++this.daytime.days;
            ++this.daytime.weekday;
            this.daytime.hours = 0;
        }
    }

    /**
     * Updates the week
     * @author Merijn Hendriks
     */
    public void updateWeeks() {
        if (this.daytime.weekday >= 7) {
            ++this.daytime.weeks;
            this.daytime.weekday = 0;
        }
    }

    /**
     * Updates the month
     * @author Merijn Hendriks
     */
    public void updateMonths() {
        if (this.daytime.days > DaytimeUtility.getDaysInMonth(this.daytime)) {
            ++this.daytime.months;
            this.daytime.days = 1;
        }
    }

    /**
     * Updates the year
     * @author Merijn Hendriks
     */
    public void updateYears() {
        if (this.daytime.months > 12) {
            ++this.daytime.years;
            this.daytime.weeks = 1;
            this.daytime.months = 1;
        }
    }

    /**
     * Get the daytime
     * @author Merijn Hendriks
     * @return the daytime
     */
    public Daytime getDaytime() {
        return this.daytime;
    }
}
