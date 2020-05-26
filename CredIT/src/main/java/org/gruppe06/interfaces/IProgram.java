package org.gruppe06.interfaces;

import java.util.ArrayList;

public interface IProgram {

    /*
     IProgram has the following methods, getter for the program name, ArrayList of cast (cast members and producers
     from the program
     */

    String getName();

    ArrayList<ICastMember> getCast();

    ArrayList<IProducer> getProducers();

    String getYear();

}
