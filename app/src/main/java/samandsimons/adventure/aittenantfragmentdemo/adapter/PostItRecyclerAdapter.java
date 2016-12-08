package samandsimons.adventure.aittenantfragmentdemo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import samandsimons.adventure.aittenantfragmentdemo.R;

/**
 * Created by samgrund on 12/4/16.
 */
public class PostItRecyclerAdapter extends RecyclerView.Adapter<PostItRecyclerAdapter.ViewHolder> {


    public PostItRecyclerAdapter() {
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView poster;
        private TextView subject;
        private TextView message;

        public ViewHolder(View itemView) {
            super(itemView);

            poster = (TextView) itemView.findViewById(R.id.tMessagePoster);
            subject = (TextView) itemView.findViewById(R.id.tvMessageSubject);
            message = (TextView) itemView.findViewById(R.id.tvMessage);

        }

    }


    @Override
    public PostItRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View note = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.message,parent,false);
        return new ViewHolder(note);
    }

    @Override
    public void onBindViewHolder(PostItRecyclerAdapter.ViewHolder holder, final int position) {

    }


    @Override
    public int getItemCount() {
        return 0;
    }


}
