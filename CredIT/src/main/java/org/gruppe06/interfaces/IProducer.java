package org.gruppe06.interfaces;

public interface IProducer extends ICast {

    /*
    IProducer extends ICast because a producer is a part of a cast, same goes for cast member is a part of a cast.
    There is a getter and a setter method, and toString that prints the role for a program related to a person.
     */

    String getRole();

    void setRole(String role);

    @Override
    String toString();

}
