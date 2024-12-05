import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class Reciever {
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

