package samandsimons.adventure.aittenantfragmentdemo.adapter.recycler;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import samandsimons.adventure.aittenantfragmentdemo.R;
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
        paymentList = new ArrayList<>();
        sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_payment, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Payment payment = paymentList.get(position);
        if (userType == User.UserType.LANDLORD) {
            holder.name.setText(payment.getFromId());
        } else {
            holder.name.setText(payment.getToId());
        }
        holder.amount.setText(payment.getAmount());
        holder.date.setText(sdf.format(new Date(payment.getTime())));
        holder.message.setText(payment.getMessage());
        Log.d("TAG", holder.amount.getText().toString());
    }

    @Override
    public int getItemCount() {
        return paymentList.size();
    }

    public void updateForUser(User user) {
        paymentList = user.getPayments();
        userType = User.UserType.values()[user.getType()];
        notifyDataSetChanged();
    }

    public void addItem(Payment newPayment) {
        paymentList.add(newPayment);
        notifyItemInserted(paymentList.size() - 1);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name, amount, date, message;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.paymentName);
            amount = (TextView) itemView.findViewById(R.id.paymentAmount);
            date = (TextView) itemView.findViewById(R.id.paymentDate);
            message = (TextView) itemView.findViewById(R.id.paymentMessage);
        }
    }
}