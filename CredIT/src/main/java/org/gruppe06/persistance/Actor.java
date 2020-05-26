package org.gruppe06.persistance;

import org.gruppe06.interfaces.IActor;

public class Actor extends Role implements IActor {

    private String characterName;

    public Actor(String characterName) {
        super("Actor");
        this.characterName = characterName;
    }

    @Override
    public String toString(){
        return characterName;
    }
}
