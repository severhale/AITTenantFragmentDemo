package samandsimons.adventure.aittenantfragmentdemo.model;

/**
 * Created by Simon on 12/3/2016.
 */

public class User {
    private String username;
    private String email;

    public User() {
    }

    public User(String username, String email) {

        this.username = username;
        this.email = email;
    }

    public String getUsername() {

        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
