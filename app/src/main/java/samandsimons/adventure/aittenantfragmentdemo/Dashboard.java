package samandsimons.adventure.aittenantfragmentdemo;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import samandsimons.adventure.aittenantfragmentdemo.adapter.pager.DashboardPagerAdapter;
import samandsimons.adventure.aittenantfragmentdemo.fragment.dashboard.EventFragment;
import samandsimons.adventure.aittenantfragmentdemo.fragment.dashboard.PaymentFragment;
import samandsimons.adventure.aittenantfragmentdemo.fragment.dashboard.MessageFragment;
import samandsimons.adventure.aittenantfragmentdemo.model.User;

public class Dashboard extends BaseActivity {

    @BindView(R.id.tvStatus)
    TextView tvStatus;
    @BindView(R.id.dashboardPager)
    ViewPager pager;
    @BindView(R.id.dashboardTabs)
    PagerTabStrip dashboardTabs;

    private DashboardPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        ButterKnife.bind(this);

//        FirebaseDatabase.getInstance().getReference().child("users").child(getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                GenericTypeIndicator<User> t = new GenericTypeIndicator<User>() {};
//                User user = dataSnapshot.getValue(t);
//                setUser(user);
//                refreshFragments(user);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Toast.makeText(Dashboard.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });

        tvStatus.setText("Logged in as: " + getUserEmail());

        pagerAdapter = new DashboardPagerAdapter(getSupportFragmentManager(), getApplicationContext());
        pager.setOffscreenPageLimit(3);
        pager.setAdapter(pagerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_open_userlist:
                Intent intentShowUserList = new Intent();
                intentShowUserList.setClass(Dashboard.this, ConnectionListActivity.class);
                startActivityForResult(intentShowUserList, 101);
                return true;
            default:
                return true;
        }
    }

    private void refreshFragments(User user) {
        EventFragment eventFragment = (EventFragment) findFragmentByTag(0);
        eventFragment.refreshData(user);


        PaymentFragment paymentFragment = (PaymentFragment) findFragmentByTag(1);
        paymentFragment.refreshData(user);

        MessageFragment messageFragment = (MessageFragment) findFragmentByTag(2);
        messageFragment.refreshData(user);
    }

    private Fragment findFragmentByTag(int page) {
        return getSupportFragmentManager().findFragmentByTag(
                "android:switcher:" + R.id.dashboardPager + ":" + pagerAdapter.getItemId(page)
        );
    }
}
