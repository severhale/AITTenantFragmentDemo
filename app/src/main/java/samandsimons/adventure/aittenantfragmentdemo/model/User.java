package samandsimons.adventure.aittenantfragmentdemo.model;

import android.util.Log;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.Exclude;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
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
            instance.initializeData();
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

    @Subscribe
    public void onEvent(Events.ConfirmedConnectionRemoved event) {
        Connection connection = event.getConnection();
        int index = -1;
        for (int i = 0; i < confirmedConnections.size(); i++) {
            if (connection.getId().equals(confirmedConnections.get(i).getId())) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            Log.w("TAG", "ERROR REMOVING CONNECTION");
            return;
        }
        confirmedConnections.remove(index);
    }

    @Subscribe
    public void onEvent(Events.PendingConnectionRemoved event) {
        Connection connection = event.getConnection();
        int index = -1;
        for (int i = 0; i < pendingConnections.size(); i++) {
            if (connection.getId().equals(pendingConnections.get(i).getId())) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            Log.w("TAG", "ERROR REMOVING CONNECTION");
            return;
        }
        pendingConnections.remove(index);
    }

    @Subscribe
    public void onEvent(Events.RequestedConnectionRemoved event) {
        Connection connection = event.getConnection();
        int index = -1;
        for (int i = 0; i < requestedConnections.size(); i++) {
            if (connection.getId().equals(requestedConnections.get(i).getId())) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            Log.w("TAG", "ERROR REMOVING CONNECTION IN SINGLETON");
            return;
        }
        requestedConnections.remove(index);
    }

    public List<Message> getMessagesForUser(String uid) {
        ArrayList<Message> messagesForUser = new ArrayList<Message>();
        for (Message message : messages) {
            if (uid.equals(message.getFromId()) || uid.equals(message.getToId())) {
                messagesForUser.add(message);
            }
        }
        return messagesForUser;
    }

    public List<Payment> getPaymentsForUser(String uid) {
        ArrayList<Payment> paymentsForUser = new ArrayList<Payment>();
        for (Payment payment : payments) {
            if (uid.equals(payment.getFromId()) || uid.equals(payment.getToId())) {
                paymentsForUser.add(payment);
            }
        }
        return paymentsForUser;
    }

    public List<Event> getEventsForUser(String uid) {
        ArrayList<Event> eventsForUser = new ArrayList<Event>();
        for (Event event : events) {
            HashMap<String, Connection> map = event.getEventUsers();
            if (map.containsKey(uid)) {
                eventsForUser.add(event);
            }
        }
        return eventsForUser;
    }

    public void initializeData() {
        setConfirmedConnections(new ArrayList<Connection>());
        setPendingConnections(new ArrayList<Connection>());
        setRequestedConnections(new ArrayList<Connection>());
        setMessages(new ArrayList<Message>());
        setPayments(new ArrayList<Payment>());
        setEvents(new ArrayList<Event>());
    }
}
