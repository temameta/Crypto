import java.util.*;

public class SixthLab {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String text = "", request = "", encryptedText = "", decryptedText = "", unusedCells = "";
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
                    System.out.println("Введите неиспользуемые ячейки в виде \"x1 y1 x2 y2 ... xn yn\":");
                    unusedCells = sc.nextLine();
                    encryptedText = encryption(text, key, unusedCells);
                    System.out.println("Зашифрованный текст:");
                    System.out.println(encryptedText);
                }
                case "2" -> {
                    System.out.println("Введите зашифрованный текст:");
                    encryptedText = sc.nextLine();
                    System.out.println("Введите ключ:");
                    key = Integer.parseInt(sc.nextLine());
                    System.out.println("Введите неиспользумеые ячейки в виде \"x1 y1 x2 y2 ... xn yn\":");
                    unusedCells = sc.nextLine();
                    decryptedText = decryption(encryptedText, key, unusedCells);
                    System.out.println("Расшифрованный текст:");
                    System.out.println(decryptedText);
                }
                default -> System.out.println("Неверный ввод");
            }
            System.out.println();
        }
    }

    // Функция шифровки текста
    static String encryption(String text, int key, String unusedCells) {
        String[] unusedCellsArray = unusedCells.split(" ");
        StringBuilder encryptedText = new StringBuilder();
        Map<Integer, String> map = new TreeMap<>();
        List<String> textArray = new ArrayList<>(Arrays.asList(text.split(""))),
                keyArray = new ArrayList<>(Arrays.asList((key + "").split(""))),
                unusedCellsList = new ArrayList<>();
        int keySize = keyArray.size();
        for (int i = 0; i < unusedCellsArray.length; i += 2)
            if (i + 1 < unusedCellsArray.length) {
                int number = Integer.parseInt(keyArray.get(Integer.parseInt(unusedCellsArray[i]) - 1));
                int amountOfCiphering = Integer.parseInt(unusedCellsArray[i + 1]);
                unusedCellsList.add(number + " " + amountOfCiphering);
            }
        int num = 0;
        for (String sT : textArray) {
            if (num == keySize) num = 0;
            String number = keyArray.get(num);
            int amountOfCiphering = map.getOrDefault(Integer.parseInt(keyArray.get(num)), "").length() + 1;
            if (unusedCellsList.contains(number + " " + amountOfCiphering)) {
                num++;
                unusedCellsList.remove(number + " " + amountOfCiphering);
            }
            if (num == keySize) num = 0;
            map.put(Integer.parseInt(keyArray.get(num)), map.getOrDefault(Integer.parseInt(keyArray.get(num)), "") + sT);
            num++;
        }
        for (Integer i : map.keySet())
            encryptedText.append(map.get(i));
        return encryptedText.toString();
    }

    // Функция расшифровки зашифрованного текста
    static String decryption(String encryptedText, int key, String unusedCells) {
        String[] unusedCellsArray = unusedCells.split(" ");
        StringBuilder decryptedText = new StringBuilder();
        Map<Integer, List<String>> map = new HashMap<>();
        List<String> textArray = new ArrayList<>(Arrays.asList(encryptedText.split(""))),
                keyArray = new ArrayList<>(Arrays.asList((key + "").split(""))),
                unusedCellsList = new ArrayList<>();
        int keySize = keyArray.size(), textSize = textArray.size();
        int amountOfSymbols = 0;
        for (int i = 0; i < unusedCellsArray.length; i += 2)
            if (i + 1 < unusedCellsArray.length) {
                int number = Integer.parseInt(keyArray.get(Integer.parseInt(unusedCellsArray[i]) - 1));
                int amountOfCiphering = Integer.parseInt(unusedCellsArray[i + 1]);
                unusedCellsList.add(number + " " + amountOfCiphering);
            }
        for (String s : unusedCellsList) {
            String[] sArray = s.split(" ");
            int x = keyArray.indexOf(sArray[0]);
            int y = Integer.parseInt(sArray[1]);
            if (x + (y - 1) * keySize < textSize) amountOfSymbols++;
        }
        for (int i = 0; i < keySize; i++)
            for (int j = 0; j < textSize + amountOfSymbols; j += keySize)
                if (i + j >= textSize + amountOfSymbols) {
                    int number = Integer.parseInt(keyArray.get(i));
                    int amountOfCiphering = (int) Math.ceil((double) (i + j) / keySize);
                    if (!unusedCellsList.contains(number + " " + amountOfCiphering))
                        unusedCellsList.add(number + " " + amountOfCiphering);
                }
        int lastPos = 0;
        for (int i = 1; i < keySize + 1; i++) {
            List<String> list = new ArrayList<>();
            for (int j = 0; j < Math.ceil(((double) textSize + unusedCellsList.size()) / keySize); j++) {
                if (lastPos < textSize) {
                    if (unusedCellsList.contains(i + " " + (j + 1))) {
                        list.add("");
                    } else {
                        list.add(textArray.get(lastPos));
                        lastPos++;
                    }
                }
            }
            map.put(i, list);
        }
        for (int i = 0; i < map.get(1).size(); i++) {
            for (int j = 0; j < keySize; j++) {
                if (i < map.get(Integer.parseInt(keyArray.get(j))).size())
                    decryptedText.append(map.get(Integer.parseInt(keyArray.get(j))).get(i));
            }
        }
        return decryptedText.toString();
    }
}

