package land.majazi.latifiarchitecure.utility;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ShaUtils {


    private static byte[] digest(byte[] input, String algorithm) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(e);
        }
        byte[] result = md.digest(input);
        return result;
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }



    public static String hashString(String s, String algorithm) {
//        String algorithm = "SHA-512";
        byte[] shaInBytes = ShaUtils.digest(s.getBytes(StandardCharsets.UTF_8), algorithm);
        return bytesToHex(shaInBytes);
    }



}
