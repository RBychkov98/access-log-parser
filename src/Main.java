import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        System.out.println("Введите первое число:");
        int firstNumber = new Scanner(System.in).nextInt();
        System.out.println("Введите второе число:");
        int secondNumber = new Scanner(System.in).nextInt();

        int sum = firstNumber + secondNumber;
        int diff = firstNumber - secondNumber;
        int prod = firstNumber * secondNumber;
        double quotient = (double) firstNumber / secondNumber;

        System.out.println("Сумма: "+sum);
        System.out.println("Разность: "+diff);
        System.out.println("Произведение: "+prod);
        System.out.println("Частное: "+quotient);


        int count=0;

        while (true) {
            System.out.println("Укажите путь к файлу");
            String path = new Scanner(System.in).nextLine();
            File file = new File(path);
            boolean fileExists = file.exists();
            boolean isDirecory = file.isDirectory();
            ArrayList<Integer> lengths = new ArrayList<Integer>();

            if (!fileExists || isDirecory ) {
                System.out.println("Указанный файл не существует или указанный путь является путём к папке");
                continue;
            } else {
                System.out.println("Путь указан верно");
                count++;
                System.out.println("Это файл номер " + count);
            }

            try {
                FileReader fileReader = new FileReader(path);
                BufferedReader reader =
                        new BufferedReader(fileReader);
                String line;
                while ((line = reader.readLine()) != null) {
                    int length = line.length();
                    if (length > 1024) throw new RuntimeException("В файле имеется строка длиннее 1024 символа. Таких длинных строк в файле быть не должно.");
                    lengths.add(length);
                }
                System.out.println("Общее колличество строк в файле: " + lengths.size());
                System.out.println("Длинна самой длинной строки в файле: " + Collections.max(lengths));
                System.out.println("Длинна самой короткой строки в файле: " + Collections.min(lengths));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }
}

