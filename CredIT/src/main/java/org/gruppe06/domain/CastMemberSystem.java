package org.gruppe06.domain;

import jdk.jshell.spi.ExecutionControl;
import org.gruppe06.interfaces.ICastMember;
import org.gruppe06.interfaces.IRole;
import org.gruppe06.persistance.CastMemberDataHandler;

public class CastMemberSystem {

    private CastMemberDataHandler castMemberDataHandler;

    public CastMemberSystem(){
        castMemberDataHandler = new CastMemberDataHandler();
    }

    private boolean checkIfCastMemberExists(String ID){
        ICastMember checkCastMember = castMemberDataHandler.getCastMemberFromID(ID);
        return checkCastMember != null;
    }

    public boolean createNewCastMember(String ID, String name){
        if(checkIfCastMemberExists(ID)){
            System.out.println("Cast member already exists");
            return false;
        }else{
            castMemberDataHandler.createCastMember(ID,name);
            return true;
        }
    }

    public boolean deleteCastMember(String ID){
        if(checkIfCastMemberExists(ID)){
            castMemberDataHandler.deleteCastMember(ID);
            return true;
        }else{
            System.out.println("Cast member does not exist");
            return false;
        }
    }

    public boolean updateCastMember(String ID, String newName){
        if(checkIfCastMemberExists(ID)){
            castMemberDataHandler.updateCastMember(ID, newName);
            return true;
        }else{
            return false;
        }
    }

    public ICastMember getCastMember(String name){
        return castMemberDataHandler.getCastMember(name);
    }

}
