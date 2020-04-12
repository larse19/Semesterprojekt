package org.gruppe06.domain;

public class Actor extends Role{

    private String characterName;

    public Actor(String characterName) {
        super("Actor");
        this.characterName = characterName;
    }

    public String getCharacterName() {
        return characterName;
    }

    @Override
    public String getRole(){
        return characterName;
    }
}
