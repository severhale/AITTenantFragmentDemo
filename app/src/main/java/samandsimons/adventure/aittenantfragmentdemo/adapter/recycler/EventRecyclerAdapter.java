package samandsimons.adventure.aittenantfragmentdemo.adapter.recycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import samandsimons.adventure.aittenantfragmentdemo.Dashboard;
import samandsimons.adventure.aittenantfragmentdemo.R;
import samandsimons.adventure.aittenantfragmentdemo.model.Event;
import samandsimons.adventure.aittenantfragmentdemo.model.User;

/**
 * Created by Simon on 12/8/2016.
 */

public class EventRecyclerAdapter extends RecyclerView.Adapter<EventRecyclerAdapter.ViewHolder> {
    private List<Event> eventList;
    private SimpleDateFormat sdf;
    private Context context;

    public EventRecyclerAdapter(Context context) {
        this.context = context;
        if (Dashboard.hasFilterConnection()) {
            eventList = User.getCurrentUser().getEventsForUser(Dashboard.getFilterId());
        } else {
            eventList = new ArrayList<>(User.getCurrentUser().getEvents());
            Collections.sort(eventList, new Comparator<Event>() {
                @Override
                public int compare(Event o1, Event o2) {
                    return (int) (o1.getTime() - o2.getTime());
                }
            });
        }
        sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
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
        holder.author.setText(event.getFromDisplay());
        holder.date.setText(sdf.format(event.getTime()));
        holder.peopleAttending.setText(String.format(Locale.getDefault(), context.getString(R.string.people_invited), event.getEventUsers().size()));
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public void addItem(Event newEvent) {
        if (Dashboard.hasFilterConnection() &&
                !newEvent.getEventUsers().containsKey(Dashboard.getFilterId()) &&
                !newEvent.getFromId().equals(Dashboard.getFilterId())) {
            // message is not to/from filter id, ignore it
            return;
        }
        int index = 0;
        for (index = eventList.size() - 1; index >= 0; index--) {
            if (newEvent.getTime() <= eventList.get(index).getTime()) {
                break;
            }
        }
        index += 1;
        eventList.add(index, newEvent);
        notifyItemInserted(index);
    }

    public void removeItem(Event event) {
        int index = -1;
        for (int i = 0; i < eventList.size(); i++) {
            if (event.getKey().equals(eventList.get(i).getKey())) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            Log.w("TAG", "ERROR REMOVING EVENT");
            return;
        }
        eventList.remove(index);
        notifyItemRemoved(index);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title, author, date, peopleAttending;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.tvEventTitle);
            author = (TextView) itemView.findViewById(R.id.tvEventAuthor);
            date = (TextView) itemView.findViewById(R.id.tvEventDate);
            peopleAttending = (TextView) itemView.findViewById(R.id.tvNumPeople);
        }
    }
}
