package samandsimons.adventure.aittenantfragmentdemo.model;

import com.google.firebase.database.Exclude;

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

    @Exclude
    private String key;

    public Event() {
        eventUsers = new HashMap<>();
    }

    public Event(String fromId, String fromDisplay, String title, long time, HashMap<String, Connection> eventUsers) {
        this.fromDisplay = fromDisplay;
        this.fromId = fromId;
        this.title = title;
        this.time = time;

        this.eventUsers = eventUsers;
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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void addEventUser(Connection user) {
        eventUsers.put(user.getId(), user);
    }

}
