package samandsimons.adventure.aittenantfragmentdemo.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import samandsimons.adventure.aittenantfragmentdemo.BaseActivity;
import samandsimons.adventure.aittenantfragmentdemo.R;
import samandsimons.adventure.aittenantfragmentdemo.model.Payment;
import samandsimons.adventure.aittenantfragmentdemo.model.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class PaymentFragment extends Fragment implements DataFragment {

    private BaseActivity baseActivity;
    private DatabaseReference database;
    List<Payment> paymentList;

    public PaymentFragment() {
        // Required empty public constructor
        if (!(getActivity() instanceof BaseActivity)) {
            throw new RuntimeException("Activity needs to extend BaseActivity");
        }

        baseActivity = (BaseActivity) getActivity();
        database = FirebaseDatabase.getInstance().getReference();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_payment, container, false);
    }

    @Override
    public void refreshData(User user) {

    }
}
