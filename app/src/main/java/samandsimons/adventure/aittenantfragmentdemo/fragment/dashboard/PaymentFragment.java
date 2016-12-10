package samandsimons.adventure.aittenantfragmentdemo.fragment.dashboard;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
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
import samandsimons.adventure.aittenantfragmentdemo.model.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class PaymentFragment extends Fragment {
    public static final int PAYMENT_REQUEST = -1;
    public static final String PAYMENT_DIALOG = "PAYMENT_DIALOG";

    PaymentRecyclerAdapter recyclerAdapter;

    public PaymentFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_payment, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.paymentRecycler);
        recyclerAdapter = new PaymentRecyclerAdapter();
        recyclerView.setAdapter(recyclerAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fabNewPayment);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddPaymentDialogFragment dialogFragment = new AddPaymentDialogFragment();
                dialogFragment.setTargetFragment(PaymentFragment.this, PAYMENT_REQUEST);
                dialogFragment.show(getFragmentManager().beginTransaction(), PAYMENT_DIALOG);
            }
        });

        return view;
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
                    Payment newPayment = new Payment(id, selectedConnection.getId(), displayName, selectedConnection.getDisplayName(), amount, System.currentTimeMillis(), name);
                    postPayment(newPayment);
                }
                else if (resultCode == Activity.RESULT_CANCELED) {
                    // probably don't do anything
                }
                break;
        }
    }

    public void postPayment(Payment newPayment) {
        DatabaseReference usersReference = FirebaseDatabase.getInstance().getReference().child("users");
        DatabaseReference postToCurrentUser = usersReference.child(newPayment.getFromDisplay()).child("payments").push();
        postToCurrentUser.setValue(newPayment);

        DatabaseReference postToOtherUser = usersReference.child(newPayment.getToDisplay()).child("payments").push();
        postToOtherUser.setValue(newPayment);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEvent(Events.PaymentEvent event) {
        recyclerAdapter.addItem(event.getPayment());
    }
}
