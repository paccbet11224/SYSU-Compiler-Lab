package com.Agenda;

import java.util.List;

public class Register implements command {
    public boolean check(String[] command, List<User> user) {
        return command.length == 3;
    }

    @Override
    public int exec(String[] command, List<User> user, int count) {
        for (int i = 0; i < user.size(); i++) {
            if (user.getname() == command[1]) {
                System.out.println("There is already a user!");
            }
        }
        throw new UnsupportedOperationException("Unimplemented method 'exec'");
    }
}
