package org.gruppe06.domain;


public class CredIT {

    private  static CredIT credIT = null;
    private  int userRole;
    private  String username;
    private  String name;

    private CredIT(){

    }

    //Returns the instance of the class (Singleton)
    public static CredIT getCredITInstance(){
        if(credIT == null){
            credIT = new CredIT();
        }
        return credIT;
    }

    //Returns the user role for the user currently logged in
    public  int getUserRole() {
        return userRole;
    }

    //Sets the user role for the user currently logged in
    public void setUserRole(int userRole) {
        this.userRole = userRole;
    }

    //Returns the user name for the user currently logged in
    public  String getUsername() {
        return username;
    }

    //Returns the name for the user currently logged in
    public  String getName() {
        return name;
    }

    //Sets the username for the user currently logged in
    public  void setUsername(String username) {
        this.username = username;
    }

    //Sets the name for the user currently logged in
    public  void setName(String name) {
        this.name = name;
    }
}
