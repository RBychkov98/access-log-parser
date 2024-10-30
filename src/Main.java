import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        int count=0;

        while (true) {
            System.out.println("Укажите путь к файлу");
            String path = new Scanner(System.in).nextLine();
            File file = new File(path);
            boolean fileExists = file.exists();
            boolean isDirecory = file.isDirectory();
            ArrayList<Integer> lengths = new ArrayList<Integer>();
            ArrayList<String> userAgents = new ArrayList<String>();
            ArrayList<String> partsWithSearchBots = new ArrayList<String>();
            ArrayList<String> searchBots = new ArrayList<String>();
            int countOfYandexBots = 0;
            int countOfGooglebots = 0;
            Statistics stat = new Statistics();

            if (!fileExists || isDirecory ) {
                System.out.println("Указанный файл не существует или указанный путь является путём к папке");
                continue;
            } else {
                System.out.println("Путь указан верно");
                count++;
                System.out.println("Это файл номер " + count);
            }

            try {
                countReqAndTakeUserAgents(path, lengths, userAgents, stat);

                findPartsWithSearchBotsInUA( userAgents, partsWithSearchBots);

                FindSearchBots(partsWithSearchBots, searchBots);

                for (String bot : searchBots) {
                    if (bot.contains("YandexBot")) {
                        countOfYandexBots += 1;
                    }
                    if (bot.contains("Googlebot")) {
                        countOfGooglebots += 1;
                    }
                }

                countPercents(lengths.size(), countOfYandexBots, countOfGooglebots);

                stat.getTrafficRate();

                stat.calcSharesOfOpSystems();

                stat.calcSharesOfOpBrowsers();

                stat.AvgNumbersOfVisitsPerHour();

                stat.AvgNumbersOfFaledRequestsPerHour();

                stat.AvgNumbersOfVisitsByOneUser();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private static void countReqAndTakeUserAgents(String path, ArrayList<Integer> lengths, ArrayList<String> userAgents, Statistics stat) throws IOException {
        FileReader fileReader = new FileReader(path);
        BufferedReader reader =
                new BufferedReader(fileReader);
        String line;
        while ((line = reader.readLine()) != null) {
            LogEntry lEnt = new LogEntry(line);
            System.out.println(lEnt);
            stat.addEntry(lEnt);
            int length = line.length();
            if (length > 1024) throw new RuntimeException("В файле имеется строка длиннее 1024 символа. Таких длинных строк в файле быть не должно.");
            lengths.add(length);
            if (line.contains("Mozilla")) {
                String userData = line.substring(line.indexOf("Mozilla"));
                userAgents.add(userData);
            }
        }
        System.out.println("Общее колличество строк в файле: " + lengths.size());
    }

    private static void findPartsWithSearchBotsInUA(ArrayList<String> userAgents, ArrayList<String> partsWithSearchBots) {
        for (String uA : userAgents) {
            try {
                String searchBot = uA.substring(uA.indexOf(' ') + 1, uA.indexOf(')') + 1);
                if (searchBot.charAt(0) == '(') partsWithSearchBots.add(searchBot.substring(1, searchBot.indexOf(')')));
            } catch (StringIndexOutOfBoundsException _) {

            }
        }
    }

    public static void FindSearchBots(ArrayList<String> partsWithSearchBots, ArrayList<String> searchBots) {
        for (String part : partsWithSearchBots) {
            String[] parts = part.split(";");
            ArrayList<String> fractionsOfPart = new ArrayList<String>();
            for (String p : parts) {
                fractionsOfPart.add(p.trim());
            }
            if (fractionsOfPart.size() >= 2) {
                String searchBot = fractionsOfPart.get(1);
                if (searchBot.contains("/"))  {
                    searchBots.add(searchBot.substring(0, searchBot.indexOf('/')));
                } else searchBots.add(searchBot);
            }
        }
    }

    private static void countPercents(int lengths, int countOfYandexBots, int countOfGooglebots) {
        double onePercent = (double) lengths /100;
        DecimalFormat df = new DecimalFormat("#.##");
        double percentOfYandexBotRequests = countOfYandexBots/onePercent;
        double percentOfGoogleBotRequests = countOfGooglebots/onePercent;

        System.out.println("Процент запросов Яндекс бота от общего колличества запросов - " + df.format(percentOfYandexBotRequests) + '%');
        System.out.println("Процент запросов Google бота от общего колличества запросов - " + df.format(percentOfGoogleBotRequests) + '%');
    }
}

