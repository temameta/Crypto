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
                    key = Integer.parseInt(sc.nextLine());
                    encryptedText = encryption(text, key);
                    System.out.println("Зашифрованный текст:");
                    System.out.println(encryptedText);
                }
                case "2" -> {
                    System.out.println("Введите зашифрованный текст:");
                    encryptedText = sc.nextLine();
                    System.out.println("Введите ключ:");
                    key = Integer.parseInt(sc.nextLine());
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
        Map<Integer, List<String>> map = new TreeMap<>();
        List<String> textArray = new ArrayList<>(Arrays.asList(text.split(""))),
                keyArray = new ArrayList<>(Arrays.asList((key + "").split("")));
        int keySize = keyArray.size(), textSize = textArray.size();
        for (int i = 0; i < keySize; i++) {
            List<String> list = new ArrayList<>();
            for (int j = 0; j < textSize; j += keySize)
                if (i + j < textSize)
                    list.add(textArray.get(i + j));
            map.put(Integer.parseInt(keyArray.get(i)), list);
        }
        for (Integer i : map.keySet())
            for (String s : map.get(i))
                encryptedText.append(s);
        return encryptedText.toString();
    }

    // Функция расшифровки зашифрованного текста
    static String decryption(String encryptedText, int key) {
        StringBuilder decryptedText = new StringBuilder();
        Map<Integer, List<String>> map = new HashMap<>();
        List<String> textArray = new ArrayList<>(Arrays.asList(encryptedText.split(""))),
                keyArray = new ArrayList<>(Arrays.asList((key + "").split("")));
        int keySize = keyArray.size(), textSize = textArray.size();
        List<Integer> iterators = new ArrayList<>(Collections.nCopies(keySize, 0));
        int num = 1;
        // Определение сколько раз позицию использовать при расшифровке
        for (int i = 0; i < keySize; i++)
            for (int j = 0; j < textSize; j += keySize)
                if (i + j < textSize) {
                    int index = Integer.parseInt(keyArray.get(i)) - 1;
                    int elem = iterators.get(Integer.parseInt(keyArray.get(i))-1)+1;
                    iterators.set(index, elem);
                }
        int lastPos = 0;
        // Создание словаря, согласно которому будет понятно, на каком месте должны стоять символы
        for (Integer i : iterators) {
            List<String> list = new ArrayList<>();
            for (int j = 0; j < i; j++)
                if (lastPos + j < textSize)
                    list.add(textArray.get(j + lastPos));
            lastPos += i;
            map.put(num++, list);
        }
        // Перенос символов из словаря в возвращаемую строку
        for (int i = 0; i < Collections.max(iterators); i++) {
            for (int j = 0; j < keySize; j++) {
                if (i < map.get(Integer.parseInt(keyArray.get(j))).size())
                    decryptedText.append(map.get(Integer.parseInt(keyArray.get(j))).get(i));
            }
        }
        return decryptedText.toString().trim();
    }
}

