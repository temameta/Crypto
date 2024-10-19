import java.util.*;

public class ThirdLab {
    static final int AMOUNT_OF_CIRCUITS = 3;
    static char[] mainAlphabet = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯабвгдеёжзийклмнопрстуфхцчшщъыьэюя1234567890!\"\\#$%&'()*+,-./:;<=>?@[]^_{|}~ ".toCharArray();
    static ArrayList<Character> originalAlphabet = new ArrayList<>();
    static HashMap<Integer, HashMap<Integer, ArrayList<Character>>> circuits = new HashMap<>(AMOUNT_OF_CIRCUITS);

    static {
        int seed = 1;
        for (char c : mainAlphabet) {
            originalAlphabet.add(c);
        }
        System.out.println(originalAlphabet);
        for (int i = 0; i < AMOUNT_OF_CIRCUITS; i++) {
            //Создание контура
            HashMap<Integer, ArrayList<Character>> circuit = new HashMap<>();
            //Добавление контура в словарь
            circuits.put(i, circuit);
            for (int j = 0; j < 2 + new Random(i).nextInt(6); j++) {
                //Создание алфавита
                ArrayList<Character> alphabet = new ArrayList<>();
                //Добавление алфавита в контур
                circuits.get(i).put(j, alphabet);
                //Наполнение алфавита
                for (char c : mainAlphabet) {
                    circuits.get(i).get(j).add(c);
                }
                //Перемешивание алфавита
                Collections.shuffle(circuits.get(i).get(j), new Random(seed += 3));
                //Вывод алфавита
                System.out.println(circuits.get(i).get(j));
            }
            //Пустая строка между контурами
            System.out.println();
        }
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
        int alphabetNum = 0, circuitNum = 0;
        for (char c : charText) {
            encryptedText += circuits.get(circuitNum).get(alphabetNum).get(originalAlphabet.indexOf(c));
            if (++alphabetNum >= circuits.get(circuitNum).size()) {
                alphabetNum = 0;
                if (++circuitNum >= AMOUNT_OF_CIRCUITS) circuitNum = 0;
            }
        }
        return encryptedText;
    }

    // Функция расшифровки зашифрованного текста
    static String decryption(String encryptedText) {
        String decryptedText = "";
        char[] charEncryptedText = encryptedText.toCharArray();
        int alphabetNum = 0, circuitNum = 0;
        for (char c : charEncryptedText) {
            decryptedText += originalAlphabet.get(circuits.get(circuitNum).get(alphabetNum).indexOf(c));
            if (++alphabetNum >= circuits.get(circuitNum).size()) {
                alphabetNum = 0;
                if (++circuitNum >= AMOUNT_OF_CIRCUITS) circuitNum = 0;
            }
        }
        return decryptedText;
    }
}
