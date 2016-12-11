package samandsimons.adventure.aittenantfragmentdemo.model;

import java.io.Serializable;

/**
 * Created by samgrund on 12/8/16.
 */
public class Connection implements Serializable {

    private String id;
    private String displayName;

    // VERY IMPORTANT: We want our connection to only display the email
    // This way we can use it to populate a spinner
    @Override
    public String toString() {
        return displayName;
    }

    public Connection() {
        id = "";
        displayName = "";
    }

    public Connection(String id, String displayName) {
        this.id = id;
        this.displayName = displayName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
