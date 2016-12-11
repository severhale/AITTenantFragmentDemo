package samandsimons.adventure.aittenantfragmentdemo.fragment.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import samandsimons.adventure.aittenantfragmentdemo.ConnectionListInterface;
import samandsimons.adventure.aittenantfragmentdemo.LoginActivity;
import samandsimons.adventure.aittenantfragmentdemo.R;
import samandsimons.adventure.aittenantfragmentdemo.model.Connection;

/**
 * Created by samgrund on 12/9/16.
 */
public class AddConnectionDialogFragment extends DialogFragment {

    public static final String OUTGOING = "OUTGOING";
    public static final String INCOMING = "INCOMING";

    private ConnectionListInterface connectionListInterface = null;

    public AddConnectionDialogFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof ConnectionListInterface) {
            connectionListInterface = (ConnectionListInterface) context;
        } else {
            throw new RuntimeException("Activity is not implementing ConnectionListInterface.");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

        LayoutInflater customInflater = getActivity().getLayoutInflater();
        View customView = customInflater.inflate(R.layout.add_new_connection, null);

        final EditText etUserName = (EditText) customView.findViewById(R.id.etAddConnection);

        alertDialogBuilder.setView(customView);
        alertDialogBuilder.setTitle(R.string.new_connection_dialog_title);
        alertDialogBuilder.setPositiveButton("Connect", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                final String name = etUserName.getText().toString();
                if (name.equals("")) {
                    etUserName.setError(getString(R.string.invalid_connection_error));
                } else {
                    FirebaseDatabase.getInstance().getReference().child("emails").child(LoginActivity.encodeEmail(name)).
                            addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.getValue() == null) {
                                        return;
                                    }
                                    String toId = dataSnapshot.getValue(String.class);

                                    String fromId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                    String fromDisplayName = FirebaseAuth.getInstance().getCurrentUser().getEmail();

                                    Connection outConnection = new Connection(toId, name);
                                    Connection inConnection = new Connection(fromId, fromDisplayName);

                                    connectionListInterface.addNewConnection(outConnection, inConnection);
                                }
                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    etUserName.setError(databaseError.getMessage());
                                }
                            });
                }
            }
        });
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        
        return alertDialogBuilder.create();
    }
}
