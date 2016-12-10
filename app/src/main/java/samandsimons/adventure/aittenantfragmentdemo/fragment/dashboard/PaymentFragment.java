package samandsimons.adventure.aittenantfragmentdemo.fragment.dashboard;


import android.os.Bundle;
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
import com.google.firebase.database.FirebaseDatabase;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import samandsimons.adventure.aittenantfragmentdemo.R;
import samandsimons.adventure.aittenantfragmentdemo.adapter.recycler.PaymentRecyclerAdapter;
import samandsimons.adventure.aittenantfragmentdemo.event.Events;
import samandsimons.adventure.aittenantfragmentdemo.model.Payment;
import samandsimons.adventure.aittenantfragmentdemo.model.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class PaymentFragment extends Fragment implements DataFragment {

    PaymentRecyclerAdapter recyclerAdapter;

    public PaymentFragment() {
        String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase.getInstance().getReference().child("users").child(id).child("payments").
                addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        Payment newPayment = dataSnapshot.getValue(Payment.class);
                        recyclerAdapter.addItem(newPayment);
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
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
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void refreshData(User user) {
        recyclerAdapter.updateForUser(user);
    }

    @Subscribe
    public void onEvent(Events.PaymentEvent event) {
        recyclerAdapter.addItem(event.getPayment());
    }
}
