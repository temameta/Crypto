import java.util.Scanner;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Введите числа p и q через \"Enter\" для Артёма");
        Person matvei = new Person(Integer.parseInt(sc.nextLine()), Integer.parseInt(sc.nextLine()));
        System.out.println("Введите числа p и q через \"Enter\" для Бориса");
        Person nadya = new Person(Integer.parseInt(sc.nextLine()), Integer.parseInt(sc.nextLine()));
        System.out.println("Артём:\n" + matvei);
        System.out.println("Борис:\n" + nadya);
        System.out.println("Что вы хотите сделать?\n1. Добавить документ Матвею;\n2. Добавить документ Наде;\n3. Вывести документы Матвея;\n4. Вывести документы Надя;\n5. Проверить подпись Матвея на документе;\n6. Проверить подпись Нади на документе.\n\nВведите stop для остановки");
        String command = sc.nextLine();
        while (!command.equalsIgnoreCase("stop")) {
            try {
                switch (command) {
                    case "1" -> {
                        System.out.println("Введите текст для документа:");
                        command = sc.nextLine();
                        if (matvei.addDocument(command)) System.out.println("Документ добавлен");
                        else System.out.println("Документ уже существует");
                    }
                    case "2" -> {
                        System.out.println("Введите текст для документа:");
                        command = sc.nextLine();
                        if (nadya.addDocument(command)) System.out.println("Документ добавлен");
                        else System.out.println("Документ уже существует");
                    }
                    case "3" -> {
                        System.out.println("Артём:\n" + matvei);
                    }
                    case "4" -> {
                        System.out.println("Борис:\n" + nadya);
                    }
                    case "5" -> {
                        System.out.println("Введите текст:");
                        String text = sc.nextLine();
                        System.out.println("Введите подпись:");
                        int signature = Integer.parseInt(sc.nextLine());
                        System.out.println(matvei.signatureVerification(text, signature));
                        System.out.println(matvei.signatureVerification(matvei.getDocuments().get(0)));
                    }
                    case "6" -> {
                        System.out.println("Введите текст:");
                        String text = sc.nextLine();
                        System.out.println("Введите подпись:");
                        int signature = Integer.parseInt(sc.nextLine());
                        System.out.println(nadya.signatureVerification(text, signature));
                    }
                    default -> System.out.println("Неверный ввод");
                }
            } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
                System.out.println("FATAL ERROR " + e.getLocalizedMessage());
            }
            System.out.println("Что вы хотите сделать?\n1. Добавить документ Артёму;\n2. Добавить документ Борису;\n3. Вывести документы Артёма;\n4. Вывести документы Бориса;\n5. Проверить подпись Артёма на документе;\n6. Проверить подпись Бориса на документе.\n\nВведите stop для остановки");
            command = sc.nextLine();
        }
    }
}

