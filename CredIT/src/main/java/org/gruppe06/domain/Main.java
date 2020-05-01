package org.gruppe06.domain;

import org.gruppe06.presentation.App;

public class Main {

    public static void main(String[] args) {
        LoginSystem loginSystem = new LoginSystem();
        System.out.println(loginSystem.login("admin", "admin"));
        App.launch(args);
    }

}
