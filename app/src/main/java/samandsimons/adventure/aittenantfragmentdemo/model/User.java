package samandsimons.adventure.aittenantfragmentdemo.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Simon on 12/3/2016.
 */

public class User {
    private String username;
    private String email;

    private List<Message> messages;
    private List<Payment> payments;
    private List<Event> events;
    private List<String> connections;

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

    public List<String> getConnections() {
        return connections;
    }

    public void setConnections(List<String> connections) {
        this.connections = connections;
    }

    public User(String username, String email) {

        this.username = username;
        this.email = email;
        messages = new ArrayList<>();
//        messages.add(new Message("0", "0", "test", 0));
        payments = new ArrayList<>();
//        payments.add(new Payment("0", "0", "55.55", 0));
        events = new ArrayList<>();
//        events.add(new Event("0", "event is happening", 0));
        connections = new ArrayList<>();


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
