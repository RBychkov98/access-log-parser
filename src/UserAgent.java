import java.util.ArrayList;

public class UserAgent {
    private String osType = "";
    private final ArrayList<String> brousers = new ArrayList<>();


    public UserAgent(String userAgent) {
        if (userAgent.contains("Windows") || userAgent.contains("Linux") || userAgent.contains("Mac ")) {
            String uA;
            if (userAgent.contains(")")) {
                uA = userAgent.substring(userAgent.indexOf('(') + 1,userAgent.indexOf(')'));
            } else uA = userAgent.substring(userAgent.indexOf('(') + 1);
            String[] parts = uA.split(";");
            for (String p : parts) {
                if (p.contains("Windows") || p.contains("Linux") || p.contains("Mac ")) {
                    this.osType = p.trim();
                }
            }
        }

        if (userAgent.contains("Edge") || userAgent.contains("Firefox") || userAgent.contains("Chrome") || userAgent.contains("Opera") || userAgent.contains("Safari")) {
            String[] parts = userAgent.split(" ");
            for (String p : parts) {
                if (p.contains("Edge") || p.contains("Firefox") || p.contains("Chrome") || p.contains("Opera") || p.contains("Safari")) {
                    if (p.contains("\"")) p = p.replace("\"", "");
                    this.brousers.add(p);
                }
            }
        }
    }

    public String getOsType() {
        return osType;
    }

    public ArrayList<String> getBrousers() {
        return brousers;
    }

    @Override
    public String toString() {
        return "UserAgent{" +
                "osType='" + osType + '\'' +
                ", brousers=" + brousers +
                '}';
    }
}
