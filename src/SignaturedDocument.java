import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class SignaturedDocument {
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
        return String.format("Text: \"%s\"\nSignature: \"%d\"\n", this.text, this.signature);
    }

}

