package samandsimons.adventure.aittenantfragmentdemo.fragment.dashboard;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import samandsimons.adventure.aittenantfragmentdemo.R;
import samandsimons.adventure.aittenantfragmentdemo.adapter.recycler.MessageRecyclerAdapter;
import samandsimons.adventure.aittenantfragmentdemo.event.Events;
import samandsimons.adventure.aittenantfragmentdemo.fragment.dialog.AddMessageDialogFragment;
import samandsimons.adventure.aittenantfragmentdemo.model.Connection;
import samandsimons.adventure.aittenantfragmentdemo.model.Message;
import samandsimons.adventure.aittenantfragmentdemo.model.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class MessageFragment extends Fragment implements CreateDialogInterface {
    public static final int MESSAGE_REQUEST = -1;
    public static final String MESSAGE_DIALOG = "MESSAGE_DIALOG";

    private MessageRecyclerAdapter recyclerAdapter;
    private LinearLayoutManager layoutManager;

    public MessageFragment() {

        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        EventBus.getDefault().register(this);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.messageRecycler);
        recyclerAdapter = new MessageRecyclerAdapter();
        recyclerView.setAdapter(recyclerAdapter);
        layoutManager = new LinearLayoutManager(getContext());
//        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case MESSAGE_REQUEST:
                if (resultCode == Activity.RESULT_OK) {
                    String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    String displayName = FirebaseAuth.getInstance().getCurrentUser().getEmail();
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
        DatabaseReference usersReference = FirebaseDatabase.getInstance().getReference().child("users");
        DatabaseReference postToCurrentUser = usersReference.child(newMessage.getFromId()).child("messages").push();
        postToCurrentUser.setValue(newMessage);

        DatabaseReference postToOtherUser = usersReference.child(newMessage.getToId()).child("messages").push();
        postToOtherUser.setValue(newMessage);
    }

    @Subscribe
    public void onEvent(Events.MessageEvent event) {
        recyclerAdapter.addItem(event.getMessage());
        layoutManager.scrollToPosition(recyclerAdapter.getItemCount() - 1);
    }

    @Override
    public void openDialog() {
        AddMessageDialogFragment dialogFragment = new AddMessageDialogFragment();
        dialogFragment.setTargetFragment(MessageFragment.this, MESSAGE_REQUEST);
        dialogFragment.show(getFragmentManager().beginTransaction(), MESSAGE_DIALOG);
    }
}
