package samandsimons.adventure.aittenantfragmentdemo.adapter.recycler;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import samandsimons.adventure.aittenantfragmentdemo.Dashboard;
import samandsimons.adventure.aittenantfragmentdemo.R;
import samandsimons.adventure.aittenantfragmentdemo.event.Events;
import samandsimons.adventure.aittenantfragmentdemo.model.Connection;
import samandsimons.adventure.aittenantfragmentdemo.model.User;

/**
 * Created by samgrund on 12/9/16.
 */
public class ConfirmedConnectionRecyclerAdapter extends RecyclerView.Adapter<ConfirmedConnectionRecyclerAdapter.ViewHolder>{


    private List<Connection> connectionList;
    private Context context;

    public ConfirmedConnectionRecyclerAdapter(Context context) {
        this.connectionList = new ArrayList<>(User.getCurrentUser().getConfirmedConnections());
        this.context = context;
    }

    public List<Connection> getConnectionList() {
        return connectionList;
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
            Log.w("TAG", "ERROR REMOVING CONNECTION");
            return;
        }
        connectionList.remove(index);
        notifyItemRemoved(index);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView name;
        private LinearLayout layout;
        private ImageView btnDelete;

        public ViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.tvConfirmedConnectionName);
            layout = (LinearLayout) view.findViewById(R.id.confirmedConnectionLayout);
            btnDelete = (ImageView) view.findViewById(R.id.btnRemoveConnection);
        }
    }

    @Override
    public ConfirmedConnectionRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.confirmed_connection_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ConfirmedConnectionRecyclerAdapter.ViewHolder holder, final int position) {
        holder.name.setText(connectionList.get(position).getDisplayName());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(context, Dashboard.class);
                intent.putExtra(Dashboard.FILTER_CONNECTION_EXTRA, connectionList.get(position));
                context.startActivity(intent);
            }
        });
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteConnection(connectionList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return connectionList.size();
    }

    public void addConnection(Connection newConnection) {
        connectionList.add(newConnection);
        notifyItemInserted(connectionList.size()-1);

    }

    private void deleteConnection(Connection connection) {
        String myId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String theirId = connection.getId();

        DatabaseReference outRef = FirebaseDatabase.getInstance().getReference().child("users").child(theirId).child("connections");
        outRef.child("confirmed").child(myId).removeValue();

        DatabaseReference inRef = FirebaseDatabase.getInstance().getReference().child("users").child(myId).child("connections");
        inRef.child("confirmed").child(theirId).removeValue();
    }
}
