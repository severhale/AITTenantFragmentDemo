package samandsimons.adventure.aittenantfragmentdemo.adapter.recycler;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import samandsimons.adventure.aittenantfragmentdemo.Dashboard;
import samandsimons.adventure.aittenantfragmentdemo.R;
import samandsimons.adventure.aittenantfragmentdemo.event.Events;
import samandsimons.adventure.aittenantfragmentdemo.model.Payment;
import samandsimons.adventure.aittenantfragmentdemo.model.User;

/**
 * Created by Simon on 12/8/2016.
 */

public class PaymentRecyclerAdapter extends RecyclerView.Adapter<PaymentRecyclerAdapter.ViewHolder> {
    private List<Payment> paymentList;
    private User.UserType userType;
    private SimpleDateFormat sdf;

    public PaymentRecyclerAdapter() {
        if (Dashboard.hasFilterConnection()) {
            paymentList = User.getCurrentUser().getPaymentsForUser(Dashboard.getFilterId());
        } else {
            paymentList = new ArrayList<>(User.getCurrentUser().getPayments());
        }
        sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_payment, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Payment payment = paymentList.get(position);

        if (payment.getState() == Payment.states.OUTGOING.ordinal()) {
            holder.btnConfirm.setVisibility(View.GONE);
        } else {
            holder.btnConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    payment.setConfirmed(true);

                    FirebaseDatabase.getInstance().getReference().child("users").child(payment.getFromId()).child("payments")
                            .child(payment.getKey()).child("confirmed").setValue(true);

                    FirebaseDatabase.getInstance().getReference().child("users").child(payment.getToId()).child("payments")
                            .child(payment.getKey()).child("confirmed").setValue(true);

                    //paymentConfirmed(holder, payment);
                }
            });
        }

        if (payment.isConfirmed()) {
            paymentConfirmed(holder, payment);
        }

        if (userType == User.UserType.LANDLORD) {
            holder.sender.setText(payment.getFromDisplay());
        } else {
            holder.sender.setText(payment.getToDisplay());
        }
        holder.amount.setText("$"+payment.getAmount());
        holder.date.setText(sdf.format(new Date(payment.getTime())));
        holder.name.setText(payment.getMessage());

        Log.d("TAG", holder.amount.getText().toString());
    }

    public void onPaymentConfirmed(Payment payment) {
        int index = -1;
        for (int i = 0; i < paymentList.size(); i++) {
            if (payment.getFromId().equals(paymentList.get(i).getFromId()) || payment.getToId().equals(paymentList.get(i).getToId())) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            Log.w("TAG", "ERROR REMOVING CONNECTION");
            return;
        }
        notifyItemChanged(index);
    }

    private void paymentConfirmed(ViewHolder holder, Payment payment) {
        if (payment.getState() == Payment.states.OUTGOING.ordinal()) {
            holder.layout.setBackgroundColor(Color.parseColor("#b3ff88"));
        } else {
            holder.layout.setBackgroundColor(Color.parseColor("#ff7272"));
        }
        holder.btnConfirm.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return paymentList.size();
    }

    public void addItem(Payment newPayment) {
        if (Dashboard.hasFilterConnection() &&
                !newPayment.getFromId().equals(Dashboard.getFilterId()) &&
                !newPayment.getToId().equals(Dashboard.getFilterId())) {
            // message is not to/from filter id, ignore it
            return;
        }
        paymentList.add(newPayment);
        notifyItemInserted(paymentList.size() - 1);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout layout;
        public TextView name, amount, date, sender;
        public Button btnConfirm;

        public ViewHolder(View itemView) {
            super(itemView);
            layout = (LinearLayout) itemView.findViewById(R.id.payment_item_layout);
            name = (TextView) itemView.findViewById(R.id.tvPaymentName);
            amount = (TextView) itemView.findViewById(R.id.tvPaymentAmount);
            date = (TextView) itemView.findViewById(R.id.tvPaymentDate);
            sender = (TextView) itemView.findViewById(R.id.tvPaymentSender);
            btnConfirm = (Button) itemView.findViewById(R.id.btnConfirmPayment);
        }
    }
}
