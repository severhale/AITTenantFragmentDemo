package samandsimons.adventure.aittenantfragmentdemo;

import com.google.firebase.auth.FirebaseAuth;

import samandsimons.adventure.aittenantfragmentdemo.model.User;

/**
 * Created by Simon on 12/3/2016.
 */

public class BaseActivity extends ProgressActivity {
    private User user;

    public String getUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    public String getUserName() {
        return FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
    }

    public User.UserType getUserType() {
        return User.UserType.values()[getUser().getType()];
    }

    public User getUser() {
        if (user == null) {
            user = new User(getUserName(), getUserEmail(), User.UserType.LANDLORD);
        }
        return user;
    }

    public void setUser(User newUser) {
        if (user == null) {
            user = newUser;
        }
        else {
            user.setValues(newUser);
        }
    }

    public String getUserEmail() {
        return FirebaseAuth.getInstance().getCurrentUser().getEmail();
    }
}