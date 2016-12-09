package samandsimons.adventure.aittenantfragmentdemo.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import samandsimons.adventure.aittenantfragmentdemo.R;
import samandsimons.adventure.aittenantfragmentdemo.adapter.recycler.PendingConnectionRecyclerAdapter;
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


    public void addConnection(Connection newConnection) {
        recyclerAdapter.addConnection(newConnection);
    }
}
