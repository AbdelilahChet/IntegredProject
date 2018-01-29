package constants;

import java.security.MessageDigest;
import java.util.logging.Logger;

public class Encryption {
    private static MessageDigest md;
    private static final Logger LOGGER = Logger.getLogger(Encryption.class.getName());
    /**
     * https://www.mkyong.com/java/java-md5-hashing-example/
     * @param password take the password
     * @return the password Encrypted
     */
    public String encrypt(String password) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte byteData[] = md.digest();
        //convert the byte to hex format method 1
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }
        LOGGER.info("Digest(in hex format):: " + sb.toString());
        //convert the byte to hex format method 2
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
            String hex = Integer.toHexString(0xff & byteData[i]);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        LOGGER.info("Digest(in hex format):: " + hexString.toString());
        return hexString.toString();
    }

    public boolean CompareWithEncryptedPassword(String candidatePassword, String encryptedPassword)throws Exception{
         return encrypt(candidatePassword).equals(encryptedPassword);
    }
}

