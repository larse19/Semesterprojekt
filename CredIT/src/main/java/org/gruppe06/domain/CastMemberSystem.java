package org.gruppe06.domain;

import org.gruppe06.interfaces.IActor;
import org.gruppe06.interfaces.ICastMember;
import org.gruppe06.interfaces.IRole;
import org.gruppe06.persistance.Actor;
import org.gruppe06.persistance.CastMemberDataHandler;
import org.gruppe06.persistance.Role;

import java.util.List;

public class CastMemberSystem {

    private CastMemberDataHandler castMemberDataHandler;

    public CastMemberSystem(){
        castMemberDataHandler = new CastMemberDataHandler();
    }

    //Creates an instance of an Actor
    public IActor createActor(String characterName){
        return new Actor(characterName);
    }

    //Creates an instance of a Role
    public IRole createRole(String role){
        return new Role(role);
    }

    //Checks if a cast member exists in the database
    private boolean checkIfCastMemberExists(String ID){
        try {
            ICastMember checkCastMember = castMemberDataHandler.getCastMemberFromID(ID);
            return true;
        }catch (NullPointerException ex){
            return false;
        }

    }

    //Creates new cast member, and adds them to the database
    public boolean createNewCastMember(String ID, String name){
        if(checkIfCastMemberExists(ID)){
            System.out.println("Cast member already exists");
            return false;
        }else{
            castMemberDataHandler.createCastMember(ID,name);
            return true;
        }
    }

    //Deletes cast member from the database
    public boolean deleteCastMember(String ID){
        if(checkIfCastMemberExists(ID)){
            castMemberDataHandler.deleteCastMember(ID);
            return true;
        }else{
            System.out.println("Cast member does not exist");
            return false;
        }
    }

    //Updates the name of a cast member, based on their ID
    public boolean updateCastMember(String ID, String newName){
        if(checkIfCastMemberExists(ID)){
            castMemberDataHandler.updateCastMember(ID, newName);
            return true;
        }else{
            return false;
        }
    }

    //Get a cast member based on their name (With multiple cast members with same name, it returns the first added cast member)
    public ICastMember getCastMember(String name) throws NullPointerException{
        if(castMemberDataHandler.getCastMember(name) != null){
            return castMemberDataHandler.getCastMember(name);
        }else{
            throw new NullPointerException();
        }
    }

    //Returns a list of all cast members in the database
    public List<ICastMember> getAllCastMembers(){
        return castMemberDataHandler.getAllCastMembers();
    }

    //Returns list of all cast members name
    public List<String> getListOfCastMemberNames(){
        return castMemberDataHandler.getAllCastMemberNames();
    }
}
