package samandsimons.adventure.aittenantfragmentdemo.model;

/**
 * Created by Simon on 12/4/2016.
 */

public class Event {
    private String fromId;
    private String text;
    private long time;

    public Event() {
    }

    public String getFromId() {

        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }

    public String getText() {
        return text;
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

    public Event(String fromId, String text, long time) {

        this.fromId = fromId;
        this.text = text;
        this.time = time;
    }
}
