package org.gruppe06.domain;

import org.gruppe06.interfaces.IPerson;
import org.gruppe06.persistance.ProducerDataHandler;
import org.gruppe06.persistance.SystemAdministratorDataHandler;

import java.util.List;
import java.util.Random;

public class UserSystem {

    private SystemAdministratorDataHandler systemAdministratorDataHandler;
    private ProducerDataHandler producerDataHandler;
    private PasswordAuthentication passwordAuthentication;

    public UserSystem(){
        passwordAuthentication = new PasswordAuthentication();
        systemAdministratorDataHandler = new SystemAdministratorDataHandler();
        producerDataHandler = new ProducerDataHandler();
    }

    public String createSystemAdministrator(String name, String password){
        String username = checkedUsername(name);
        if(systemAdministratorDataHandler.createSystemAdministrator(username, name, password)){
            return username;
        }
        username = "";
        return username;
    }

    public String createProducer(String name, String password){
        String username = checkedUsername(name);
        if(producerDataHandler.createProducer(username, name, password)){
            return username;
        }
        username = "";
        return username;
    }

    public String createUsername(String name){
        StringBuilder username = new StringBuilder();
        String[] nameArray = name.split(" ");

        String[] firstName = nameArray[0].split("");
        for (String letter : firstName) {
            username.append(letter);
            if (username.length() == 3) {
                break;
            }
        }

        String[] lastName = nameArray[nameArray.length - 1].split("");
        int i = 0;
        while (username.length() < 7) {
            if (lastName.length >= i + 1) {
                username.append(lastName[i++]);
            } else {
                break;
            }
        }
        Random r = new Random();
        while (username.length() < 11) {
            username.append(r.nextInt(9));
        }
        return username.toString();
    }

    private String checkedUsername(String name){
        String username;
        name = name.toLowerCase();
        do {
            username = createUsername(name);
        }
        while(!systemAdministratorDataHandler.checkIfUsernameIsAvailable(username));

        return username;

    }

    public List<IPerson> getListOfUsers(String searchString){
        return systemAdministratorDataHandler.getListOfUsers(searchString);
    }

    public boolean deleteUser(String username){
        if(systemAdministratorDataHandler.getUserRole(username) == 1){
            return systemAdministratorDataHandler.deleteSystemAdministrator(username);
        }else if (systemAdministratorDataHandler.getUserRole(username) == 2){
            return producerDataHandler.deleteProducer(username);
        }
        return false;
    }

    public boolean updateUserRole(String username, int role){
        return systemAdministratorDataHandler.updateUserRole(username, role);
    }

    public boolean updateUsersName(String username, String newName){
        return systemAdministratorDataHandler.updateUsersName(username, newName);
    }

    public boolean updatePassword(String username, String password){
        return systemAdministratorDataHandler.updatePassword(username, password);
    }

}
