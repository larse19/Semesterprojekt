package org.gruppe06.interfaces;

public interface IProgramRole {

    /*
     IProgramRole has getProgramInfo methods that gets the information of a program.
     The toString methods prints the values.
     */

    IProgramInfo getProgramInfo();

    @Override
    String toString();
}
