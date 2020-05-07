package org.gruppe06.domain;

import org.gruppe06.persistance.LoginDataHandler;
import org.gruppe06.persistance.SystemAdministratorDataHandler;

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
    private SystemAdministratorDataHandler systemAdministratorDataHandler;
    private PasswordAuthentication passwordAuthentication;

    public LoginSystem(){
        credITInstance = CredIT.getCredITInstance();
        systemAdministratorDataHandler = new SystemAdministratorDataHandler();
        passwordAuthentication = new PasswordAuthentication();
        loginDataHandler = new LoginDataHandler();
    }

    public int login(String username, String password)
    {
        String token = loginDataHandler.getLogin(username);
        if(!token.equals("")){
            if(passwordAuthentication.authenticate(password.toCharArray(),token)){
                int userRole = systemAdministratorDataHandler.getUserRole(username);
                if(userRole != 0){
                    credITInstance.setName(loginDataHandler.getUsersName(username));
                    credITInstance.setUsername(username);
                }
                return userRole;
            }
        }
        return 0;
    }

}
