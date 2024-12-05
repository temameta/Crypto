import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class Sender {
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
            b = (int)Math.pow(p, i);
            a = AdditionalMath.modExp(b, (p-1)/q, p);
            if (a != 1)
                break;
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

