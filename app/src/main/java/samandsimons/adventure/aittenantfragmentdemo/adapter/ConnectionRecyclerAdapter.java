package samandsimons.adventure.aittenantfragmentdemo.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import samandsimons.adventure.aittenantfragmentdemo.R;
import samandsimons.adventure.aittenantfragmentdemo.model.Connection;
import samandsimons.adventure.aittenantfragmentdemo.model.User;

/**
 * Created by samgrund on 12/8/16.
 */
public class ConnectionRecyclerAdapter extends RecyclerView.Adapter<ConnectionRecyclerAdapter.ViewHolder>{

    List<Connection> connectionList;

    public ConnectionRecyclerAdapter() {
        this.connectionList = new ArrayList<Connection>();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView name;
        private Button btnDeny, btnConfirm;

        public ViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.connectionName);
            btnDeny = (Button) itemView.findViewById(R.id.btnConnectionDeny);
            btnConfirm = (Button) itemView.findViewById(R.id.btnConnectionConfirm);

        }
    }

    @Override
    public ConnectionRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.connection_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ConnectionRecyclerAdapter.ViewHolder holder, final int position) {
        final Connection connection = connectionList.get(position);

        holder.name.setText(connection.getDisplayName());

        holder.btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connection.setConfirmed(true);
                notifyItemChanged(position);
            }
        });

        holder.btnDeny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connection.setConfirmed(false);
                notifyItemChanged(position);
            }
        });

    }

    public void addConnection(String emailAddress) {
        FirebaseDatabase.getInstance().getReference().child("user").
                orderByChild("emailAddress").equalTo(emailAddress)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        User user = dataSnapshot.getValue(User.class);
                        String displayName = user.getEmail();
                        String uid = dataSnapshot.getKey();

                        Connection newConnection = new Connection(false, uid, displayName);
                        connectionList.add(newConnection);
                        notifyItemInserted(connectionList.size()-1);
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.d("FIREBASE_CANCELLED", databaseError.getMessage());
                    }
                });
    }

    public void updateForUser(User user) {
        Log.d("TAG", "Updating for user with " + user.getConnections().size() + " connections.");

        connectionList = user.getConnections();

        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return connectionList.size();
    }
}
