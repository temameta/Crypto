import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.*;
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
            } else {
                System.out.println(matvei);
                System.out.println("Подпись на документе Матвея неправильная");
            }

        } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
            System.out.println("FATAL ERROR " + e.getLocalizedMessage());
        }
    }


    public static class Sender {
        private int w;
        private int q = 23;
        private List<SignaturedDocument> documents;
        private List<Integer> openKey;
        private List<Integer> secretKey;

        public Sender() {
            calculateKeys();

            this.documents = new ArrayList<>();
        }

        private void setOpenKey(List<Integer> openKey) {
            this.openKey = openKey;
        }


        private void setSecretKey(List<Integer> secretKey) {
            this.secretKey = secretKey;
        }

        public void setW(int w) {
            this.w = w;
        }

        public int getW() {
            return w;
        }

        private int calculateA(int p, int q) {
            int a = 0;
            int b = 0;
            for (int i = 0; i < q; i++) {
                b = (int) Math.pow(p, i);
                a = AdditionalMath.modExp(b, (p - 1) / q, p);
                if (a != 1) break;
            }
            return a;
        }

        private void calculateKeys() {
            int p = 47;
            int a = calculateA(p, q);
            int x = 15;
            int y = AdditionalMath.modExp(a, x, p);
            List<Integer> list = new ArrayList<>();
            list.add(p);
            list.add(a);
            list.add(y);
            setOpenKey(list);
            list = new ArrayList<>();
            list.add(x);
            list.add(p);
            setSecretKey(list);
        }

        public void calculateW(int z) {
            int reciprocalX = AdditionalMath.reciprocalNum(secretKey.get(0), q);
            int w = AdditionalMath.modExp(z, reciprocalX, getOpenKey().get(0));
            setW(w);
        }

        public boolean addDocument(String text) throws UnsupportedEncodingException, NoSuchAlgorithmException {
            SignaturedDocument document = new SignaturedDocument(text, this.secretKey);
            if (!this.documents.contains(document)) return this.documents.add(document);
            return false;
        }

        public List<SignaturedDocument> getDocuments() {
            return documents;
        }

        public List<Integer> getOpenKey() {
            return openKey;
        }

        @Override
        public String toString() {
            StringBuilder ret = new StringBuilder();
            ret.append(String.format("Open key: \"%d, %d\"", this.openKey.get(0), this.openKey.get(1)));
            ret.append("\nDocuments:\n");
            for (SignaturedDocument d : this.documents)
                ret.append(d);
            return ret.toString();
        }
    }

    public static class Reciever {
        private int z;
        private int a;
        private int b;
        private List<SignaturedDocument> documents;
        private List<Integer> openKey;
        private List<Integer> secretKey;

        public Reciever(Sender sender, int a, int b) {
            this.documents = new ArrayList<>();
            setA(a);
            setB(b);
            setOpenKey(sender.getOpenKey());
        }

        public void setA(int a) {
            this.a = a;
        }

        public void setB(int b) {
            this.b = b;
        }

        public void setZ(int z) {
            this.z = z;
        }

        public int getZ() {
            return z;
        }

        private void setOpenKey(List<Integer> openKey) {
            this.openKey = openKey;
        }

        private void setSecretKey(List<Integer> secretKey) {
            this.secretKey = secretKey;
        }

        public void calculateZ(SignaturedDocument document) {
            int p = this.getOpenKey().get(0);
            int alpha = this.getOpenKey().get(1);
            int y = this.getOpenKey().get(2);
            int signature = document.getSignature();
            setZ(AdditionalMath.modExp(AdditionalMath.modExp(signature, a, p), AdditionalMath.modExp(y, b, p), p));
        }

        public boolean addDocument(String text) throws UnsupportedEncodingException, NoSuchAlgorithmException {
            SignaturedDocument document = new SignaturedDocument(text, this.secretKey);
            if (!this.documents.contains(document)) return this.documents.add(document);
            return false;
        }

        public List<SignaturedDocument> getDocuments() {
            return documents;
        }

        public List<Integer> getOpenKey() {
            return openKey;
        }

        public boolean signatureVerification(SignaturedDocument document, int wA) throws UnsupportedEncodingException, NoSuchAlgorithmException {
            String text = document.getText();
            int hashText = AdditionalMath.hashFunction(text);
            int p = this.openKey.get(0);
            int alpha = this.openKey.get(1);
            int wB = AdditionalMath.modExp(AdditionalMath.modExp(hashText, a, p), AdditionalMath.modExp(alpha, b, p), p);
            return wA == wB;
        }

        @Override
        public String toString() {
            StringBuilder ret = new StringBuilder();
            ret.append(String.format("Open key: \"%d, %d\"", this.openKey.get(0), this.openKey.get(1)));
            ret.append("\nDocuments:\n");
            for (SignaturedDocument d : this.documents)
                ret.append(d);
            return ret.toString();
        }
    }

    public static class SignaturedDocument {
        private String text;
        private int signature;

        public SignaturedDocument(String text, List<Integer> secretKey) throws UnsupportedEncodingException, NoSuchAlgorithmException {
            setText(text);
            setSignature(text, secretKey);
        }

        private void setText(String text) {
            this.text = text;
        }

        private void setSignature(String text, List<Integer> secretKey) throws UnsupportedEncodingException, NoSuchAlgorithmException {
            this.signature = calculateSignature(text, secretKey);
        }

        private int calculateSignature(String text, List<Integer> secretKey) throws UnsupportedEncodingException, NoSuchAlgorithmException {
            int hashText = AdditionalMath.hashFunction(text);
            int x = secretKey.get(0);
            int p = secretKey.get(1);
            return AdditionalMath.modExp(hashText, x, p);
        }

        public String getText() {
            return text;
        }

        public int getSignature() {
            return signature;
        }

        @Override
        public String toString() {
            return String.format("Text: \"%s\"\nSignature: \"%d\"\n", this.text, Math.abs(this.signature));
        }
    }

    public static final class AdditionalMath {
        public static int modExp(int x, int y, int N) {
            if (y == 0) return 1;
            int z = modExp(x, y / 2, N);
            if (y % 2 == 0)
                return (z * z) % N;
            else
                return (x * z * z) % N;
        }

        public static int hashFunction(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] data1 = text.getBytes(StandardCharsets.UTF_8);
            byte[] digest = messageDigest.digest(data1);
            int ret = 0;
            for (byte b : digest)
                ret += Integer.parseInt(String.valueOf(b));
            return ret;
        }

        public static int reciprocalNum(int num, int mod) {
            int reciprocalNum = 0;
            int i = 2;
            while (reciprocalNum == 0)
                if (Math.floorMod(num * i++, mod) == 1)
                    reciprocalNum = i;
            return reciprocalNum;
        }
    }
}
