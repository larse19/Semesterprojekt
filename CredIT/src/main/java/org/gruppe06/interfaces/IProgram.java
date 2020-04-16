package org.gruppe06.interfaces;

import org.gruppe06.persistance.CastMember;
import org.gruppe06.persistance.Producer;

import java.util.ArrayList;

public interface IProgram {

    String getName();

    ArrayList<ICastMember> getCast();

    ArrayList<IProducer> getProducers();

}
