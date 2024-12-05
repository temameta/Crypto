import java.util.Scanner;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public class Main {
    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(System.in);
            Sender matvei = new Sender();

            System.out.println("Введите числа a и b через \"Enter\" для Нади, меньше 23");
            int a = Integer.parseInt(sc.nextLine());
            int b = Integer.parseInt(sc.nextLine());
            Reciever nadya = new Reciever(matvei, a, b);

            System.out.println("Введите текст для документа Матвея");
            String text = sc.nextLine();

            if (matvei.addDocument(text)) System.out.println("Документ добавлен");
            else System.out.println("Документ уже существует");

            System.out.println(matvei);

            nadya.calculateZ(matvei.getDocuments().get(0));
            matvei.calculateW(nadya.getZ());
            System.out.println(nadya.signatureVerification(matvei.getDocuments().get(0), matvei.getW()));

        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            System.out.println("FATAL ERROR " + e.getLocalizedMessage());
        }
    }
}

