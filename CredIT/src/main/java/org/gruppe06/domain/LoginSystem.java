package org.gruppe06.domain;

import org.gruppe06.persistance.LoginDataHandler;

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

}
