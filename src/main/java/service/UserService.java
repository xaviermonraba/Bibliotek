package service;

import model.User;

import java.util.ArrayList;
import java.util.List;

public class UserService {
    private final List<User> users;

    public UserService() {
        this.users = new ArrayList<User>();
    }

    public UserService(List<User> users) {
        this.users = users;
    }

    public User getUserById(String userName) {
        if(this.users == null) {
            throw new IllegalStateException("User list is not initialized.");
        }

        return users.stream()
                .filter(user -> user.getUsername().equals(userName))
                .findFirst()
                .orElse(null);
    }

    public void addUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        this.users.add(user);
    }

}
