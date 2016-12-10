package samandsimons.adventure.aittenantfragmentdemo.fragment.connections;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import samandsimons.adventure.aittenantfragmentdemo.R;
import samandsimons.adventure.aittenantfragmentdemo.adapter.recycler.PendingConnectionRecyclerAdapter;
import samandsimons.adventure.aittenantfragmentdemo.event.Events;
import samandsimons.adventure.aittenantfragmentdemo.model.Connection;

/**
 * Created by samgrund on 12/9/16.
 */
public class PendingConnectionsFragment extends Fragment {

    private PendingConnectionRecyclerAdapter recyclerAdapter;

    public PendingConnectionsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        EventBus.getDefault().register(this);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.pending_connections_fragment, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.pendingConnectionRecycler);
        recyclerAdapter = new PendingConnectionRecyclerAdapter();
        recyclerView.setAdapter(recyclerAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);

        return view;
    }

    @Subscribe
    public void onEvent(Events.PendingConnectionEvent event) {
        Log.d("TAG", "WHY IS THIS HAPPENING");
        addConnection(event.getPending());
    }

    @Subscribe
    public void onEvent(Events.PendingConnectionRemoved event) {
        Log.d("TAG", "Got PendingConnectionRemoved event");
        removeConnection(event.getConnection());
    }

    private void removeConnection(Connection connection) {
        recyclerAdapter.removeConnection(connection);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    public void addConnection(Connection newConnection) {
        recyclerAdapter.addConnection(newConnection);
    }
}
