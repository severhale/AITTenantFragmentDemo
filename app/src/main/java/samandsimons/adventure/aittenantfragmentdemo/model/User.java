package samandsimons.adventure.aittenantfragmentdemo.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Simon on 12/3/2016.
 */

public class User {

    public enum UserType {
        LANDLORD,
        TENANT
    }

    private String username;
    private String email;
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    private List<Message> messages;
    private List<Payment> payments;
    private List<Event> events;
    private List<Connection> connections;

    public User() {
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public List<Connection> getConnections() {
        return connections;
    }

    public void setConnections(List<Connection> connections) {
        this.connections = connections;
    }

    public User(String username, String email, UserType type) {

        this.username = username;
        this.email = email;
        this.type = type.ordinal();
        messages = new ArrayList<>();
        payments = new ArrayList<>();
        events = new ArrayList<>();
        connections = new ArrayList<>();
        messages.add(new Message("0", "0", "subject", "body", 0));
        payments.add(new Payment("0", "0", "55.55", 0));
        events.add(new Event("0", "event is happening", 0));


    }

    public String getUsername() {

        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setValues(User user) {
        setConnections(user.getConnections());
        setEmail(user.getEmail());
        setEvents(user.getEvents());
        setMessages(user.getMessages());
        setPayments(user.getPayments());
        setUsername(user.getUsername());
    }
}
