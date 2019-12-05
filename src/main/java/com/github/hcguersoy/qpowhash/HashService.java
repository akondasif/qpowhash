package com.github.hcguersoy.qpowhash;

import javax.enterprise.context.ApplicationScoped;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.logging.Logger;

@ApplicationScoped
class HashService {

    private final Logger log = Logger.getLogger(HashService.class.getName());

    // shameless copied and adapted from https://gist.github.com/CryptoKass
    private String applySha256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString().trim();
        }
        catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String binaryHash(int difficulty, String input ) {
        long nonce = 0;
        String hash;
        char[] chars = new char[difficulty];
        Arrays.fill(chars, '0');
        String target = new String(chars);
        do {
            nonce ++;
            hash = applySha256(input + nonce);
        } while(!hash.substring( 0, difficulty).equals(target));
        log.fine("Found hash with nonce [" + nonce + "]");
        return hash;
    }

    String hash(String input) {
        log.fine("Will hash [" + input + "]");
        return binaryHash(4, input) ;
    }
}
