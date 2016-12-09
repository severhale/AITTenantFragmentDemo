package samandsimons.adventure.aittenantfragmentdemo.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import samandsimons.adventure.aittenantfragmentdemo.R;
import samandsimons.adventure.aittenantfragmentdemo.model.Connection;

/**
 * Created by Simon on 12/9/2016.
 */

public class AddMessageDialogFragment extends DialogFragment {
    public static final String CONNECTION = "CONNECTION";
    public static final String SUBJECT = "SUBJECT";
    public static final String BODY = "BODY";
    public static final String BUNDLE = "BUNDLE";
    private Spinner spinner;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_new_message, null);

        final EditText etSubject = (EditText) view.findViewById(R.id.etMessageSubject);
        final EditText etMessage = (EditText) view.findViewById(R.id.etMessageText);

        alertDialogBuilder.setView(view);
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialogBuilder.setPositiveButton("Send", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Connection selectedConnection = (Connection) spinner.getSelectedItem();
                if (selectedConnection == null) {
                    selectedConnection = new Connection(true, "placeholderid", "placeholder name");
                    Log.d("TAG", "Connection was null");
                }
                Intent intent = new Intent();
                intent.putExtra(CONNECTION, selectedConnection);
                intent.putExtra(SUBJECT, etSubject.getText().toString());
                intent.putExtra(BODY, etMessage.getText().toString());
                getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
            }
        });
        List<Connection> possibleRecipients = new ArrayList<>();
        spinner = (Spinner) view.findViewById(R.id.spMessageRecipient);
        ArrayAdapter<Connection> adapter = new ArrayAdapter<Connection>(getContext(), android.R.layout.simple_spinner_dropdown_item, possibleRecipients);
        spinner.setAdapter(adapter);

        return alertDialogBuilder.create();
    }
}
