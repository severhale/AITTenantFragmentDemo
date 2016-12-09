package samandsimons.adventure.aittenantfragmentdemo.model;

import java.io.Serializable;

/**
 * Created by samgrund on 12/8/16.
 */
public class Connection implements Serializable {

    public enum State {
        INCOMING,
        OUTGOING,
        CONFIRMED
    }

    private String id;
    private String displayName;
    private State state;

    // VERY IMPORTANT: We want our connection to only display the email
    // This way we can use it to populate a spinner
    @Override
    public String toString() {
        return displayName;
    }

    public State getConnectionType() {
        return state;
    }

    public void setConnectionType(State type) {
        state = type;
    }

    public Connection() {
    }

    public Connection(String id, String displayName, State state) {
        this.id = id;
        this.displayName = displayName;
        this.state = state;
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
