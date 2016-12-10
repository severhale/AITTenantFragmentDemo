package samandsimons.adventure.aittenantfragmentdemo.fragment.connections;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import samandsimons.adventure.aittenantfragmentdemo.R;
import samandsimons.adventure.aittenantfragmentdemo.adapter.recycler.RequestedConnectionRecyclerAdapter;
import samandsimons.adventure.aittenantfragmentdemo.event.Events;
import samandsimons.adventure.aittenantfragmentdemo.model.Connection;

/**
 * Created by samgrund on 12/9/16.
 */
public class RequestedConnectionsFragment extends Fragment{

    private RequestedConnectionRecyclerAdapter recyclerAdapter;

    public RequestedConnectionsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        EventBus.getDefault().register(this);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.requested_connection_fragment, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.requestedConnectionRecycler);
        recyclerAdapter = new RequestedConnectionRecyclerAdapter();
        recyclerView.setAdapter(recyclerAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);

        return view;
    }

    @Subscribe
    public void onEvent(Events.RequestedConnectionEvent event) {
        addConnection(event.getRequested());
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
