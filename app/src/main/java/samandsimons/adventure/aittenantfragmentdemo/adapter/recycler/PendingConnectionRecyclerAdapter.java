package samandsimons.adventure.aittenantfragmentdemo.adapter.recycler;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import samandsimons.adventure.aittenantfragmentdemo.R;
import samandsimons.adventure.aittenantfragmentdemo.model.Connection;
import samandsimons.adventure.aittenantfragmentdemo.model.User;

/**
 * Created by samgrund on 12/8/16.
 */
public class PendingConnectionRecyclerAdapter extends RecyclerView.Adapter<PendingConnectionRecyclerAdapter.ViewHolder>{

    private List<Connection> connectionList;

    public PendingConnectionRecyclerAdapter() {
        this.connectionList = new ArrayList<>(User.getCurrentUser().getPendingConnections());
    }

    @Override
    public int getItemCount() {
        return connectionList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView name;
        private ImageView btnDeny, btnConfirm;

        public ViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.connectionName);
            btnDeny = (ImageView) itemView.findViewById(R.id.btnConnectionDeny);
            btnConfirm = (ImageView) itemView.findViewById(R.id.btnConnectionConfirm);
        }
    }

    @Override
    public PendingConnectionRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.pending_connection_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final PendingConnectionRecyclerAdapter.ViewHolder holder, final int position) {
        final Connection connection = connectionList.get(position);

        holder.name.setText(connection.getDisplayName());

        holder.btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // this is where we do it
                cancelConnection(connection);
                confirmConnection(connection);
            }
        });

        holder.btnDeny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelConnection(connection);
            }
        });
    }

    public void addConnection(Connection newConnection) {
        connectionList.add(newConnection);
        notifyItemInserted(connectionList.size()-1);
    }

    public void removeConnection(Connection connection) {
        int index = -1;
        for (int i = 0; i < connectionList.size(); i++) {
            if (connection.getId().equals(connectionList.get(i).getId())) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            return;
        }
        connectionList.remove(index);
        notifyItemRemoved(index);
    }

    // confirm in firebase
    public void confirmConnection(Connection connection) {
        String myId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String myDisplay = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        String theirId = connection.getId();

        Connection theirConnection = new Connection(myId, myDisplay);

        DatabaseReference outRef = FirebaseDatabase.getInstance().getReference().child("users").child(theirId).child("connections");
        outRef.child("confirmed").child(myId).setValue(theirConnection);

        DatabaseReference inRef = FirebaseDatabase.getInstance().getReference().child("users").child(myId).child("connections");
        inRef.child("confirmed").child(theirId).setValue(connection);
    }

    // remove in firebase
    public void cancelConnection(Connection connection) {
        String myId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String theirId = connection.getId();

        DatabaseReference outRef = FirebaseDatabase.getInstance().getReference().child("users").child(theirId).child("connections");
        outRef.child("outgoing").child(myId).removeValue();

        DatabaseReference inRef = FirebaseDatabase.getInstance().getReference().child("users").child(myId).child("connections");
        inRef.child("incoming").child(theirId).removeValue();
    }


}
