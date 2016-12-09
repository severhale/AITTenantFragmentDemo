package samandsimons.adventure.aittenantfragmentdemo.fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import samandsimons.adventure.aittenantfragmentdemo.R;
import samandsimons.adventure.aittenantfragmentdemo.adapter.recycler.MessageRecyclerAdapter;
import samandsimons.adventure.aittenantfragmentdemo.fragment.dialog.AddMessageDialogFragment;
import samandsimons.adventure.aittenantfragmentdemo.model.Connection;
import samandsimons.adventure.aittenantfragmentdemo.model.Message;
import samandsimons.adventure.aittenantfragmentdemo.model.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class MessageFragment extends Fragment implements DataFragment {
    public static final int MESSAGE_REQUEST = -1;
    public static final String MESSAGE_DIALOG = "MESSAGE_DIALOG";

    private MessageRecyclerAdapter recyclerAdapter;
    private String id;
    private String displayName;
    private DatabaseReference usersReference;

    public MessageFragment() {
        id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        displayName = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        usersReference = FirebaseDatabase.getInstance().getReference().child("users");
        usersReference.child(id).child("messages").
                addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        Message newMessage = dataSnapshot.getValue(Message.class);
                        recyclerAdapter.addItem(newMessage);
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
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.messageRecycler);
        recyclerAdapter = new MessageRecyclerAdapter();
        recyclerView.setAdapter(recyclerAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fabNewMessage);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddMessageDialogFragment dialogFragment = new AddMessageDialogFragment();
                dialogFragment.setTargetFragment(MessageFragment.this, MESSAGE_REQUEST);
                dialogFragment.show(getFragmentManager().beginTransaction(), MESSAGE_DIALOG);
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case MESSAGE_REQUEST:
                if (resultCode == Activity.RESULT_OK) {
                    Connection selectedConnection = (Connection) data.getSerializableExtra(AddMessageDialogFragment.CONNECTION);
                    String subject = data.getStringExtra(AddMessageDialogFragment.SUBJECT);
                    String body = data.getStringExtra(AddMessageDialogFragment.BODY);
                    Message newMessage = new Message(id, selectedConnection.getId(), displayName, selectedConnection.getDisplayName(), subject, body, System.currentTimeMillis());
                    postMessage(newMessage);
                }
                else if (resultCode == Activity.RESULT_CANCELED) {
                    // probably don't do anything
                }
                break;
        }
    }

    public void postMessage(Message newMessage) {
        DatabaseReference postToCurrentUser = usersReference.child(newMessage.getFromId()).child("messages").push();
        postToCurrentUser.setValue(newMessage);

        DatabaseReference postToOtherUser = usersReference.child(newMessage.getToId()).child("messages").push();
        postToOtherUser.setValue(newMessage);
    }

    @Override
    public void refreshData(User user) {
        recyclerAdapter.updateForUser(user);
    }
}
