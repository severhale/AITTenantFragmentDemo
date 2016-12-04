package samandsimons.adventure.aittenantfragmentdemo.model;

/**
 * Created by Simon on 12/4/2016.
 */

public class Payment {
    private String fromId;
    private String toId;
    private String amount;
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

    public Payment(String fromId, String toId, String amount, long time) {

        this.fromId = fromId;
        this.toId = toId;
        this.amount = amount;
        this.time = time;
    }
}
