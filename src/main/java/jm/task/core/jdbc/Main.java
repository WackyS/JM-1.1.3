package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Alesha", "T", (byte) 42);
        userService.saveUser("Ivan", "Dobro", (byte) 31);
        userService.saveUser("Lev", "Lewa", (byte) 33);
        userService.saveUser("Mikitko", "K", (byte) 35);
        userService.getAllUsers().stream().map(User::toString).forEach(System.out::println);
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
