import java.util.*;

public class FifthLab {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String text = "", request = "", encryptedText = "", decryptedText = "";
        int key = 0;
        while (!request.equals("STOP")) {
            System.out.println("Что вы хотите сделать?\n1.Зашифровать текст\n2.Расшифровать текст\nВведите цифру:");
            request = sc.nextLine();
            switch (request) {
                case "1" -> {
                    System.out.println("Введите текст:");
                    text = sc.nextLine();
                    System.out.println("Введите ключ:");
                    key = sc.nextInt();
                    encryptedText = encryption(text, key);
                    System.out.println("Зашифрованный текст:");
                    System.out.println(encryptedText);
                }
                case "2" -> {
                    System.out.println("Введите зашифрованный текст:");
                    encryptedText = sc.nextLine();
                    System.out.println("Введите ключ:");
                    key = sc.nextInt();
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
    static String encryption(String text, int key) {
        StringBuilder encryptedText = new StringBuilder();
        TreeMap<Integer, ArrayList<String>> map = new TreeMap<>();
        ArrayList<String> textArray = new ArrayList<>(Arrays.asList(text.split(""))), keyArray = new ArrayList<>(Arrays.asList((key + "").split("")));
        for (int i = 0; i < textArray.size() % keyArray.size(); i++) {
            textArray.add(" ");
        }
        for (int i = 0; i < keyArray.size(); i++) {
            ArrayList<String> list = new ArrayList<>();
            for (int j = 0; j < textArray.size(); j += keyArray.size()) {
                list.add(textArray.get(i + j));
            }
            map.put(Integer.parseInt(keyArray.get(i)), list);
        }
        for (int i = 1; i < keyArray.size() + 1; i++) {
            for (String s : map.get(i))
                encryptedText.append(s);
        }
        return encryptedText.toString();
    }

    // Функция расшифровки зашифрованного текста
    static String decryption(String encryptedText, int key) {
        StringBuilder decryptedText = new StringBuilder();
        HashMap<Integer, ArrayList<String>> map = new HashMap<>();
        ArrayList<String> textArray = new ArrayList<>(Arrays.asList(encryptedText.split(""))), keyArray = new ArrayList<>(Arrays.asList((key + "").split("")));
        int num = 1;
        for (int i = 0; i < textArray.size(); i += textArray.size() / keyArray.size()) {
            ArrayList<String> list = new ArrayList<>();
            for (int j = 0; j < textArray.size() / keyArray.size(); j++) {
                list.add(textArray.get(j + i));
            }
            map.put(num++, list);
        }
        for (int i = 0; i < textArray.size() / keyArray.size(); i++) {
            for (String s : keyArray) {
                decryptedText.append(map.get(Integer.parseInt(s)).get(i));
            }
        }
        return decryptedText.toString().trim();
    }
}

