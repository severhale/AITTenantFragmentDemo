package samandsimons.adventure.aittenantfragmentdemo.model;

/**
 * Created by samgrund on 12/8/16.
 */
public class Connection {

    private boolean confirmed;
    private String id;
    private String displayName;

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
