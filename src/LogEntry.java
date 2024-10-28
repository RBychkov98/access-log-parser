import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

public class LogEntry {
    private final ArrayList<Integer> indexOfSpaces = new ArrayList<>();
    private final String ipAdress;
    private final LocalDateTime time;
    private final String method;
    private final String reqPath;
    private final int codeOfAnswer;
    private final int weightOfData;
    private final String referer;
    private final String userAgentString;
    private UserAgent userAgent;



    public LogEntry(String line) {
       char[] lineChars = line.toCharArray();
        for (int i = 0; i < lineChars.length; i++) {
            if (lineChars[i] == ' ') indexOfSpaces.add(i);
        }
        String dateTimeStr = line.substring(line.indexOf('[') + 1, line.indexOf(']'));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss Z", Locale.ENGLISH);

        this.ipAdress = line.substring(0, line.indexOf(' '));
        this.time = LocalDateTime.parse(dateTimeStr, formatter);
        this.method = line.substring(indexOfSpaces.get(4) + 2, indexOfSpaces.get(5));
        this.reqPath = line.substring(indexOfSpaces.get(5) + 1, indexOfSpaces.get(6));
        this.codeOfAnswer = Integer.parseInt(line.substring(indexOfSpaces.get(7) + 1, indexOfSpaces.get(8)));
        this.weightOfData = Integer.parseInt(line.substring(indexOfSpaces.get(8) + 1, indexOfSpaces.get(9)));
        this.referer = line.substring(indexOfSpaces.get(9) + 2, indexOfSpaces.get(10) - 1);
        this.userAgentString = line.substring(indexOfSpaces.get(10) + 2);

        this.userAgent = new UserAgent(userAgentString);
    }

    @Override
    public String toString() {
        return "LogEntry{" +
                "ipAdress='" + ipAdress + '\'' +
                ", time=" + time +
                ", method='" + method + '\'' +
                ", reqPath='" + reqPath + '\'' +
                ", codeOfAnswer=" + codeOfAnswer +
                ", weightOfData=" + weightOfData +
                ", referer='" + referer + '\'' +
                ", userAgentString='" + userAgentString + '\'' +
                ", userAgent=" + userAgent +
                '}';
    }

    public String getIpAdress() {
        return ipAdress;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public String getMethod() {
        return method;
    }

    public String getReqPath() {
        return reqPath;
    }

    public int getCodeOfAnswer() {
        return codeOfAnswer;
    }

    public int getWeightOfData() {
        return weightOfData;
    }

    public String getReferer() {
        return referer;
    }

    public String getUserAgentString() {
        return userAgentString;
    }

    public UserAgent getUserAgent() {
        return userAgent;
    }
}

