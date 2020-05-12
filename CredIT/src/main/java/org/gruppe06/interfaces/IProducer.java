package org.gruppe06.interfaces;

public interface IProducer extends ICast {

    String getRole();

    void setRole(String role);

    @Override
    String toString();

}
