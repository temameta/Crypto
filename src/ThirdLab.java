import java.util.*;

public class ThirdLab {
    static char[] mainAlphabet = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯабвгдеёжзийклмнопрстуфхцчшщъыьэюя1234567890!\"\\#$%&'()*+,-./:;<=>?@[]^_{|}~ ".toCharArray();
    static ArrayList<Character> originalAlphabet = new ArrayList<>(), firstCircuitFirstAlphabet = new ArrayList<>(), firstCircuitSecondAlphabet = new ArrayList<>(), firstCircuitThirdAlphabet = new ArrayList<>(),
            secondCircuitFirstAlphabet = new ArrayList<>(), secondCircuitSecondAlphabet = new ArrayList<>(), secondCircuitThirdAlphabet = new ArrayList<>(),
            thirdCircuitFirstAlphabet = new ArrayList<>(), thirdCircuitSecondAlphabet = new ArrayList<>(), thirdCircuitThirdAlphabet = new ArrayList<>();

    static HashMap<Integer, ArrayList<Character>> firstCircuit = new HashMap<>(), secondCircuit = new HashMap<>(), thirdCircuit = new HashMap<>();
    static HashMap<Integer, HashMap<Integer, ArrayList<Character>>> circuits = new HashMap<>();

    static {
        for (char c : mainAlphabet) {
            originalAlphabet.add(c);
            firstCircuitFirstAlphabet.add(c);
            firstCircuitSecondAlphabet.add(c);
            firstCircuitThirdAlphabet.add(c);
            secondCircuitFirstAlphabet.add(c);
            secondCircuitSecondAlphabet.add(c);
            secondCircuitThirdAlphabet.add(c);
            thirdCircuitFirstAlphabet.add(c);
            thirdCircuitSecondAlphabet.add(c);
            thirdCircuitThirdAlphabet.add(c);
        }
        for (int i = 0; i < circuits.size()* circuits.get(1).size(); i++) {
            Collections.shuffle(circuits.get(i), new Random(3));
        }

        Collections.shuffle(firstCircuitFirstAlphabet, new Random(3));
        Collections.shuffle(firstCircuitSecondAlphabet, new Random(5));
        Collections.shuffle(firstCircuitThirdAlphabet, new Random(8));

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
