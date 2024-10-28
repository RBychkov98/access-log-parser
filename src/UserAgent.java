import java.util.ArrayList;

public class UserAgent {
    private String osType = "";
    private final ArrayList<String> browsers = new ArrayList<>();


    public UserAgent(String userAgent) {
        if (userAgent.contains("Windows")) {
            this.osType = "Windows";
        }
        if (userAgent.contains("Linux")) {
            this.osType = "Linux";
        }
        if (userAgent.contains("Mac OS")) {
            this.osType = "Mac OS";
        }

        if (userAgent.contains("Edge") || userAgent.contains("Firefox") || userAgent.contains("Chrome") || userAgent.contains("Opera") || userAgent.contains("Safari")) {
            String[] parts = userAgent.split(" ");
            for (String p : parts) {
                if (p.contains("Edge") || p.contains("Firefox") || p.contains("Chrome") || p.contains("Opera") || p.contains("Safari")) {
                    if (p.contains("\"")) p = p.replace("\"", "");
                    this.browsers.add(p);
                }
            }
        }
    }

    public String getOsType() {
        return osType;
    }

    public ArrayList<String> getBrowsers() {
        return browsers;
    }

    @Override
    public String toString() {
        return "UserAgent{" +
                "osType='" + osType + '\'' +
                ", browsers=" + browsers +
                '}';
    }
}
