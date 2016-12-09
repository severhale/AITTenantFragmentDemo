package samandsimons.adventure.aittenantfragmentdemo.model;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Simon on 12/3/2016.
 */

public class User {

    @Exclude
    private static User instance;

    public static User getCurrentUser() {
        if (instance == null) {
            instance = new User();
        }
        return instance;
    }

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

    public List<Connection> getConfirmedConnections(){
        ArrayList<Connection> confirmed = new ArrayList<Connection>();
        for (Connection connection : connections) {
            if (connection.getConnectionType()==Connection.State.CONFIRMED){
                confirmed.add(connection);
            }
        }
        return confirmed;
    }

    public List<Connection> getIncomingConnections(){
        ArrayList<Connection> pending = new ArrayList<Connection>();
        for (Connection connection : connections) {
            if (connection.getConnectionType() == Connection.State.INCOMING){
                pending.add(connection);
            }
        }
        return pending;
    }

    public void setFirebaseUser(FirebaseUser fbUser) {
        setEmail(fbUser.getEmail());
        setUsername(fbUser.getDisplayName());
    }
}
