package com.Agenda;

import java.util.List;

public interface command {
    boolean check(String[] command, List<User> user);

    int exec(String[] command, List<User> user, int count);
}