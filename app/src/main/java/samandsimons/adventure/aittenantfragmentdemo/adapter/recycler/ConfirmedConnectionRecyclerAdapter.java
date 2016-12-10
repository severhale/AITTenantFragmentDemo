package samandsimons.adventure.aittenantfragmentdemo.adapter.recycler;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import samandsimons.adventure.aittenantfragmentdemo.R;
import samandsimons.adventure.aittenantfragmentdemo.model.Connection;
import samandsimons.adventure.aittenantfragmentdemo.model.User;

/**
 * Created by samgrund on 12/9/16.
 */
public class ConfirmedConnectionRecyclerAdapter extends RecyclerView.Adapter<ConfirmedConnectionRecyclerAdapter.ViewHolder>{


    List<Connection> connectionList;

    public ConfirmedConnectionRecyclerAdapter() {
        this.connectionList = User.getCurrentUser().getConfirmedConnections();
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

        public ViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.tvConfirmedConnectionName);

        }
    }

    @Override
    public ConfirmedConnectionRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.confirmed_connection_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ConfirmedConnectionRecyclerAdapter.ViewHolder holder, int position) {
        holder.name.setText(connectionList.get(position).getDisplayName());
    }

    @Override
    public int getItemCount() {
        return connectionList.size();
    }

    public void addConnection(Connection newConnection) {
        connectionList.add(newConnection);
        notifyItemInserted(connectionList.size()-1);

    }
}
