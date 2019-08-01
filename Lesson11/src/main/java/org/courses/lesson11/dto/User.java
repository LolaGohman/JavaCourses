package org.courses.lesson11.dto;

public class User {

    private long id;
    private String username;
    private String password;
    private boolean isAdmin;

    public User(long id, String username, String password, boolean isAdmin) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public User(){

    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean getIsAdmin() {
        return isAdmin;
    }

    public long getId() {
        return id;
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

    public void setIsAdmin(boolean admin) {
        isAdmin = admin;
    }

    @Override
    public String toString() {
        return id + " "+ username + " "+ password + " "+ isAdmin;
    }
}
