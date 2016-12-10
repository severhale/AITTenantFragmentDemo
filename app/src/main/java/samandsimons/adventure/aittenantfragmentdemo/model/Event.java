package samandsimons.adventure.aittenantfragmentdemo.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Simon on 12/4/2016.
 */

public class Event {
    private String toId;
    private String toDisplay;
    private String fromId;
    private String fromDisplay;
    private long time;
    private String title;

    private List<Connection> eventUsers;

    public Event() {
    }

    public Event(String toId, String toDisplay, String fromId, String fromDisplay, String title, long time) {
        this.fromDisplay = fromDisplay;
        this.fromId = fromId;
        this.toId = toId;
        this.toDisplay = toDisplay;
        this.title = title;
        this.time = time;

        this.eventUsers = new ArrayList<Connection>();
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

    public String getToId() {
        return toId;
    }

    public void setToId(String toId) {
        this.toId = toId;
    }

    public String getToDisplay() {
        return toDisplay;
    }

    public void setToDisplay(String toDisplay) {
        this.toDisplay = toDisplay;
    }

    public List<Connection> getEventUsers() {
        return eventUsers;
    }

    public void setEventUsers(List<Connection> eventUsers) {
        this.eventUsers = eventUsers;
    }

    public void addEventUser(Connection user) {
        eventUsers.add(user);
    }
}
