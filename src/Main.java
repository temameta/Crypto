import java.util.Scanner;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public class Main {
    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(System.in);
            Sender matvei = new Sender();

            System.out.println("Введите числа a и b через \"Enter\" для Нади, меньше 23.\nЭти числа требуются для создания секретного кода");
            int a = Integer.parseInt(sc.nextLine());
            int b = Integer.parseInt(sc.nextLine());
            Reciever nadya = new Reciever(matvei, a, b);

            System.out.println("Введите текст для документа Матвея");
            String text = sc.nextLine();

            if (matvei.addDocument(text)) System.out.println("Документ добавлен");
            else System.out.println("Документ уже существует");

            System.out.println(matvei);

            nadya.calculateZ(matvei.getDocuments().get(0));
            System.out.println("Число z у Нади: " + nadya.getZ());
            matvei.calculateW(nadya.getZ());
            System.out.println("Число w у Матвея: " + matvei.getW());

            if (nadya.signatureVerification(matvei.getDocuments().get(0), matvei.getW())) {
                System.out.println(matvei);
                System.out.println("Подпись на документе Матвея правильная");
            }
            else {
                System.out.println(matvei);
                System.out.println("Подпись на документе Матвея неправильная");
            }

        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            System.out.println("FATAL ERROR " + e.getLocalizedMessage());
        }
    }
}

