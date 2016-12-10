package samandsimons.adventure.aittenantfragmentdemo.fragment.dashboard;


import android.os.Bundle;
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
import com.google.firebase.database.FirebaseDatabase;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import samandsimons.adventure.aittenantfragmentdemo.R;
import samandsimons.adventure.aittenantfragmentdemo.adapter.recycler.EventRecyclerAdapter;
import samandsimons.adventure.aittenantfragmentdemo.event.Events;
import samandsimons.adventure.aittenantfragmentdemo.model.Event;
import samandsimons.adventure.aittenantfragmentdemo.model.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventFragment extends Fragment implements DataFragment{

    private EventRecyclerAdapter recyclerAdapter;

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
        recyclerAdapter = new EventRecyclerAdapter();
        recyclerView.setAdapter(recyclerAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setReverseLayout(true);
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
    public void refreshData(User user) {
        recyclerAdapter.updateForUser(user);
    }

    @Subscribe
    public void onEvent(Events.EventEvent eventevent) {
        // fuck it
        recyclerAdapter.addItem(eventevent.getEvent());
    }
}
