package samandsimons.adventure.aittenantfragmentdemo.adapter;

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
    public void onBindViewHolder(ConnectionRecyclerAdapter.ViewHolder holder, int position) {
        Connection connection = connectionList.get(position);

        holder.name.setText(connection.getDisplayName());

        holder.btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        holder.btnDeny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    public void updateForUser(User user) {
        Log.d("TAG", "Updating for user with " + user.getPayments().size() + " payments");

        connectionList = user.getConnections();

        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
