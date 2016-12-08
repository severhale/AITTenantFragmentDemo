package samandsimons.adventure.aittenantfragmentdemo.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import samandsimons.adventure.aittenantfragmentdemo.BaseActivity;
import samandsimons.adventure.aittenantfragmentdemo.R;
import samandsimons.adventure.aittenantfragmentdemo.adapter.PaymentRecyclerAdapter;
import samandsimons.adventure.aittenantfragmentdemo.model.Payment;
import samandsimons.adventure.aittenantfragmentdemo.model.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class PaymentFragment extends Fragment implements DataFragment {

    PaymentRecyclerAdapter recyclerAdapter;

    public PaymentFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_payment, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.paymentRecycler);
        recyclerAdapter = new PaymentRecyclerAdapter();
        recyclerView.setAdapter(recyclerAdapter);
        return view;
    }

    @Override
    public void refreshData(User user) {
        recyclerAdapter.updateForUser(user);
    }
}
