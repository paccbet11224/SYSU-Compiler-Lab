package Agenda;

import Agenda.model.User;
import java.util.*;

public class UserManager {
    private final Map<String, User> users = new HashMap<>();

    public boolean register(String username, String password) {
        if (users.containsKey(username))
            return false;
        users.put(username, new User(username, password));
        return true;
    }

    public boolean authenticate(String username, String password) {
        return users.containsKey(username) && users.get(username).getPassword().equals(password);
    }

    public User getUser(String username) {
        return users.get(username);
    }

    public boolean userExists(String username) {
        return users.containsKey(username);
    }
}