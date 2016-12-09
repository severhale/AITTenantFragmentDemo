package samandsimons.adventure.aittenantfragmentdemo.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import samandsimons.adventure.aittenantfragmentdemo.R;
import samandsimons.adventure.aittenantfragmentdemo.model.Connection;
import samandsimons.adventure.aittenantfragmentdemo.model.User;

/**
 * Created by samgrund on 12/9/16.
 */
public class AddConnectionDialogFragment extends DialogFragment {

    public static final String OUTGOING = "OUTGOING";
    public static final String INCOMING = "INCOMING";

    public AddConnectionDialogFragment() {

    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

        LayoutInflater customInflater = getActivity().getLayoutInflater();
        View customView = customInflater.inflate(R.layout.add_new_connection, null);

        final EditText etUserName = (EditText) customView.findViewById(R.id.etAddConnection);

        alertDialogBuilder.setView(customView);
        alertDialogBuilder.setTitle("Add new connection.");
        alertDialogBuilder.setPositiveButton("Connect", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String name = etUserName.getText().toString();
                if (name.equals("")) {
                    etUserName.setError("You must enter a valid username");
                } else {

                    FirebaseDatabase.getInstance().getReference().child("user").orderByChild("emailAddress").equalTo(name).
                            addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    User user = dataSnapshot.getValue(User.class);
                                    String toDisplayName = user.getEmail();
                                    String toId = dataSnapshot.getKey();

                                    String fromId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                    String fromDisplayName = FirebaseAuth.getInstance().getCurrentUser().getEmail();


                                    Connection outConnection = new Connection(toId, toDisplayName, Connection.State.OUTGOING.ordinal());
                                    Connection inConnection = new Connection(fromId, fromDisplayName, Connection.State.INCOMING.ordinal());

                                    Intent intent = new Intent();
                                    intent.putExtra(OUTGOING, outConnection);
                                    intent.putExtra(INCOMING, inConnection);
                                    getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
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
