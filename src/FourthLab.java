import java.util.*;

public class FourthLab {
    static char[] mainAlphabet = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯабвгдеёжзийклмнопрстуфхцчшщъыьэюя1234567890!\"\\#$%&'()*+,-./:;<=>?@[]^_{|}~ ".toCharArray();
    static ArrayList<Character> alphabet = new ArrayList<>();

    static {
        for (char c : mainAlphabet) {
            alphabet.add(c);
        }
        System.out.println("Таблица замены:");
        for (int i = 0; i < alphabet.size(); i++) {
            int index = i;
            for (int j = 0; j < alphabet.size(); j++) {
                System.out.print(alphabet.get(Math.floorMod(index++, alphabet.size())));
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String text = "", request = "", encryptedText = "", decryptedText = "", key = "";
        System.out.println("Что вы хотите сделать?\n1.Зашифровать текст\n2.Расшифровать текст\nВведите цифру:");
        request = sc.nextLine();
        while (!request.equals("STOP")) {
            switch (request) {
                case "1" -> {
                    System.out.println("Введите текст:");
                    text = sc.nextLine();
                    System.out.println("Введите ключ:");
                    key = sc.nextLine();
                    encryptedText = encryption(text, key);
                    System.out.println("Зашифрованный текст:");
                    System.out.println(encryptedText);
                }
                case "2" -> {
                    System.out.println("Введите зашифрованный текст:");
                    encryptedText = sc.nextLine();
                    System.out.println("Введите ключ:");
                    key = sc.nextLine();
                    decryptedText = decryption(encryptedText, key);
                    System.out.println("Расшифрованный текст:");
                    System.out.println(decryptedText);
                }
                default -> System.out.println("Неверный ввод");
            }
            System.out.println();
            System.out.println("Что вы хотите сделать?\n1.Зашифровать текст\n2.Расшифровать текст\nВведите цифру:");
            request = sc.nextLine();
        }
    }

    // Функция шифровки текста    static String encryption(String text, String key) {
    static String encryption(String text, String key) {
        String encryptedText = "";
        char[] charText = text.toCharArray(), charKey = key.toCharArray();
        int charIndex = 0;
        for (char c : charText) {
            encryptedText += alphabet.get(Math.floorMod(alphabet.indexOf(charKey[Math.floorMod(charIndex++, charKey.length)]) + alphabet.indexOf(c), alphabet.size()));
        }
        return encryptedText;
    }

    // Функция расшифровки зашифрованного текста
    static String decryption(String encryptedText, String key) {
        String decryptedText = "";
        char[] charEncryptedText = encryptedText.toCharArray(), charKey = key.toCharArray();
        int charIndex = 0;
        for (char c : charEncryptedText) {
            decryptedText += alphabet.get(Math.floorMod(alphabet.indexOf(c) - alphabet.indexOf(charKey[Math.floorMod(charIndex++, charKey.length)]), alphabet.size()));
        }
        return decryptedText;
    }
}