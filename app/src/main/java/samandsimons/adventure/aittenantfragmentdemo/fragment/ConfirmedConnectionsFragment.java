package samandsimons.adventure.aittenantfragmentdemo.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DatabaseReference;

import samandsimons.adventure.aittenantfragmentdemo.R;
import samandsimons.adventure.aittenantfragmentdemo.adapter.recycler.ConfirmedConnectionRecyclerAdapter;
import samandsimons.adventure.aittenantfragmentdemo.model.Connection;

/**
 * Created by samgrund on 12/9/16.
 */
public class ConfirmedConnectionsFragment extends Fragment {

    private DatabaseReference usersReference;

    private ConfirmedConnectionRecyclerAdapter recyclerAdapter;

    public ConfirmedConnectionsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.confirmed_connections_fragment, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.confirmedConnectionRecycler);
        recyclerAdapter = new ConfirmedConnectionRecyclerAdapter();
        recyclerView.setAdapter(recyclerAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);

        return view;
    }

    public void addConnection(Connection connection) {
        recyclerAdapter.addConnection(connection);
    }
}
