package samandsimons.adventure.aittenantfragmentdemo.adapter.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import samandsimons.adventure.aittenantfragmentdemo.R;
import samandsimons.adventure.aittenantfragmentdemo.model.Connection;

/**
 * Created by samgrund on 12/9/16.
 */
public class RequestedConnectionRecyclerAdapter extends RecyclerView.Adapter<RequestedConnectionRecyclerAdapter.ViewHolder>{

    private List<Connection> connectionList;

    public RequestedConnectionRecyclerAdapter() {
        this.connectionList = new ArrayList<Connection>();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView name;
        private Button btnCancel;

        public ViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.tvRequestedConnectionName);
            btnCancel = (Button) itemView.findViewById(R.id.btnCancelConnectionRequest);
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
                // cancel connection request
            }
        });

    }

    public void addConnection(Connection newConnection) {
        connectionList.add(newConnection);
        notifyItemInserted(connectionList.size()-1);

    }

    @Override
    public int getItemCount() {
        return connectionList.size();
    }
}
