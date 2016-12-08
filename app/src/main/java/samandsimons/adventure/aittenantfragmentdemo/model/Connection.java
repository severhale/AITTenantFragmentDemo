package samandsimons.adventure.aittenantfragmentdemo.model;

/**
 * Created by samgrund on 12/8/16.
 */
public class Connection {

    private boolean confirmed;
    private String fromId;
    private String toId;

    public Connection() {
    }

    public Connection(boolean confirmed, String fromId, String toId) {
        this.confirmed = confirmed;
        this.fromId = fromId;
        this.toId = toId;
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
}
