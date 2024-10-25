import java.time.Duration;
import java.time.LocalDateTime;

public class Statistics {
    private long totalTraffic;
    private LocalDateTime minTime;
    private LocalDateTime maxTime;


    public void addEntry(LogEntry lEnt) {
        totalTraffic += lEnt.getWeightOfData();
        if (lEnt.getTime().isBefore(minTime)) {
            this.minTime = lEnt.getTime();
        }
        if (lEnt.getTime().isAfter(maxTime)) {
            this.maxTime = lEnt.getTime();
        }
    }

    public void getTrafficRate() {
        Duration duration = Duration.between(minTime, maxTime);
        double durHours = (double) duration.toMinutes() /60;
        double scale = Math.pow(10, 2);
        double hours = Math.round(durHours * scale) / scale;

        int trafficForHour = (int) (totalTraffic / hours);
        System.out.println("Объем часового траффика - " + trafficForHour);
    }

    public Statistics() {
        this.totalTraffic = 0;
        this.minTime = LocalDateTime.MAX;
        this.maxTime = LocalDateTime.MIN;
    }

    @Override
    public String toString() {
        return "Statistics{" +
                "totalTraffic=" + totalTraffic +
                ", minTime=" + minTime +
                ", maxTime=" + maxTime +
                '}';
    }
}
