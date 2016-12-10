package samandsimons.adventure.aittenantfragmentdemo;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.greenrobot.eventbus.EventBus;

import samandsimons.adventure.aittenantfragmentdemo.event.Events;
import samandsimons.adventure.aittenantfragmentdemo.model.Connection;
import samandsimons.adventure.aittenantfragmentdemo.model.Event;
import samandsimons.adventure.aittenantfragmentdemo.model.Message;
import samandsimons.adventure.aittenantfragmentdemo.model.User;

/**
 * Created by Simon on 12/10/2016.
 */

public class FirebaseListener {
    public static void startAllListeners() {
        setupConfirmedListener();
    }

    public static void setupMessageListener() {
        String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference usersReference = FirebaseDatabase.getInstance().getReference().child("users");
        Log.d("TAG", "STARTING LISTENING FOR NEW MESSAGES");
        usersReference.child(id).child("messages").
                addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        Log.d("TAG", "ADDING MESSAGE");
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
    }

    public static void setupConfirmedListener() {

        String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase.getInstance().getReference().child("users")
                .child(id).child("connections").child("confirmed").
                addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        Connection newConnection = dataSnapshot.getValue(Connection.class);
                        User.getCurrentUser().getConnections().add(newConnection);

                        EventBus.getDefault().post(new Events.ConfirmedConnectionEvent(newConnection));

                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {
                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }
}
