package samandsimons.adventure.aittenantfragmentdemo.fragment.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import samandsimons.adventure.aittenantfragmentdemo.R;
import samandsimons.adventure.aittenantfragmentdemo.model.Connection;
import samandsimons.adventure.aittenantfragmentdemo.model.Event;
import samandsimons.adventure.aittenantfragmentdemo.model.User;

/**
 * Created by samgrund on 12/10/16.
 */
public class AddEventDialogFragment extends DialogFragment {

    public static final String EVENT_NAME = "EVENT_NAME";
    public static final String EVENT_TIME = "EVENT_TIME";
    public static final String CONNECTION = "CONNECTION";

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_new_event, null);

        dialogBuilder.setView(view);
        dialogBuilder.setTitle(R.string.new_event_dialog_title);

        final HashMap<String, Connection> selectedConnections = new HashMap<String, Connection>();

        setupSpinner(view, selectedConnections);

        setupButtons(dialogBuilder, view, selectedConnections);

        return dialogBuilder.create();
    }

    private void setupButtons(AlertDialog.Builder dialogBuilder, View view, final HashMap<String, Connection> selectedConnections) {
        final DatePicker datePicker = (DatePicker) view.findViewById(R.id.eventDatePicker);
        final TimePicker timePicker = (TimePicker) view.findViewById(R.id.eventTimePicker);
        final EditText etName = (EditText) view.findViewById(R.id.etEventName);

        setupPositiveButton(dialogBuilder, selectedConnections, datePicker, timePicker, etName);
        setupNegativeButton(dialogBuilder);

    }

    private void setupNegativeButton(AlertDialog.Builder dialogBuilder) {
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
    }

    private void setupPositiveButton(AlertDialog.Builder dialogBuilder, final HashMap<String, Connection> selectedConnections, final DatePicker datePicker, final TimePicker timePicker, final EditText etName) {
        dialogBuilder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                int hour;
                int minute;
                if (Build.VERSION.SDK_INT >= 23) {
                    hour = timePicker.getHour();
                    minute = timePicker.getMinute();
                } else {
                    hour = timePicker.getCurrentHour();
                    minute = timePicker.getCurrentMinute();
                }
                Calendar date = new GregorianCalendar(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), hour, minute);
                String eventName = etName.getText().toString();

                Intent intent = new Intent();
                intent.putExtra(EVENT_NAME, eventName);
                intent.putExtra(EVENT_TIME, date.getTimeInMillis());
                intent.putExtra(CONNECTION, selectedConnections);

                getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
            }
        });
    }

    private void setupSpinner(View view, final HashMap<String, Connection> selectedConnections) {
        final TextView tvRecipients = (TextView) view.findViewById(R.id.tvEventRecipients);
        final Spinner recipients = (Spinner) view.findViewById(R.id.spEventRecipient);
        final ArrayList<Connection> connectionList = new ArrayList<>(User.getCurrentUser().getConfirmedConnections());
        connectionList.add(0, new Connection());
        final ArrayAdapter<Connection> arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, connectionList);
        recipients.setAdapter(arrayAdapter);
        recipients.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    return;
                }
                String participantString = tvRecipients.getText().toString();
                Connection selected = (Connection) recipients.getAdapter().getItem(position);
                if (!selectedConnections.containsKey(selected.getId())) {
                    selectedConnections.put(selected.getId(), selected);
                    tvRecipients.setText(participantString + selected.getDisplayName() + " ");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // do nothing
            }
        });
    }
}
