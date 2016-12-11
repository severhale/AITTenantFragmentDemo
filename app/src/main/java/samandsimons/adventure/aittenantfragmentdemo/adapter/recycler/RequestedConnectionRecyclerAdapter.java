package samandsimons.adventure.aittenantfragmentdemo.adapter.recycler;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import samandsimons.adventure.aittenantfragmentdemo.R;
import samandsimons.adventure.aittenantfragmentdemo.model.Connection;
import samandsimons.adventure.aittenantfragmentdemo.model.User;

/**
 * Created by samgrund on 12/9/16.
 */
public class RequestedConnectionRecyclerAdapter extends RecyclerView.Adapter<RequestedConnectionRecyclerAdapter.ViewHolder>{

    private List<Connection> connectionList;

    public RequestedConnectionRecyclerAdapter() {
        this.connectionList = new ArrayList<>(User.getCurrentUser().getRequestedConnections());
    }

    @Override
    public int getItemCount() {
        return connectionList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView name;
        private ImageView btnCancel;

        public ViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.tvRequestedConnectionName);
            btnCancel = (ImageView) itemView.findViewById(R.id.btnCancelConnectionRequest);
        }
    }

    @Override
    public RequestedConnectionRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.requested_connection_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RequestedConnectionRecyclerAdapter.ViewHolder holder, int position) {
        final Connection connection = connectionList.get(position);

        holder.name.setText(connection.getDisplayName());

        holder.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelConnection(connection);
                // cancel connection request
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

    // remove in firebase
    public void cancelConnection(Connection connection) {
        String myId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String theirId = connection.getId();

        DatabaseReference theirRef = FirebaseDatabase.getInstance().getReference().child("users").child(theirId).child("connections");
        theirRef.child("incoming").child(myId).removeValue();

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("users").child(myId).child("connections");
        myRef.child("outgoing").child(theirId).removeValue();
    }
}
