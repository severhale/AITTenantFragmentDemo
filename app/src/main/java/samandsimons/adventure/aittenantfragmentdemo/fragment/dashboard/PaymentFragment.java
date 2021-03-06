package samandsimons.adventure.aittenantfragmentdemo.fragment.dashboard;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import samandsimons.adventure.aittenantfragmentdemo.R;
import samandsimons.adventure.aittenantfragmentdemo.adapter.recycler.PaymentRecyclerAdapter;
import samandsimons.adventure.aittenantfragmentdemo.event.Events;
import samandsimons.adventure.aittenantfragmentdemo.fragment.dialog.AddPaymentDialogFragment;
import samandsimons.adventure.aittenantfragmentdemo.model.Connection;
import samandsimons.adventure.aittenantfragmentdemo.model.Payment;

/**
 * A simple {@link Fragment} subclass.
 */
public class PaymentFragment extends Fragment implements CreateDialogInterface {

    public static final int PAYMENT_REQUEST = -1;
    public static final String PAYMENT_DIALOG = "PAYMENT_DIALOG";

    private PaymentRecyclerAdapter recyclerAdapter;
    private LinearLayoutManager layoutManager;

    public PaymentFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_payment, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.paymentRecycler);
        recyclerAdapter = new PaymentRecyclerAdapter(getContext());
        recyclerView.setAdapter(recyclerAdapter);
        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PAYMENT_REQUEST:
                if (resultCode == Activity.RESULT_OK) {
                    String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    String displayName = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                    Connection selectedConnection = (Connection) data.getSerializableExtra(AddPaymentDialogFragment.CONNECTION);
                    String name = data.getStringExtra(AddPaymentDialogFragment.NAME);
                    String amount = data.getStringExtra(AddPaymentDialogFragment.AMOUNT);
                    Payment newOut = new Payment(id, selectedConnection.getId(), displayName, selectedConnection.getDisplayName(), amount, System.currentTimeMillis(), name, Payment.states.OUTGOING.ordinal());
                    Payment newIn = new Payment(id, selectedConnection.getId(), displayName, selectedConnection.getDisplayName(), amount, System.currentTimeMillis(), name, Payment.states.INCOMING.ordinal());
                    postPayment(newOut, newIn);
                }
                break;
        }
    }

    public void postPayment(Payment out, Payment in) {
        DatabaseReference usersReference = FirebaseDatabase.getInstance().getReference().child("users");
        DatabaseReference postToCurrentUser = usersReference.child(out.getFromId()).child("payments").push();
        postToCurrentUser.setValue(out);

        DatabaseReference postToOtherUser = usersReference.child(in.getToId()).child("payments").child(postToCurrentUser.getKey());
        postToOtherUser.setValue(in);
    }

    @Subscribe
    public void onEvent(Events.PaymentEvent event) {
        recyclerAdapter.addPayment(event.getPayment());
        layoutManager.scrollToPosition(recyclerAdapter.getItemCount() - 1);
    }

    @Subscribe
    public void onEvent(Events.PaymentConfirmedEvent event) {
        recyclerAdapter.onPaymentConfirmed(event.getPayment());
    }

    @Subscribe
    public void onEvent(Events.PaymentRemovedEvent event) {
        recyclerAdapter.removePayment(event.getPayment());
    }

    @Override
    public void openDialog() {
        AddPaymentDialogFragment dialogFragment = new AddPaymentDialogFragment();
        dialogFragment.setTargetFragment(PaymentFragment.this, PAYMENT_REQUEST);
        dialogFragment.show(getFragmentManager().beginTransaction(), PAYMENT_DIALOG);
    }
}
