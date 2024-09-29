import java.util.Scanner;

public class Main {
    static String alphabet = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯабвгдеёжзийклмнопрстуфхцчшщъыьэюя1234567890!\"\\#$%&'()*+,-./:;<=>?@[]^_{|}~ ";
    static char[] charAlphabet = alphabet.toCharArray();
    static int alphabetLength = alphabet.length();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String text, request, encryptedText = "", decryptedText = "";
        int key;
        System.out.println("Что вы хотите сделать?\n1.Зашифровать текст\n2.Расшифровать текст\n3.Вывести алфавит со сдвигом\nВведите цифру:");
        request = sc.nextLine();
        while (!request.equals("STOP")) {
            switch (request) {
                case "1" -> {
                    System.out.println("Введите текст:");
                    text = sc.nextLine();
                    System.out.println("Введите ключ:");
                    key = Integer.parseInt(sc.next()) % alphabetLength;
                    encryptedText = encryption(text, key);
                    System.out.println("Зашифрованный текст:");
                    System.out.println(encryptedText);
                }
                case "2" -> {
                    System.out.println("Введите зашифрованный текст:");
                    encryptedText = sc.nextLine();
                    System.out.println("Введите ключ:");
                    key = Integer.parseInt(sc.next()) % alphabetLength;
                    decryptedText = decryption(encryptedText, key);
                    System.out.println("Расшифрованный текст:");
                    System.out.println(decryptedText);
                }
                case "3" -> {
                    System.out.println("Введите ключ:");
                    key = Integer.parseInt(sc.next()) % alphabetLength;
                    System.out.println("Новый алфавит со сдвигом:");
                    displayNewAlphabet(alphabet, key);
                }
                default -> System.out.println("Неверный ввод");
            }
            System.out.println();
            System.out.println("Что вы хотите сделать?\n1.Зашифровать текст\n2.Расшифровать текст\n3.Вывести алфавит со сдвигом\nВведите цифру:");
            request = sc.next();
        }
        System.out.println(decryptedText);
    }

    // Функция шифровки текста
    static String encryption(String text, int key) {
        String encryptedText = "";
        char[] charText = text.toCharArray();
        for (int i = 0; i < charText.length; i++)
            encryptedText += charAlphabet[(alphabet.indexOf(charText[i]) + key) % alphabetLength];
        return encryptedText;
    }

    // Функция расшифровки зашифрованного текста
    static String decryption(String encryptedText, int key) {
        String decryptedText = "";
        char[] charEncryptedText = encryptedText.toCharArray();
        for (int i = 0; i < charEncryptedText.length; i++) {
            decryptedText += charAlphabet[Math.floorMod(alphabet.indexOf(charEncryptedText[i]) - key, alphabetLength)];
        }
        return decryptedText;
    }

    static void displayNewAlphabet(String alphabet, int key) {
        String newAlphabet = "";
        for (int i = 0; i < alphabetLength; i++)
            newAlphabet += charAlphabet[(alphabet.indexOf(charAlphabet[i]) + key) % alphabetLength];
        System.out.println(newAlphabet);
    }
}
