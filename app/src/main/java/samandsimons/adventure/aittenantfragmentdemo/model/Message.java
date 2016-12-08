package samandsimons.adventure.aittenantfragmentdemo.model;

/**
 * Created by Simon on 12/4/2016.
 */

public class Message {
    private String fromId;
    private String toId;
    private String text;
    private long time;

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

    public String getText() {
        return text;
    }

    public Message() {
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public Message(String fromId, String toId, String text, long time) {

        this.fromId = fromId;
        this.toId = toId;
        this.text = text;
        this.time = time;
    }
}
