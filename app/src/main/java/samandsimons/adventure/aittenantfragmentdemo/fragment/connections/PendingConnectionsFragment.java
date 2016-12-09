package samandsimons.adventure.aittenantfragmentdemo.fragment.connections;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import samandsimons.adventure.aittenantfragmentdemo.R;
import samandsimons.adventure.aittenantfragmentdemo.adapter.recycler.PendingConnectionRecyclerAdapter;
import samandsimons.adventure.aittenantfragmentdemo.model.Connection;

/**
 * Created by samgrund on 12/9/16.
 */
public class PendingConnectionsFragment extends Fragment implements OnConnectionChangedListener {

    private PendingConnectionRecyclerAdapter recyclerAdapter;

    public PendingConnectionsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.pending_connections_fragment, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.pendingConnectionRecycler);
        recyclerAdapter = new PendingConnectionRecyclerAdapter(this);
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

    @Override
    public void connectionChanged(Connection connection) {
        String myId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String theirId = connection.getId();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users").child(theirId).child("connections");
        ref.child("outgoing").child(myId).removeValue();
        ref.child("confirmed").child(myId).setValue(connection);

        DatabaseReference inRef = FirebaseDatabase.getInstance().getReference().child("users").child(myId).child("connections");
        inRef.child("incoming").child(theirId).removeValue();
        inRef.child("confirmed").child(theirId).setValue(connection);
    }
}
