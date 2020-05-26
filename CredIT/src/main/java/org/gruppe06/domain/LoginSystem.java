package org.gruppe06.domain;

import org.gruppe06.persistance.LoginDataHandler;
import org.gruppe06.persistance.SystemAdministratorDataHandler;

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

    //Attempts to login the user, and returns their user role. Returns 0 if login failed
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
