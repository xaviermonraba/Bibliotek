package model;

public class User {
    private static final int USER_POINTS = 100; // Default points for a user
    private String username;
    private String password;
    private String email;
    private int points;

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.points = USER_POINTS;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
