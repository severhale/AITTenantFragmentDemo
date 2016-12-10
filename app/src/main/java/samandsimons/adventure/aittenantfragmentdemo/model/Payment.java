package samandsimons.adventure.aittenantfragmentdemo.model;

import com.google.firebase.database.Exclude;

/**
 * Created by Simon on 12/4/2016.
 */

public class Payment {

    public enum states {OUTGOING, INCOMING}

    @Exclude
    private String key;

    private String fromId;
    private String toId;
    private String fromDisplay;
    private String toDisplay;
    private String amount;
    private long time;
    private String message;
    private int state;
    private boolean confirmed;

    public Payment() {
    }

    public Payment(String fromId, String toId, String fromDisplay, String toDisplay, String amount, long time, String message, int state) {

        this.fromId = fromId;
        this.toId = toId;
        this.fromDisplay = fromDisplay;
        this.toDisplay = toDisplay;
        this.amount = amount;
        this.time = time;
        this.message = message;

        this.state = state;
        this.confirmed = false;
    }

    public String getFromDisplay() {
        return fromDisplay;
    }

    public void setFromDisplay(String fromDisplay) {
        this.fromDisplay = fromDisplay;
    }

    public String getToDisplay() {
        return toDisplay;
    }

    public void setToDisplay(String toDisplay) {
        this.toDisplay = toDisplay;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
