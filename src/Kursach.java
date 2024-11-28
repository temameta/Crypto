import java.util.*;

public class Kursach {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String text = "", encryptedText = "";
        encryptedText = encryption(text);
        System.out.println(encryptedText);
    }
    static String encryption(String text) {
        long P = 4251787632253716L,
                Q = 68172685827345L,
                N = P*Q,
                fN = (P-1) * (Q-1),
                e = 2,
                d = ;
        for (long i = e; i <= fN; i++) {
            long eCopy = i,
            fNCopy = fN;
            while(eCopy!=0 && fNCopy!=0){
                if (eCopy>fNCopy) eCopy=eCopy%fNCopy;
                else fNCopy=fNCopy%eCopy;
            }
            if (eCopy + fNCopy == 1) {
                e = i;
                break;
            }
        }

    }
}
