package org.gruppe06.interfaces;

public interface IRole {

     //Has a getter and a setter to get a role for a person. The toString prints the role.
     String getRole();

     void setRole(String role);

     @Override
     String toString();

}
