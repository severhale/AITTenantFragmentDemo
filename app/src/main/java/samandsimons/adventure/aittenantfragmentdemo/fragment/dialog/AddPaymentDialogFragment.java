package samandsimons.adventure.aittenantfragmentdemo.fragment.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.List;

import samandsimons.adventure.aittenantfragmentdemo.Dashboard;
import samandsimons.adventure.aittenantfragmentdemo.R;
import samandsimons.adventure.aittenantfragmentdemo.model.Connection;
import samandsimons.adventure.aittenantfragmentdemo.model.User;

/**
 * Created by samgrund on 12/10/16.
 */
public class AddPaymentDialogFragment extends DialogFragment {

    public static final String CONNECTION = "CONNECTION";
    public static final String NAME = "NAME";
    public static final String AMOUNT = "AMOUNT";
    private Spinner spinner;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_new_payment, null);

        alertDialogBuilder.setView(view);
        alertDialogBuilder.setTitle(R.string.new_payment_dialog_title);

        setupSpinner(view);
        setupButtons(alertDialogBuilder, view);


        return alertDialogBuilder.create();
    }

    private void setupSpinner(View view) {
        spinner = (Spinner) view.findViewById(R.id.spPaymentRecipient);
        if (Dashboard.hasFilterConnection()) {
            spinner.setVisibility(View.GONE);
        }
        else {
            spinner.setVisibility(View.VISIBLE);
            List<Connection> possibleRecipients = User.getCurrentUser().getConfirmedConnections();
            ArrayAdapter<Connection> adapter = new ArrayAdapter<Connection>(getContext(), android.R.layout.simple_spinner_dropdown_item, possibleRecipients);
            spinner.setAdapter(adapter);
        }
    }

    private void setupButtons(AlertDialog.Builder alertDialogBuilder, View view) {
        final EditText etName = (EditText) view.findViewById(R.id.etPaymentName);
        final EditText etAmount = (EditText) view.findViewById(R.id.etPaymentAmount);
        setupPositiveButton(alertDialogBuilder, etName, etAmount);
        setupNegativeButton(alertDialogBuilder);
    }

    private void setupNegativeButton(AlertDialog.Builder alertDialogBuilder) {
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
    }

    private void setupPositiveButton(AlertDialog.Builder alertDialogBuilder, final EditText etName, final EditText etAmount) {
        alertDialogBuilder.setPositiveButton("Send", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Connection selectedConnection;
                if (Dashboard.hasFilterConnection()) {
                    selectedConnection = Dashboard.getFilterConnection();
                }
                else {
                    selectedConnection = (Connection) spinner.getSelectedItem();
                }
                if (selectedConnection == null) {
                    selectedConnection = new Connection("placeholderid", "placeholder subject");
                }
                String amount = etAmount.getText().toString();

                Intent intent = new Intent();
                intent.putExtra(CONNECTION, selectedConnection);
                intent.putExtra(NAME, etName.getText().toString());
                intent.putExtra(AMOUNT, amount.equals("") ? "0" : amount);
                getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
            }
        });
    }

}
