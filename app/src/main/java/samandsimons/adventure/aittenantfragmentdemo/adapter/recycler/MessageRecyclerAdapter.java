package samandsimons.adventure.aittenantfragmentdemo.adapter.recycler;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import samandsimons.adventure.aittenantfragmentdemo.Dashboard;
import samandsimons.adventure.aittenantfragmentdemo.R;
import samandsimons.adventure.aittenantfragmentdemo.model.Message;
import samandsimons.adventure.aittenantfragmentdemo.model.User;

/**
 * Created by samgrund on 12/4/16.
 */
public class MessageRecyclerAdapter extends RecyclerView.Adapter<MessageRecyclerAdapter.ViewHolder> {
    private List<Message> messageList;
    SimpleDateFormat sdf;
    private Context context;

    public MessageRecyclerAdapter(Context context) {
        this.context = context;
        if (Dashboard.hasFilterConnection()) {
            messageList = User.getCurrentUser().getMessagesForUser(Dashboard.getFilterId());
        } else {
            messageList = new ArrayList<>(User.getCurrentUser().getMessages());
        }
        sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
    }

    public void addItem(Message newMessage) {
        if (Dashboard.hasFilterConnection() &&
                !newMessage.getFromId().equals(Dashboard.getFilterId()) &&
                !newMessage.getToId().equals(Dashboard.getFilterId())) {
            // message is not to/from filter id, ignore it
            return;
        }
        messageList.add(newMessage);
        notifyItemInserted(messageList.size() - 1);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView poster;
        private TextView subject;
        private TextView message;
        private TextView timestamp;
        private LinearLayout layout;

        public ViewHolder(View itemView) {
            super(itemView);

            poster = (TextView) itemView.findViewById(R.id.tvMessagePoster);
            subject = (TextView) itemView.findViewById(R.id.tvMessageSubject);
            message = (TextView) itemView.findViewById(R.id.tvMessage);
            timestamp = (TextView) itemView.findViewById(R.id.tvMessageTimestamp);
            layout = (LinearLayout) itemView.findViewById(R.id.messageLayout);

        }

    }


    @Override
    public MessageRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View note = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_incoming_message, parent, false);
        return new ViewHolder(note);
    }

    @Override
    public void onBindViewHolder(MessageRecyclerAdapter.ViewHolder holder, final int position) {
        Message message = messageList.get(position);
        holder.poster.setText(message.getFromDisplay());
        holder.message.setText(message.getText());
        holder.subject.setText(message.getSubject());
        holder.timestamp.setText(sdf.format(new Date(message.getTime())));

        boolean incoming = message.getToId().equals(FirebaseAuth.getInstance().getCurrentUser().getUid());
        int leftMargin = incoming ? 0 : 30;
        int rightMargin = 30 - leftMargin;
        int messageGravity = incoming ? Gravity.LEFT : Gravity.RIGHT;
        int infoGravity = incoming ? Gravity.RIGHT : Gravity.LEFT;

        LinearLayout.LayoutParams messageParams = new LinearLayout.LayoutParams(holder.message.getLayoutParams());
        messageParams.gravity = messageGravity;

        LinearLayout.LayoutParams subjectParams = new LinearLayout.LayoutParams(holder.subject.getLayoutParams());
        subjectParams.gravity = messageGravity;

        LinearLayout.LayoutParams posterParams = new LinearLayout.LayoutParams(holder.poster.getLayoutParams());
        posterParams.gravity = infoGravity;

        LinearLayout.LayoutParams timestampParams = new LinearLayout.LayoutParams(holder.timestamp.getLayoutParams());
        timestampParams.gravity = infoGravity;

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(holder.layout.getLayoutParams());
        layoutParams.setMargins(dpToPixels(leftMargin), 0, dpToPixels(rightMargin), 0);

        if (incoming) {
            holder.layout.setBackground(context.getResources().getDrawable(R.drawable.rounded_rectangle));
        } else {
            holder.layout.setBackground(context.getResources().getDrawable(R.drawable.rounded_rectangle_outgoing));
        }

        holder.message.setLayoutParams(messageParams);
        holder.subject.setLayoutParams(subjectParams);
        holder.poster.setLayoutParams(posterParams);
        holder.timestamp.setLayoutParams(timestampParams);
        holder.layout.setLayoutParams(layoutParams);
    }


    @Override
    public int getItemCount() {
        return messageList.size();
    }

    private int dpToPixels(int dp) {
        Resources r = context.getResources();
        int px = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                r.getDisplayMetrics()
        );
        return px;
    }

}
