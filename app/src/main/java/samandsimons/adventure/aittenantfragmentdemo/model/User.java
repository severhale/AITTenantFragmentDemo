package samandsimons.adventure.aittenantfragmentdemo.model;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.Exclude;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import samandsimons.adventure.aittenantfragmentdemo.event.Events;

/**
 * Created by Simon on 12/3/2016.
 */

public class User {

    @Exclude
    private static User instance;

    public static User getCurrentUser() {
        if (instance == null) {
            instance = new User();
            instance.setConfirmedConnections(new ArrayList<Connection>());
            instance.setPendingConnections(new ArrayList<Connection>());
            instance.setRequestedConnections(new ArrayList<Connection>());
            instance.setMessages(new ArrayList<Message>());
            instance.setPayments(new ArrayList<Payment>());
            instance.setEvents(new ArrayList<Event>());
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

    private List<Message> messages;
    private List<Payment> payments;
    private List<Event> events;
    private List<Connection> confirmedConnections;
    private List<Connection> pendingConnections;
    private List<Connection> requestedConnections;

    public User() {
    }

    public User(String username, String email, UserType type) {

        this.username = username;
        this.email = email;
        this.type = type.ordinal();
        messages = new ArrayList<>();
        payments = new ArrayList<>();
        events = new ArrayList<>();
        confirmedConnections = new ArrayList<>();
        pendingConnections = new ArrayList<>();
        requestedConnections = new ArrayList<>();
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public List<Connection> getConfirmedConnections() {
        return confirmedConnections;
    }

    public void setConfirmedConnections(List<Connection> confirmedConnections) {
        this.confirmedConnections = confirmedConnections;
    }

    public List<Connection> getPendingConnections() {
        return pendingConnections;
    }

    public void setPendingConnections(List<Connection> pendingConnections) {
        this.pendingConnections = pendingConnections;
    }

    public List<Connection> getRequestedConnections() {
        return requestedConnections;
    }

    public void setRequestedConnections(List<Connection> requestedConnections) {
        this.requestedConnections = requestedConnections;
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
        setConfirmedConnections(user.getConfirmedConnections());
        setEmail(user.getEmail());
        setEvents(user.getEvents());
        setMessages(user.getMessages());
        setPayments(user.getPayments());
        setUsername(user.getUsername());
    }

    public void setFirebaseUser(FirebaseUser fbUser) {
        setEmail(fbUser.getEmail());
        setUsername(fbUser.getDisplayName());
    }

    @Subscribe
    public void onEvent(Events.ConfirmedConnectionEvent confirmed) {
        confirmedConnections.add(confirmed.getConfirmed());
    }

    @Subscribe
    public void onEvent(Events.PendingConnectionEvent pending) {
        pendingConnections.add(pending.getPending());
    }

    @Subscribe
    public void onEvent(Events.RequestedConnectionEvent requested) {
        requestedConnections.add(requested.getRequested());
    }

    @Subscribe
    public void onEvent(Events.MessageEvent message) {
        messages.add(message.getMessage());
    }

    @Subscribe
    public void onEvent(Events.PaymentEvent payment) {
        payments.add(payment.getPayment());
    }

    @Subscribe
    public void onEvent(Events.EventEvent event) {
        events.add(event.getEvent());
    }
}
