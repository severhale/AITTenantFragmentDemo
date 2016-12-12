package samandsimons.adventure.aittenantfragmentdemo.adapter.recycler;

import android.animation.ArgbEvaluator;
import android.animation.IntEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import samandsimons.adventure.aittenantfragmentdemo.Dashboard;
import samandsimons.adventure.aittenantfragmentdemo.R;
import samandsimons.adventure.aittenantfragmentdemo.model.Payment;
import samandsimons.adventure.aittenantfragmentdemo.model.User;

/**
 * Created by Simon on 12/8/2016.
 */

public class PaymentRecyclerAdapter extends RecyclerView.Adapter<PaymentRecyclerAdapter.ViewHolder> {
    private List<Payment> paymentList;
    private SimpleDateFormat sdf;
    private Context context;

    public PaymentRecyclerAdapter(Context context) {
        this.context = context;
        if (Dashboard.hasFilterConnection()) {
            paymentList = User.getCurrentUser().getPaymentsForUser(Dashboard.getFilterId());
        } else {
            paymentList = new ArrayList<>(User.getCurrentUser().getPayments());
        }
        sdf = new SimpleDateFormat("EEE, MMM d, yyyy");
    }

    @Override
    public int getItemCount() {
        return paymentList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_payment, parent, false);
        return new ViewHolder(v);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout layout;
        public TextView subject, amount, date, name;
        public ImageView btnConfirm, btnDeny;

        public ViewHolder(View itemView) {
            super(itemView);
            layout = (LinearLayout) itemView.findViewById(R.id.payment_item_layout);
            subject = (TextView) itemView.findViewById(R.id.tvPaymentSubject);
            amount = (TextView) itemView.findViewById(R.id.tvPaymentAmount);
            date = (TextView) itemView.findViewById(R.id.tvPaymentDate);
            name = (TextView) itemView.findViewById(R.id.tvPaymentName);
            btnConfirm = (ImageView) itemView.findViewById(R.id.btnConfirmPayment);
            btnDeny = (ImageView) itemView.findViewById(R.id.btnDenyPayment);
        }
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Payment payment = paymentList.get(position);
        boolean incoming = (payment.getState() == Payment.states.INCOMING.ordinal());

        if (!incoming) {
            holder.btnConfirm.setVisibility(View.GONE);
            holder.btnDeny.setVisibility(View.GONE);
            holder.name.setText(payment.getToDisplay());
            holder.amount.setText("-$" + payment.getAmount());
        } else {
            holder.btnConfirm.setVisibility(View.VISIBLE);
            holder.btnDeny.setVisibility(View.VISIBLE);
            holder.name.setText(payment.getFromDisplay());
            holder.amount.setText("+$" + payment.getAmount());

            holder.btnConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    FirebaseDatabase.getInstance().getReference().child("users").child(payment.getToId()).child("payments")
                            .child(payment.getKey()).child("confirmed").setValue(true);

                    FirebaseDatabase.getInstance().getReference().child("users").child(payment.getFromId()).child("payments")
                            .child(payment.getKey()).child("confirmed").setValue(true);
                }
            });
            holder.btnDeny.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cancelPayment(payment);
                }
            });
        }

        if (payment.isConfirmed()) {
            paymentConfirmed(holder, payment);
        } else {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(holder.layout.getLayoutParams());
            layoutParams.setMargins(10, 10, 10, 10);
            holder.layout.setLayoutParams(layoutParams);
            holder.layout.getBackground().setColorFilter(new
                    PorterDuffColorFilter(context.getResources().getColor(R.color.colorLightGrey), PorterDuff.Mode.MULTIPLY));
        }

        holder.date.setText(sdf.format(new Date(payment.getTime())));
        holder.subject.setText(payment.getMessage());
    }

    // remove in firebase
    private void cancelPayment(Payment payment) {
        String myId = payment.getFromId();
        String theirId = payment.getToId();

        DatabaseReference outRef = FirebaseDatabase.getInstance().getReference().child("users").child(theirId).child("payments");
        outRef.child(payment.getKey()).removeValue();

        DatabaseReference inRef = FirebaseDatabase.getInstance().getReference().child("users").child(myId).child("payments");
        inRef.child(payment.getKey()).removeValue();
    }

    public void addPayment(Payment newPayment) {
        if (Dashboard.hasFilterConnection() &&
                !newPayment.getFromId().equals(Dashboard.getFilterId()) &&
                !newPayment.getToId().equals(Dashboard.getFilterId())) {
            // message is not to/from filter id, ignore it
            return;
        }
        paymentList.add(newPayment);
        notifyItemInserted(paymentList.size() - 1);
    }

    public void removePayment(Payment payment) {
        int index = -1;
        for (int i = 0; i < paymentList.size(); i++) {
            if (payment.getKey().equals(paymentList.get(i).getKey())) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            return;
        }
        paymentList.remove(index);
        notifyItemRemoved(index);
    }

    public void onPaymentConfirmed(Payment payment) {
        int index = -1;
        for (int i = 0; i < paymentList.size(); i++) {
            if (payment.getKey().equals(paymentList.get(i).getKey())) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            return;
        }
        paymentList.set(index, payment);
        notifyItemChanged(index);
    }

    private void paymentConfirmed(final ViewHolder holder, Payment payment) {

        changeMarginSize(holder, payment);

        animateColorChange(holder, payment);

        holder.btnConfirm.setVisibility(View.GONE);
        holder.btnDeny.setVisibility(View.GONE);
    }

    private void animateColorChange(final ViewHolder holder, Payment payment) {
        boolean incoming = (payment.getState() == Payment.states.INCOMING.ordinal());
        int colorTo = incoming ? context.getResources().getColor(R.color.colorGreen) : context.getResources().getColor(R.color.colorRed);
        int colorFrom = context.getResources().getColor(R.color.colorLightGrey);
        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
        colorAnimation.setDuration(100); // milliseconds
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                holder.layout.getBackground().setColorFilter(new
                        PorterDuffColorFilter((int)animator.getAnimatedValue(), PorterDuff.Mode.MULTIPLY));
            }
        });
        colorAnimation.start();
    }

    private void changeMarginSize(ViewHolder holder, Payment payment) {
        boolean incoming = (payment.getState() == Payment.states.INCOMING.ordinal());
        int leftMargin = incoming ? 50 : 0;
        int rightMargin = 50 - leftMargin;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(holder.layout.getLayoutParams());
        layoutParams.setMargins(dpToPixels(leftMargin), 0, dpToPixels(rightMargin), 0);
        holder.layout.setLayoutParams(layoutParams);
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
