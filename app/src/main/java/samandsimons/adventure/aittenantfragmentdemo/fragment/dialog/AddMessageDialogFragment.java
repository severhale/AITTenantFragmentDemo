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
 * Created by Simon on 12/9/2016.
 */

public class AddMessageDialogFragment extends DialogFragment {
    public static final String CONNECTION = "CONNECTION";
    public static final String SUBJECT = "SUBJECT";
    public static final String BODY = "BODY";
    private Spinner spinner;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_new_message, null);

        alertDialogBuilder.setView(view);
        alertDialogBuilder.setTitle(R.string.new_message_dialog_title);

        setupSpinner(view);
        setupButtons(alertDialogBuilder, view);

        return alertDialogBuilder.create();
    }

    private void setupButtons(AlertDialog.Builder alertDialogBuilder, View view) {
        final EditText etSubject = (EditText) view.findViewById(R.id.etMessageSubject);
        final EditText etMessage = (EditText) view.findViewById(R.id.etMessageText);
        setupPositiveButton(alertDialogBuilder, etSubject, etMessage);
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

    private void setupPositiveButton(AlertDialog.Builder alertDialogBuilder, final EditText etSubject, final EditText etMessage) {
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
                Intent intent = new Intent();
                intent.putExtra(CONNECTION, selectedConnection);
                intent.putExtra(SUBJECT, etSubject.getText().toString());
                intent.putExtra(BODY, etMessage.getText().toString());
                getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
            }
        });
    }

    private void setupSpinner(View view) {
        List<Connection> possibleRecipients = User.getCurrentUser().getConfirmedConnections();
        spinner = (Spinner) view.findViewById(R.id.spMessageRecipient);
        if (Dashboard.hasFilterConnection()) {
            spinner.setVisibility(View.GONE);
        }
        else {
            spinner.setVisibility(View.VISIBLE);
            ArrayAdapter<Connection> adapter = new ArrayAdapter<Connection>(getContext(), android.R.layout.simple_spinner_dropdown_item, possibleRecipients);
            spinner.setAdapter(adapter);
        }
    }
}
