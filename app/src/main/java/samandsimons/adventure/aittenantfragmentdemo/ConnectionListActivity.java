package samandsimons.adventure.aittenantfragmentdemo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import samandsimons.adventure.aittenantfragmentdemo.adapter.pager.ConnectionsPagerAdapter;
import samandsimons.adventure.aittenantfragmentdemo.fragment.dialog.AddConnectionDialogFragment;
import samandsimons.adventure.aittenantfragmentdemo.model.Connection;

/**
 * Created by samgrund on 12/9/16.
 */
public class ConnectionListActivity extends BaseActivity implements ConnectionListInterface {

    public static final String CONNECTION_LIST_DIALOG = "CONNECTION_LIST_DIALOG";

    private ViewPager connectionsPager;
    private ConnectionsPagerAdapter connectionsPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection_list);

        connectionsPager = (ViewPager) findViewById(R.id.connectionsPager);

        connectionsPagerAdapter = new ConnectionsPagerAdapter(getSupportFragmentManager(), getApplicationContext());

        connectionsPager.setOffscreenPageLimit(4);
        connectionsPager.setAdapter(connectionsPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayoutConnections);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.contacts));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.pending));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.requested));
        connectionsPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                connectionsPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabNewConnection);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddConnectionDialogFragment dialogFragment = new AddConnectionDialogFragment();
                dialogFragment.show(getSupportFragmentManager().beginTransaction(), CONNECTION_LIST_DIALOG);
            }
        });

    }

    @Override
    public void addNewConnection(Connection out, Connection in) {
        DatabaseReference usersReference = FirebaseDatabase.getInstance().getReference().child("users");
        DatabaseReference postToCurrentUser = usersReference.child(in.getId()).child("connections").child("outgoing").child(out.getId());
        postToCurrentUser.setValue(out);

        DatabaseReference postToOtherUser = usersReference.child(out.getId()).child("connections").child("incoming").child(in.getId());
        postToOtherUser.setValue(in);
    }
}
