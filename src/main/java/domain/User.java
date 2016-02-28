package domain;

import java.util.ArrayList;

import com.google.gson.annotations.Expose;

public class User {

    @Expose
    private String userName;
    @Expose(serialize = false)
    private String password;
    @Expose
    private ArrayList<String> roles = new ArrayList<>();

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<String> getRoles() {
        return roles;
    }

    public void setRoles(ArrayList<String> roles) {
        this.roles = roles;
    }

}
