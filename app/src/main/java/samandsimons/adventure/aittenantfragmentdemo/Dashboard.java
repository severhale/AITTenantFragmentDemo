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
import samandsimons.adventure.aittenantfragmentdemo.fragment.dashboard.CreateDialogInterface;
import samandsimons.adventure.aittenantfragmentdemo.fragment.dashboard.MessageFragment;
import samandsimons.adventure.aittenantfragmentdemo.model.Connection;
import samandsimons.adventure.aittenantfragmentdemo.model.User;

public class Dashboard extends BaseActivity {

    public static final String FILTER_CONNECTION_EXTRA = "FILTER_CONNECTION_EXTRA";
    private static Connection FILTER_CONNECTION;

    @BindView(R.id.dashboardPager)
    ViewPager pager;
    @BindView(R.id.dashboardTabs)
    PagerTabStrip dashboardTabs;

    private DashboardPagerAdapter pagerAdapter;

    public static Connection getFilterConnection() {
        return FILTER_CONNECTION;
    }

    public static String getFilterId() {
        return FILTER_CONNECTION.getId();
    }

    public static boolean hasFilterConnection() {
        return FILTER_CONNECTION != null;
    }

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (!FirebaseListener.isStarted()) {
            FirebaseListener.startAllListeners();
        }
        if (!EventBus.getDefault().isRegistered(User.getCurrentUser())) {
            EventBus.getDefault().register(User.getCurrentUser());
        }
        setContentView(R.layout.activity_dashboard);

        ButterKnife.bind(this);
        if (getIntent().hasExtra(FILTER_CONNECTION_EXTRA)) {
            FILTER_CONNECTION = (Connection) getIntent().getSerializableExtra(FILTER_CONNECTION_EXTRA);
            setTitle(FILTER_CONNECTION.getDisplayName());
        }
        else {
            FILTER_CONNECTION = null;
            setTitle(getString(R.string.dashboard));
        }

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
        if (hasFilterConnection()) {
            return false;
        }
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
            case R.id.action_open_dialog:
                int page = pager.getCurrentItem();
                CreateDialogInterface fragment = (CreateDialogInterface) findFragmentByTag(page);
                fragment.openDialog();
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
