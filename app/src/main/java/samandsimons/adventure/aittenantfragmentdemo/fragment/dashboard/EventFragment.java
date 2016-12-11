package samandsimons.adventure.aittenantfragmentdemo.fragment.dashboard;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;

import samandsimons.adventure.aittenantfragmentdemo.R;
import samandsimons.adventure.aittenantfragmentdemo.adapter.recycler.EventRecyclerAdapter;
import samandsimons.adventure.aittenantfragmentdemo.event.Events;
import samandsimons.adventure.aittenantfragmentdemo.fragment.dialog.AddEventDialogFragment;
import samandsimons.adventure.aittenantfragmentdemo.model.Connection;
import samandsimons.adventure.aittenantfragmentdemo.model.Event;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventFragment extends Fragment implements CreateDialogInterface {

    public static final String ADD_EVENT_DIALOG = "ADD_EVENT_DIALOG";
    public static final int ADD_EVENT_REQUEST_CODE = 1;

    private EventRecyclerAdapter recyclerAdapter;
    private LinearLayoutManager layoutManager;

    public EventFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_event, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.eventRecycler);
        recyclerAdapter = new EventRecyclerAdapter(getContext());
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
            case ADD_EVENT_REQUEST_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                    long date = data.getLongExtra(AddEventDialogFragment.EVENT_TIME, 0);
                    String title = data.getStringExtra(AddEventDialogFragment.EVENT_NAME);
                    HashMap<String, Connection> recipients = (HashMap<String, Connection>) data.getSerializableExtra(AddEventDialogFragment.CONNECTION);

                    Event event = new Event(id, email, title, date, recipients);
                    postEvent(event, recipients);

                }
                break;
        }
    }

    @Subscribe
    public void onEvent(Events.EventEvent eventevent) {
        // fuck it
        recyclerAdapter.addItem(eventevent.getEvent());
        layoutManager.scrollToPosition(recyclerAdapter.getItemCount() - 1);

    }

    public void postEvent(Event event, HashMap<String, Connection> recipients) {
        String fromId = event.getFromId();
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("users");

        DatabaseReference newEventRef = usersRef.child(fromId).child("events").push();
        newEventRef.setValue(event);

        for (String c : recipients.keySet()) {
            newEventRef = usersRef.child(c).child("events").push();
            newEventRef.setValue(event);
        }
    }

    @Override
    public void openDialog() {
        AddEventDialogFragment dialogFragment = new AddEventDialogFragment();
        dialogFragment.setTargetFragment(EventFragment.this, ADD_EVENT_REQUEST_CODE);
        dialogFragment.show(getFragmentManager(), ADD_EVENT_DIALOG);
    }
}
