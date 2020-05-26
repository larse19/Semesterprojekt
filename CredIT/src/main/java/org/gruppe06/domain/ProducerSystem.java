package org.gruppe06.domain;

import org.gruppe06.interfaces.IProducer;
import org.gruppe06.persistance.ProducerDataHandler;

import java.util.List;


public class ProducerSystem {

    private ProducerDataHandler producerDataHandler;

    public ProducerSystem(){
        producerDataHandler = new ProducerDataHandler();
    }

    //Returns a producer based on their name (With multiple producers with same name, it returns the first added producer)
    public IProducer getProducer(String producerName) throws NullPointerException {
        if(producerDataHandler.getProducer(producerName) != null){
            return producerDataHandler.getProducer(producerName);
        }else{
            throw new NullPointerException();
        }
    }

    //Returns a list of all producers names
    public List<String> getListOfProducerNames(){
        return producerDataHandler.getAllProducerNames();
    }

    //Returns a list of all producers
    public List<IProducer> getAllProducers(){
        return producerDataHandler.getAllProducers();
    }
}
