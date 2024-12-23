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

