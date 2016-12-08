package samandsimons.adventure.aittenantfragmentdemo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by samgrund on 12/8/16.
 */
public class ConnectionRecyclerAdapter extends RecyclerView.Adapter<ConnectionRecyclerAdapter.ViewHolder>{



    public class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }


    @Override
    public ConnectionRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ConnectionRecyclerAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
