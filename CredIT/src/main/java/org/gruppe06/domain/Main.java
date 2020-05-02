package org.gruppe06.domain;

import org.gruppe06.presentation.App;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        /*LoginSystem loginSystem = new LoginSystem();
        System.out.println(Arrays.toString(loginSystem.hash("qwerty")));
        System.out.println(Arrays.toString(loginSystem.hash("qwerty")));*/
        PasswordAuthentication passwordAuthentication = new PasswordAuthentication();
        String password = "qwerty";
        String token = passwordAuthentication.hash(password.toCharArray());
        System.out.println(passwordAuthentication.authenticate(password.toCharArray(), token));
        App.launch(args);
    }

}
