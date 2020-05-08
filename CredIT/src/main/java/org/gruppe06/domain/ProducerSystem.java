package org.gruppe06.domain;

import org.gruppe06.interfaces.IProducer;
import org.gruppe06.persistance.ProducerDataHandler;
import org.gruppe06.persistance.ProgramDataHandler;


public class ProducerSystem {

    private ProducerDataHandler producerDataHandler;

    public ProducerSystem(){
        producerDataHandler = new ProducerDataHandler();
    }

    public IProducer getProducer(String producerName) throws NullPointerException {
        IProducer producer;
        try {
            producer = producerDataHandler.getProducer(producerName);
        }catch (NullPointerException e){
            throw new NullPointerException();
        }
        return producer;
    }
}
