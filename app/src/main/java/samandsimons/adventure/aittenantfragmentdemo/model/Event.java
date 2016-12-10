package samandsimons.adventure.aittenantfragmentdemo.model;

/**
 * Created by Simon on 12/4/2016.
 */

public class Event {
    private String fromId;
    private String fromDisplay;
    private long time;
    private String title;

    public Event() {
    }

    public Event(String fromId, String fromDisplay, String title, long time) {
        this.fromDisplay = fromDisplay;
        this.fromId = fromId;
        this.title = title;
        this.time = time;
    }

    public String getFromDisplay() {
        return fromDisplay;
    }

    public void setFromDisplay(String fromDisplay) {
        this.fromDisplay = fromDisplay;
    }

    public String getFromId() {

        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }


}
