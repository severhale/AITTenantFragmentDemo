package samandsimons.adventure.aittenantfragmentdemo;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import samandsimons.adventure.aittenantfragmentdemo.model.Payment;
import samandsimons.adventure.aittenantfragmentdemo.model.User;

import static samandsimons.adventure.aittenantfragmentdemo.model.Payment.states.INCOMING;
import static samandsimons.adventure.aittenantfragmentdemo.model.Payment.states.OUTGOING;

/**
 * Created by Simon on 12/11/2016.
 */

public class PaymentSummaryFragment extends DialogFragment {
    private static final long monthTimeDifference = 2678400000L; // a month in milliseconds :)
    private static final long yearTimeDifference = 31536000000L; // a year in milliseconds :)

    public PaymentSummaryFragment() {
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_payment_summary, null);

        TextView tvMonthSent = (TextView) view.findViewById(R.id.tvMonthSent);
        TextView tvMonthReceived = (TextView) view.findViewById(R.id.tvMonthReceived);
        TextView tvYearSent = (TextView) view.findViewById(R.id.tvYearSent);
        TextView tvYearReceived = (TextView) view.findViewById(R.id.tvYearReceived);

        float monthSent = 0;
        float monthReceived = 0;
        float yearSent = 0;
        float yearReceived = 0;

        long currTime = System.currentTimeMillis();

        for (Payment p : User.getCurrentUser().getPayments()) {
            if (p.isConfirmed()) {
                long timeDifference = currTime - p.getTime();
                Log.d("TAG", "TIME DIFFERENCE IS " + timeDifference);
                try {
                    if (p.getState() == INCOMING.ordinal()) {
                        if (timeDifference <= monthTimeDifference) {
                            monthReceived += Float.parseFloat(p.getAmount());
                        }
                        if (timeDifference <= yearTimeDifference) {
                            yearReceived += Float.parseFloat(p.getAmount());
                        }
                    } else if (p.getState() == OUTGOING.ordinal()) {
                        if (timeDifference <= monthTimeDifference) {
                            monthSent += Float.parseFloat(p.getAmount());
                        }
                        if (timeDifference <= yearTimeDifference) {
                            yearSent += Float.parseFloat(p.getAmount());
                        }
                    }
                }
                catch (NumberFormatException e) {
                    Log.w("TAG", e.getLocalizedMessage());
                }
            }
        }

        tvMonthReceived.setText(Float.toString(monthReceived));
        tvMonthSent.setText(Float.toString(monthSent));
        tvYearReceived.setText(Float.toString(yearReceived));
        tvYearSent.setText(Float.toString(yearSent));

        builder.setView(view);
        builder.setTitle(getString(R.string.payment_summary));

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        return builder.create();
    }
}
