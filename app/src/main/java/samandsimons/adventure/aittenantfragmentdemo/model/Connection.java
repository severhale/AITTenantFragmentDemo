package samandsimons.adventure.aittenantfragmentdemo.model;

/**
 * Created by samgrund on 12/8/16.
 */
public class Connection {

    private boolean confirmed;
    private String fromId;
    private String toId;
    private String fromDisplayName;
    private String toDisplayName;

    public Connection() {
    }

    public Connection(boolean confirmed, String fromId, String toId, String fromDisplayName, String toDisplayName) {
        this.confirmed = confirmed;
        this.fromId = fromId;
        this.toId = toId;
        this.fromDisplayName = fromDisplayName;
        this.toDisplayName = toDisplayName;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }

    public String getToId() {
        return toId;
    }

    public void setToId(String toId) {
        this.toId = toId;
    }

    public String getFromDisplayName() {
        return fromDisplayName;
    }

    public void setFromDisplayName(String fromDisplayName) {
        this.fromDisplayName = fromDisplayName;
    }

    public String getToDisplayName() {
        return toDisplayName;
    }

    public void setToDisplayName(String toDisplayName) {
        this.toDisplayName = toDisplayName;
    }
}
