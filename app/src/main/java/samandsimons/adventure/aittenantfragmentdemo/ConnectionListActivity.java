package samandsimons.adventure.aittenantfragmentdemo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import samandsimons.adventure.aittenantfragmentdemo.adapter.pager.ConnectionsPagerAdapter;
import samandsimons.adventure.aittenantfragmentdemo.fragment.connections.RequestedConnectionsFragment;
import samandsimons.adventure.aittenantfragmentdemo.fragment.dialog.AddConnectionDialogFragment;
import samandsimons.adventure.aittenantfragmentdemo.fragment.connections.ConfirmedConnectionsFragment;
import samandsimons.adventure.aittenantfragmentdemo.fragment.connections.PendingConnectionsFragment;
import samandsimons.adventure.aittenantfragmentdemo.model.Connection;
import samandsimons.adventure.aittenantfragmentdemo.model.User;

/**
 * Created by samgrund on 12/9/16.
 */
public class ConnectionListActivity extends BaseActivity implements ConnectionListInterface {

    public static final String CONNECTION_LIST_DIALOG = "CONNECTION_LIST_DIALOG";

    private ViewPager connectionsPager;
    private ConnectionsPagerAdapter connectionsPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection_list);

        setupFirebase();

        connectionsPager = (ViewPager) findViewById(R.id.connectionsPager);

        connectionsPagerAdapter = new ConnectionsPagerAdapter(getSupportFragmentManager(), getApplicationContext());

        connectionsPager.setOffscreenPageLimit(3);
        connectionsPager.setAdapter(connectionsPagerAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabNewConnection);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddConnectionDialogFragment dialogFragment = new AddConnectionDialogFragment();
                dialogFragment.show(getSupportFragmentManager().beginTransaction(), CONNECTION_LIST_DIALOG);
            }
        });

    }

    private void setupFirebase() {
        String id = FirebaseAuth.getInstance().getCurrentUser().getUid();

        setupIncomingListener(id);
        setupOutgoingListener(id);
    }

    private void setupOutgoingListener(String id) {
        FirebaseDatabase.getInstance().getReference().child("users")
                .child(id).child("connections").child("outgoing").
                addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        Connection newConnection = dataSnapshot.getValue(Connection.class);
                        RequestedConnectionsFragment requestedFragment = (RequestedConnectionsFragment) findFragmentByTag(2);
                        requestedFragment.addConnection(newConnection);

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

    private void setupIncomingListener(String id) {
        FirebaseDatabase.getInstance().getReference().child("users")
                .child(id).child("connections").child("incoming").
                addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        Connection newConnection = dataSnapshot.getValue(Connection.class);
                        PendingConnectionsFragment pendingFragment = (PendingConnectionsFragment) findFragmentByTag(1);
                        pendingFragment.addConnection(newConnection);
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

    private Fragment findFragmentByTag(int page) {
        return getSupportFragmentManager().findFragmentByTag(
                "android:switcher:" + R.id.connectionsPager + ":" + connectionsPagerAdapter.getItemId(page)
        );
    }

    @Override
    public void addNewConnection(Connection out, Connection in) {
        DatabaseReference usersReference = FirebaseDatabase.getInstance().getReference().child("users");
        DatabaseReference postToCurrentUser = usersReference.child(in.getId()).child("connections").child("outgoing").child(out.getId());
        postToCurrentUser.setValue(out);

        DatabaseReference postToOtherUser = usersReference.child(out.getId()).child("connections").child("incoming").child(in.getId());
        postToOtherUser.setValue(in);
    }
}