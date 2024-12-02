import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class Person {
    private int p = 359;
    private int q = 719;
    private List<SignaturedDocument> documents;
    private List<Integer> openKey;
    private List<Integer> secretKey;

    public Person() {
        calculateKeys(this.p, this.q);
        this.documents = new ArrayList<>();
    }

    public Person(int p, int q) {
        setP(p);
        setQ(q);
        calculateKeys(p, q);
        this.documents = new ArrayList<>();
    }

    private void setP(int p) {
        this.p = p;
    }

    private void setQ(int q) {
        this.q = q;
    }

    private void setOpenKey(List<Integer> openKey) {
        this.openKey = openKey;
    }

    private void setSecretKey(List<Integer> secretKey) {
        this.secretKey = secretKey;
    }

    private void calculateKeys(int p, int q) {
        int N = p * q,
                fN = (p - 1) * (q - 1),
                e = 4,
                d = 5;
        List<Integer> openKey = new ArrayList<>(),
                secretKey = new ArrayList<>();
        for (int i = e; i <= fN; i++) {
            if (AdditionalMath.NOD(i, fN) == 1) {
                e = i;
                break;
            }
        }
        for (int i = d; i < N; i++)
            if (Math.floorMod(e * i, fN) == 1) {
                d = i;
                break;
            }
        openKey.add(e);
        openKey.add(N);
        secretKey.add(d);
        secretKey.add(N);
        setOpenKey(openKey);
        setSecretKey(secretKey);
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

    public boolean signatureVerification(SignaturedDocument document) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String text = document.getText();
        int e = this.openKey.get(0);
        int n = this.openKey.get(1);
        int signature = document.getSignature();
        int restoredHashText = AdditionalMath.modExp(signature, e, n);
        int hashText = AdditionalMath.hashFunction(text);
        return restoredHashText == hashText;
    }

    public boolean signatureVerification(String text, int signature) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        int e = this.openKey.get(0);
        int n = this.openKey.get(1);
        int restoredHashText = Math.floorMod((int) Math.pow(signature, e), n);
        int hashText = AdditionalMath.hashFunction(text);
        return restoredHashText == hashText;
    }

    static public boolean signatureVerification(SignaturedDocument document, List<Integer> openKey) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String text = document.getText();
        int e = openKey.get(0);
        int n = openKey.get(1);
        int signature = document.getSignature();
        int restoredHashText = Math.floorMod((int) Math.pow(signature, e), n);
        int hashText = AdditionalMath.hashFunction(text);
        return restoredHashText == hashText;
    }

    static public boolean signatureVerification(String text, int signature, List<Integer> openKey) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        int e = openKey.get(0);
        int n = openKey.get(1);
        int restoredHashText = Math.floorMod((int) Math.pow(signature, e), n);
        int hashText = AdditionalMath.hashFunction(text);
        return restoredHashText == hashText;
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

