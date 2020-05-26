package org.gruppe06.interfaces;

public interface IProgramInfo {

    /*
     IProgramInfo has a getter for program ID. program name and release year of a program. The toString methods prints
     the values.
     */

    int getID();

    String getName();

    String getYear();

    @Override
    String toString();

}
