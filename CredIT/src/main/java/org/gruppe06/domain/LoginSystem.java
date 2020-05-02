package org.gruppe06.domain;

import org.gruppe06.persistance.LoginDataHandler;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

public class LoginSystem {

    private CredIT credITInstance;
    private LoginDataHandler loginDataHandler;

    public LoginSystem(){
        credITInstance = CredIT.getCredITInstance();
        loginDataHandler = new LoginDataHandler();
    }

    public int login(String username, String password)
    {
        int userRole =  loginDataHandler.login(username, password);

        if(userRole != 0){
            credITInstance.setName(loginDataHandler.getUsersName(username));
            credITInstance.setUsername(username);
        }
        return userRole;
    }

    public byte[] hash(String password){
        byte[] hash = null;

        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);

        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            hash = factory.generateSecret(spec).getEncoded();
            Base64.Encoder enc = Base64.getEncoder();
            System.out.printf("salt: %s%n", enc.encodeToString(salt));
            System.out.printf("hash: %s%n", enc.encodeToString(hash));

        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return hash;
    }

}
