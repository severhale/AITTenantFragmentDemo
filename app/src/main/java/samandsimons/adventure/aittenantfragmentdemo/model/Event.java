package samandsimons.adventure.aittenantfragmentdemo.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Simon on 12/4/2016.
 */

public class Event {
    private String fromId;
    private String fromDisplay;
    private long time;
    private String title;

    private HashMap<String, Connection> eventUsers;

    public Event() {
        eventUsers = new HashMap<>();
    }

    public Event(String fromId, String fromDisplay, String title, long time) {
        this.fromDisplay = fromDisplay;
        this.fromId = fromId;
        this.title = title;
        this.time = time;

        this.eventUsers = new HashMap<String, Connection>();
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

    public HashMap<String, Connection> getEventUsers() {
        return eventUsers;
    }

    public void setEventUsers(HashMap<String, Connection> eventUsers) {
        this.eventUsers = eventUsers;
    }

    public void addEventUser(Connection user) {
        eventUsers.put(user.getId(), user);
    }
}
