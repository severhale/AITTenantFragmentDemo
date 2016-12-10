package samandsimons.adventure.aittenantfragmentdemo.adapter.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import samandsimons.adventure.aittenantfragmentdemo.R;
import samandsimons.adventure.aittenantfragmentdemo.model.Event;
import samandsimons.adventure.aittenantfragmentdemo.model.User;

/**
 * Created by Simon on 12/8/2016.
 */

public class EventRecyclerAdapter extends RecyclerView.Adapter<EventRecyclerAdapter.ViewHolder> {
    private List<Event> eventList;
    private SimpleDateFormat sdf;

    public EventRecyclerAdapter() {
        sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        eventList = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Event event = eventList.get(position);
        holder.title.setText(event.getTitle());
        holder.description.setText(event.getDescription());
        holder.author.setText(event.getFromDisplay());
        holder.date.setText(sdf.format(event.getTime()));
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public void updateForUser(User user) {
        eventList = user.getEvents();
        notifyDataSetChanged();
    }

    public void addItem(Event newEvent) {
        eventList.add(newEvent);
        notifyItemInserted(eventList.size() - 1);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title, description, author, date;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.tvEventTitle);
            description = (TextView) itemView.findViewById(R.id.tvEventDescription);
            author = (TextView) itemView.findViewById(R.id.tvEventAuthor);
            date = (TextView) itemView.findViewById(R.id.tvEventDate);
        }
    }
}