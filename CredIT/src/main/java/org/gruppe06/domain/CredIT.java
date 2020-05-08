package org.gruppe06.domain;


public class CredIT {

    private  static CredIT credIT = null;
    private  int userRole;
    private  String username;
    private  String name;

    private CredIT(){

    }

    public static CredIT getCredITInstance(){
        if(credIT == null){
            credIT = new CredIT();
        }
        return credIT;
    }

    public  int getUserRole() {
        return userRole;
    }

    public void setUserRole(int userRole) {
        this.userRole = userRole;
    }

    public  String getUsername() {
        return username;
    }

    public  String getName() {
        return name;
    }

    public  void setUsername(String username) {
        this.username = username;
    }

    public  void setName(String name) {
        this.name = name;
    }
}
