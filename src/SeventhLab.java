import java.util.*;

public class SeventhLab {
    static List<String> alphabet = new ArrayList<>(Arrays.asList("АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯабвгдеёжзийклмнопрстуфхцчшщъыьэюя1234567890!\"\\#$%&'()*+,-./:;<=>?@[]^_{|}~ ".split("")));
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String text = "", request = "", encryptedText = "", decryptedText = "", key = "";
        while (!request.equals("STOP")) {
            System.out.println("Что вы хотите сделать?\n1.Зашифровать текст\n2.Расшифровать текст\nВведите цифру:");
            request = sc.nextLine();
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
        }
    }

    // Функция шифровки текста
    static String encryption(String text, String key) {
        StringBuilder encryptedText = new StringBuilder();
        List<String> textArray = new ArrayList<>(Arrays.asList(text.split(""))),
                keyArray = new ArrayList<>(Arrays.asList(key.split("")));
        for (int i = 0; i < textArray.size(); i++) {
            int indexSymbolText = alphabet.indexOf(textArray.get(i));
            int indexSymbolKey = alphabet.indexOf(keyArray.get(Math.floorMod(i, keyArray.size())));
            int indexSymbolCipher = Math.floorMod(indexSymbolText + indexSymbolKey, alphabet.size());
            encryptedText.append(alphabet.get(indexSymbolCipher));
        }
        return encryptedText.toString();
    }

    // Функция расшифровки зашифрованного текста
    static String decryption(String encryptedText, String key) {
        StringBuilder decryptedText = new StringBuilder();
        List<String> textArray = new ArrayList<>(Arrays.asList(encryptedText.split(""))),
                keyArray = new ArrayList<>(Arrays.asList(key.split("")));
        for (int i = 0; i < textArray.size(); i++) {
            int indexSymbolCipher = alphabet.indexOf(textArray.get(i));
            int indexSymbolKey = alphabet.indexOf(keyArray.get(Math.floorMod(i, keyArray.size())));
            int indexSymbolText = Math.floorMod(indexSymbolCipher - indexSymbolKey + alphabet.size(), alphabet.size());
            decryptedText.append(alphabet.get(indexSymbolText));
        }
        return decryptedText.toString();
    }
}

