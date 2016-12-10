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

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import samandsimons.adventure.aittenantfragmentdemo.adapter.pager.DashboardPagerAdapter;
import samandsimons.adventure.aittenantfragmentdemo.fragment.dashboard.EventFragment;
import samandsimons.adventure.aittenantfragmentdemo.fragment.dashboard.PaymentFragment;
import samandsimons.adventure.aittenantfragmentdemo.fragment.dashboard.MessageFragment;
import samandsimons.adventure.aittenantfragmentdemo.model.Event;
import samandsimons.adventure.aittenantfragmentdemo.model.User;

public class Dashboard extends BaseActivity {

    public static final String FILTER_ID = "FILTER_ID";
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

        EventBus.getDefault().register(User.getCurrentUser());
        FirebaseListener.startAllListeners();

        tvStatus.setText("Logged in as: " + getUserEmail());

        pagerAdapter = new DashboardPagerAdapter(getSupportFragmentManager(), getApplicationContext());
        pager.setOffscreenPageLimit(3);
        pager.setAdapter(pagerAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(User.getCurrentUser());
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

    private Fragment findFragmentByTag(int page) {
        return getSupportFragmentManager().findFragmentByTag(
                "android:switcher:" + R.id.dashboardPager + ":" + pagerAdapter.getItemId(page)
        );
    }
}
