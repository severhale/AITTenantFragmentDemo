package samandsimons.adventure.aittenantfragmentdemo.adapter.recycler;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import samandsimons.adventure.aittenantfragmentdemo.R;
import samandsimons.adventure.aittenantfragmentdemo.fragment.connections.OnConnectionChangedListener;
import samandsimons.adventure.aittenantfragmentdemo.model.Connection;
import samandsimons.adventure.aittenantfragmentdemo.model.User;

/**
 * Created by samgrund on 12/8/16.
 */
public class PendingConnectionRecyclerAdapter extends RecyclerView.Adapter<PendingConnectionRecyclerAdapter.ViewHolder>{

    private List<Connection> connectionList;
    private OnConnectionChangedListener changedListener;

    public PendingConnectionRecyclerAdapter(OnConnectionChangedListener changedListener) {
        this.connectionList = new ArrayList<Connection>();
        this.changedListener = changedListener;
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
                connection.setConnectionType(Connection.State.CONFIRMED);
                connectionList.remove(position);
                notifyItemRemoved(position);
                // this is where we do it
                changedListener.connectionChanged(connection);
            }
        });

        holder.btnDeny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connectionList.remove(position);
                notifyItemRemoved(position);
            }
        });

    }

    public void addConnection(Connection newConnection) {
        connectionList.add(newConnection);
        notifyItemInserted(connectionList.size()-1);

    }

    public List<Connection> getConnectionList() {
        return connectionList;
    }

    @Override
    public int getItemCount() {
        return connectionList.size();
    }


}
