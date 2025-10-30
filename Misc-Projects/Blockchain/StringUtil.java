import java.security.MessageDigest;
/**
 * To generate a hash, a cryptographic algorithm comes into play.
 * SHA-256 (Secure Hash Algorithm 256) provides a "hash function" to create these unique identifiers from data.
 *
 */
public class StringUtil {
    public static String applySHA256(String input) {

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");    //This applies the algorithm
            byte [] hash = md.digest(input.getBytes("UTF-8"));
            StringBuffer sb = new StringBuffer();       //This contains the hash written in hexadecimal
            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1) sb.append('0');
                sb.append(hex);
            }
            return sb.toString(); //Returns the generated identifier as a string

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
