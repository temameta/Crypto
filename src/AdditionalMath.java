import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public final class AdditionalMath {
    public static int modExp(int x, int y, int N) {
        if (y == 0) return 1;
        int z = modExp(x, y / 2, N);
        if (y % 2 == 0)
            return (z * z) % N;
        else
            return (x * z * z) % N;
    }

    public static int NOD(int x, int y) {
        while (y != 0) {
            int tmp = x % y;
            x = y;
            y = tmp;
        }
        return x;
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
}
