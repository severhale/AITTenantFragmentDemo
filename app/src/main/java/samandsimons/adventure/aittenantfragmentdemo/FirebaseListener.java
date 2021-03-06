package samandsimons.adventure.aittenantfragmentdemo;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import samandsimons.adventure.aittenantfragmentdemo.event.Events;
import samandsimons.adventure.aittenantfragmentdemo.model.Connection;
import samandsimons.adventure.aittenantfragmentdemo.model.Event;
import samandsimons.adventure.aittenantfragmentdemo.model.Message;
import samandsimons.adventure.aittenantfragmentdemo.model.Payment;
import samandsimons.adventure.aittenantfragmentdemo.model.User;

/**
 * Created by Simon on 12/10/2016.
 */

public class FirebaseListener {

    static boolean started = false;
    static HashMap<DatabaseReference, ChildEventListener> listeners = new HashMap<>();

    public static void startAllListeners() {
        started = true;
        setupConfirmedListener();
        setupPendingListener();
        setupRequestedListener();
        setupMessageListener();
        setupEventListener();
        setupPaymentListener();
    }

    public static boolean isStarted() {
        return started;
    }

    public static void stopAllListeners() {
        started = false;
        for (Map.Entry<DatabaseReference,ChildEventListener> entry : listeners.entrySet()) {
            entry.getKey().removeEventListener(entry.getValue());
        }
    }

    public static void setupRequestedListener() {
        String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users")
                .child(id).child("connections").child("outgoing");
        ChildEventListener c = ref.
                addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        Connection newConnection = dataSnapshot.getValue(Connection.class);

                        EventBus.getDefault().post(new Events.RequestedConnectionEvent(newConnection));
                    }
                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {
                        Connection connection = dataSnapshot.getValue(Connection.class);

                        EventBus.getDefault().post(new Events.RequestedConnectionRemoved(connection));
                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
        listeners.put(ref, c);
    }

    public static void setupPaymentListener() {
        String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users").child(id).child("payments");
        ChildEventListener c = ref.
                addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        Payment newPayment = dataSnapshot.getValue(Payment.class);
                        newPayment.setKey(dataSnapshot.getKey());

                        EventBus.getDefault().post(new Events.PaymentEvent(newPayment));
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                        Payment confirmedPayment = dataSnapshot.getValue(Payment.class);
                        confirmedPayment.setKey(dataSnapshot.getKey());

                        EventBus.getDefault().post(new Events.PaymentConfirmedEvent(confirmedPayment));
                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {
                        Payment removedPayment = dataSnapshot.getValue(Payment.class);
                        removedPayment.setKey(dataSnapshot.getKey());

                        EventBus.getDefault().post(new Events.PaymentRemovedEvent(removedPayment));
                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
        listeners.put(ref, c);
    }

    public static void setupEventListener() {
        String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users").child(id).child("events");
        ChildEventListener c = ref.
                addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        Event newEvent = dataSnapshot.getValue(Event.class);
                        newEvent.setKey(dataSnapshot.getKey());

                        EventBus.getDefault().post(new Events.EventEvent(newEvent));
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {
                        Event event = dataSnapshot.getValue(Event.class);
                        event.setKey(dataSnapshot.getKey());

                        EventBus.getDefault().post(new Events.EventRemovedEvent(event));
                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
        listeners.put(ref, c);
    }

    public static void setupPendingListener() {
        String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users")
                .child(id).child("connections").child("incoming");
        ChildEventListener c = ref.
                addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        Connection newConnection = dataSnapshot.getValue(Connection.class);

                        EventBus.getDefault().post(new Events.PendingConnectionEvent(newConnection));
                    }
                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {
                        Connection connection = dataSnapshot.getValue(Connection.class);

                        EventBus.getDefault().post(new Events.PendingConnectionRemoved(connection));
                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
        listeners.put(ref, c);
    }

    public static void setupMessageListener() {
        String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users").child(id).child("messages");
        ChildEventListener c = ref.
                addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        Message newMessage = dataSnapshot.getValue(Message.class);

                        EventBus.getDefault().post(new Events.MessageEvent(newMessage));
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                        // should we allow people to modify their posts?
                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                        // shouldn't happen
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
        listeners.put(ref, c);
    }

    public static void setupConfirmedListener() {

        String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users")
                .child(id).child("connections").child("confirmed");
        ChildEventListener c = ref.
                addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        Connection newConnection = dataSnapshot.getValue(Connection.class);

                        EventBus.getDefault().post(new Events.ConfirmedConnectionEvent(newConnection));

                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {
                        Connection connection = dataSnapshot.getValue(Connection.class);

                        EventBus.getDefault().post(new Events.ConfirmedConnectionRemoved(connection));
                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
        listeners.put(ref, c);
    }
}
