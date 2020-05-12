package org.gruppe06.domain;

import org.gruppe06.interfaces.IProducer;
import org.gruppe06.persistance.ProducerDataHandler;
import org.gruppe06.persistance.ProgramDataHandler;

import java.util.List;


public class ProducerSystem {

    private ProducerDataHandler producerDataHandler;

    public ProducerSystem(){
        producerDataHandler = new ProducerDataHandler();
    }

    public IProducer getProducer(String producerName) throws NullPointerException {
        if(producerDataHandler.getProducer(producerName) != null){
            return producerDataHandler.getProducer(producerName);
        }else{
            throw new NullPointerException();
        }
    }

    public List<String> getListOfProducers(){
        return producerDataHandler.getAllProducerNames();
    }
}
