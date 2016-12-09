package samandsimons.adventure.aittenantfragmentdemo.model;

import java.io.Serializable;

/**
 * Created by samgrund on 12/8/16.
 */
public class Connection implements Serializable {

    enum State {
        INCOMING,
        OUTGOING,
        CONFIRMED
    }

    private boolean confirmed;
    private String id;
    private String displayName;
    private int state;

    // VERY IMPORTANT: We want our connection to only display the email
    // This way we can use it to populate a spinner
    @Override
    public String toString() {
        return displayName;
    }

    public State getConnectionType() {
        return State.values()[state];
    }

    public void setConnectionType(State type) {
        state = type.ordinal();
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Connection() {
    }

    public Connection(boolean confirmed, String id, String displayName) {
        this.confirmed = confirmed;
        this.id = id;
        this.displayName = displayName;
    }

    public boolean isConfirmed() {

        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
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
