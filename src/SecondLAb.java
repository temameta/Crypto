import java.util.*;

public class SecondLAb {
    static char[] mainAlphabet = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯабвгдеёжзийклмнопрстуфхцчшщъыьэюя1234567890!\"\\#$%&'()*+,-./:;<=>?@[]^_{|}~ ".toCharArray();
    static ArrayList<Character> originalAlphabet = new ArrayList<>(), firstAlphabet = new ArrayList<>(), secondAlphabet = new ArrayList<>(), thirdAlphabet = new ArrayList<>();
    static ArrayList<ArrayList<Character>> alphabets = new ArrayList<>();

    static {
        for (char c : mainAlphabet) {
            originalAlphabet.add(c);
            firstAlphabet.add(c);
            secondAlphabet.add(c);
            thirdAlphabet.add(c);
        }
        alphabets.add(firstAlphabet);
        alphabets.add(secondAlphabet);
        alphabets.add(thirdAlphabet);

        Collections.shuffle(firstAlphabet, new Random(3));
        Collections.shuffle(secondAlphabet, new Random(5));
        Collections.shuffle(thirdAlphabet, new Random(8));

        System.out.println("Исходный алфавит: " + originalAlphabet);
        System.out.println("Первый алфавит: " + firstAlphabet);
        System.out.println("Второй алфавит: " + secondAlphabet);
        System.out.println("Третий алфавит: " + thirdAlphabet);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String text = "", request = "", encryptedText = "", decryptedText = "";
        System.out.println("Что вы хотите сделать?\n1.Зашифровать текст\n2.Расшифровать текст\nВведите цифру:");
        request = sc.nextLine();
        while (!request.equals("STOP")) {
            switch (request) {
                case "1" -> {
                    System.out.println("Введите текст:");
                    text = sc.nextLine();
                    encryptedText = encryption(text);
                    System.out.println("Зашифрованный текст:");
                    System.out.println(encryptedText);
                }
                case "2" -> {
                    System.out.println("Введите зашифрованный текст:");
                    encryptedText = sc.nextLine();
                    decryptedText = decryption(encryptedText);
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

    // Функция шифровки текста
    static String encryption(String text) {
        String encryptedText = "";
        char[] charText = text.toCharArray();
        int alphabetNum = 0;
        for (char c : charText) {
            encryptedText += alphabets.get(alphabetNum++).get(originalAlphabet.indexOf(c));
            if (alphabetNum > 2) alphabetNum = 0;
        }
        return encryptedText;
    }

    // Функция расшифровки зашифрованного текста
    static String decryption(String encryptedText) {
        String decryptedText = "";
        char[] charEncryptedText = encryptedText.toCharArray();
        int alphabetNum = 0;
        for (char c : charEncryptedText) {
            decryptedText += originalAlphabet.get(alphabets.get(alphabetNum++).indexOf(c));
            if (alphabetNum > 2) alphabetNum = 0;
        }
        return decryptedText;
    }
}