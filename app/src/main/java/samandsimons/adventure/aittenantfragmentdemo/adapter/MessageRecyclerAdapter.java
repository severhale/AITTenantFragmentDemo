package samandsimons.adventure.aittenantfragmentdemo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import samandsimons.adventure.aittenantfragmentdemo.R;
import samandsimons.adventure.aittenantfragmentdemo.model.Message;
import samandsimons.adventure.aittenantfragmentdemo.model.User;

/**
 * Created by samgrund on 12/4/16.
 */
public class MessageRecyclerAdapter extends RecyclerView.Adapter<MessageRecyclerAdapter.ViewHolder> {
    private List<Message> messageList;
    SimpleDateFormat sdf;

    public MessageRecyclerAdapter() {
        messageList = new ArrayList<>();
        sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView poster;
        private TextView subject;
        private TextView message;
        private TextView timestamp;

        public ViewHolder(View itemView) {
            super(itemView);

            poster = (TextView) itemView.findViewById(R.id.tMessagePoster);
            subject = (TextView) itemView.findViewById(R.id.tvMessageSubject);
            message = (TextView) itemView.findViewById(R.id.tvMessage);
            timestamp = (TextView) itemView.findViewById(R.id.tvMessageTimestamp);

        }

    }


    @Override
    public MessageRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View note = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_message,parent,false);
        return new ViewHolder(note);
    }

    @Override
    public void onBindViewHolder(MessageRecyclerAdapter.ViewHolder holder, final int position) {
        Message message = messageList.get(position);
        holder.poster.setText(message.getFromDisplay());
        holder.message.setText(message.getText());
        holder.subject.setText(message.getSubject());
        holder.timestamp.setText(sdf.format(new Date(message.getTime())));
    }


    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public void updateForUser(User user) {
        messageList = user.getMessages();
        notifyDataSetChanged();
    }

}
