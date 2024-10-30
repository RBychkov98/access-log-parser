import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;

public class Statistics {
    private long totalTraffic;
    private LocalDateTime minTime;
    private LocalDateTime maxTime;
    private HashSet<String> existingPages;
    private HashSet<String> nonExistingPages;
    private HashSet<String> usersIp;
    private HashMap<String, Integer> opSystems;
    private int countOfSystems;
    private HashMap<String, Integer> browsers;
    private int countOfBrowsers;
    private HashMap<String, Double> sharesOfOpSystems;
    private HashMap<String, Double> sharesOfBrowsers;
    double hoursInLogFile;
    int visitsFromBrowsers;
    int failedRequests;

    public void addEntry(LogEntry lEnt) {
        totalTraffic += lEnt.getWeightOfData();

        if (lEnt.getTime().isBefore(minTime)) {
            this.minTime = lEnt.getTime();
        }
        if (lEnt.getTime().isAfter(maxTime)) {
            this.maxTime = lEnt.getTime();
        }

        if(lEnt.getCodeOfAnswer() == 200) {
            existingPages.add(lEnt.getReqPath());
        } else if (lEnt.getCodeOfAnswer() == 404) nonExistingPages.add(lEnt.getReqPath());

        if (String.valueOf(lEnt.getCodeOfAnswer()).substring(0, 1).equals("4") || String.valueOf(lEnt.getCodeOfAnswer()).substring(0, 1).equals("5")) {
            failedRequests ++;
        }

        if(!Objects.equals(lEnt.getUserAgent().getOsType(), "")) {
            if (opSystems.containsKey(lEnt.getUserAgent().getOsType())) {
                opSystems.put(lEnt.getUserAgent().getOsType(), opSystems.get(lEnt.getUserAgent().getOsType()) + 1);
                countOfSystems += 1;
            } else {
                opSystems.put(lEnt.getUserAgent().getOsType(), 1);
                countOfSystems += 1;
            }
        }

        if(!lEnt.getUserAgent().getBrowsers().isEmpty()) {
            for (String br : lEnt.getUserAgent().getBrowsers()) {
                String browser = br;
                if (browser.contains("(")) {
                    browser = browser.replace("(", "");
                }
                if (browser.contains(")")) {
                    browser = browser.replace(")", "");
                }
                if (br.contains("/")) {
                    browser = br.substring(0, br.indexOf('/'));
                }
                if (br.contains("Chrome")) {
                    browser = "Chrome";
                }

                if (browsers.containsKey(browser)) {
                    browsers.put(browser, browsers.get(browser) + 1);
                    countOfBrowsers += 1;
                } else {
                    browsers.put(browser, 1);
                    countOfBrowsers += 1;
                }
            }
        }

        if (!lEnt.getUserAgentString().contains("bot")) {
            visitsFromBrowsers += 1;
            usersIp.add(lEnt.getIpAdress());
        }

    }

    public void calcSharesOfOpSystems() {
        for (Map.Entry<String, Integer> entry : opSystems.entrySet()) {
            sharesOfOpSystems.put(entry.getKey(), Math.round((double)entry.getValue() / countOfSystems * 1000.0) / 1000.0);
        }
        System.out.println("Операционные системы и их доли от общего колличества указанный в логах систем: " + sharesOfOpSystems);
    }

    public void calcSharesOfOpBrowsers() {
        for (Map.Entry<String, Integer> entry : browsers.entrySet()) {
            sharesOfBrowsers.put(entry.getKey(), Math.round((double)entry.getValue() / countOfBrowsers * 1000.0) / 1000.0);
        }
        System.out.println("Браузеры и их доли от общего колличества указанный в логах браузеров: " + sharesOfBrowsers);
    }

    public void getTrafficRate() {
        countHoursInLogFile();
        int trafficForHour = (int) (totalTraffic / hoursInLogFile);
        System.out.println("Объем часового траффика - " + trafficForHour);
        System.out.println(visitsFromBrowsers);
    }

    public void AvgNumbersOfVisitsPerHour() {
        countHoursInLogFile();
        System.out.println("Cреднее количество посещений сайта за час - " + Math.round(visitsFromBrowsers/hoursInLogFile));
    }

    public void AvgNumbersOfFaledRequestsPerHour() {
        countHoursInLogFile();
        System.out.println("Cреднее количество ошибочных запросов за час - " + Math.round(failedRequests/hoursInLogFile));
    }

    public void AvgNumbersOfVisitsByOneUser() {
        System.out.println("Cреднее количество посещений сайта одним пользователем - " + Math.round((float) visitsFromBrowsers /usersIp.size()));
    }

    public void countHoursInLogFile() {
        Duration duration = Duration.between(minTime, maxTime);
        double durHours = (double) duration.toMinutes() /60;
        double scale = Math.pow(10, 2);
        this.hoursInLogFile = Math.round(durHours * scale) / scale;
    }

    public Statistics() {
        this.totalTraffic = 0;
        this.minTime = LocalDateTime.MAX;
        this.maxTime = LocalDateTime.MIN;
        this.existingPages = new HashSet<>();
        this.nonExistingPages = new HashSet<>();
        this.usersIp = new HashSet<>();
        this.opSystems = new HashMap<>();
        this.countOfSystems = 0;
        this.browsers = new HashMap<>();
        this.countOfBrowsers = 0;
        this.sharesOfOpSystems = new HashMap<>();
        this.sharesOfBrowsers = new HashMap<>();
        this.hoursInLogFile = 0;
        this.visitsFromBrowsers = 0;
        this.failedRequests = 0;
    }

    public HashSet<String> getExistingPages() {
        return existingPages;
    }

    public HashSet<String> getNonExistingPages() {
        return nonExistingPages;
    }

    @Override
    public String toString() {
        return "Statistics{" +
                "totalTraffic=" + totalTraffic +
                ", minTime=" + minTime +
                ", maxTime=" + maxTime +
                ", existingPages=" + existingPages +
                ", nonExistingPages=" + nonExistingPages +
                ", opSystems=" + opSystems +
                ", countOfSystems=" + countOfSystems +
                ", browsers=" + browsers +
                ", countOfBrowsers=" + countOfBrowsers +
                ", sharesOfOpSystems=" + sharesOfOpSystems +
                ", sharesOfBrowsers=" + sharesOfBrowsers +
                '}';
    }
}
