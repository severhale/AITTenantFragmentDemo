package samandsimons.adventure.aittenantfragmentdemo.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import samandsimons.adventure.aittenantfragmentdemo.R;
import samandsimons.adventure.aittenantfragmentdemo.adapter.ConnectionsPagerAdapter;
import samandsimons.adventure.aittenantfragmentdemo.model.Connection;
import samandsimons.adventure.aittenantfragmentdemo.model.Message;

/**
 * Created by samgrund on 12/9/16.
 */
public class ConnectionListDialogFragment extends DialogFragment {

    public static final int CONNECTION_REQUEST = -1;
    public static final String CONNECTION_LIST_DIALOG = "CONNECTION_LIST_DIALOG";

    private ViewPager connectionsPager;
    private ConnectionsPagerAdapter connectionsPagerAdapter;


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

        LayoutInflater customInflater = getActivity().getLayoutInflater();
        View customView = customInflater.inflate(R.layout.connection_list_fragment, null);

        // necessary?
        connectionsPager = (ViewPager) customView.findViewById(R.id.connectionsPager);

        connectionsPagerAdapter = new ConnectionsPagerAdapter(getFragmentManager(), getContext());

        connectionsPager.setAdapter(connectionsPagerAdapter);

        alertDialogBuilder.setView(customView);

        alertDialogBuilder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) customView.findViewById(R.id.fabNewMessage);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddMessageDialogFragment dialogFragment = new AddMessageDialogFragment();
                dialogFragment.setTargetFragment(ConnectionListDialogFragment.this, CONNECTION_REQUEST);
                dialogFragment.show(getFragmentManager().beginTransaction(), CONNECTION_LIST_DIALOG);
            }
        });


        return alertDialogBuilder.create();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CONNECTION_REQUEST:
                if (resultCode == Activity.RESULT_OK) {

                    Connection outConnection = (Connection) data.getSerializableExtra(AddConnectionDialogFragment.OUTGOING);
                    Connection inConnection = (Connection) data.getSerializableExtra(AddConnectionDialogFragment.INCOMING);

                    postConnection(outConnection, inConnection);

                }
                else if (resultCode == Activity.RESULT_CANCELED) {
                    // probably don't do anything
                }
                break;
        }
    }

    public void postConnection(Connection out, Connection in) {
        DatabaseReference usersReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference postToCurrentUser = usersReference.child(in.getId()).child("connections").push();
        postToCurrentUser.setValue(out);

        DatabaseReference postToOtherUser = usersReference.child(out.getId()).child("connections").push();
        postToOtherUser.setValue(in);
    }
}
