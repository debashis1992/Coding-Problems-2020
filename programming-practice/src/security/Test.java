package security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class Test {
    public static void main(String[] args) throws NoSuchAlgorithmException {

        //generating a random number
        SecureRandom secureRandom=new SecureRandom();
        byte[] tokenBytes=new byte[32]; //256 bits
        secureRandom.nextBytes(tokenBytes);

        //encoding
        String token = Base64.getUrlEncoder().withoutPadding().encodeToString(tokenBytes);
        String pat = "ghp_" + token;

        System.out.println(pat);

        //creating the token hash
        MessageDigest md=MessageDigest.getInstance("SHA-256");
        byte[] hashedBytes=md.digest(pat.getBytes(StandardCharsets.UTF_8));

        //encode for storage
        String tokenHash = Base64.getUrlEncoder().encodeToString(hashedBytes);

        //finally, this encoded tokenHash is stored in db
    }
}
