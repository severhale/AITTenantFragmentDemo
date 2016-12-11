package samandsimons.adventure.aittenantfragmentdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.view.MenuInflater;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import samandsimons.adventure.aittenantfragmentdemo.adapter.pager.DashboardPagerAdapter;
import samandsimons.adventure.aittenantfragmentdemo.fragment.dashboard.CreateDialogInterface;
import samandsimons.adventure.aittenantfragmentdemo.model.Connection;
import samandsimons.adventure.aittenantfragmentdemo.model.User;

public class Dashboard extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String FILTER_CONNECTION_EXTRA = "FILTER_CONNECTION_EXTRA";
    public static final String PAYMENT_SUMMARY = "PAYMENT_SUMMARY";
    private static Connection FILTER_CONNECTION;

    @BindView(R.id.dashboardPager)
    ViewPager pager;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

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

        setContentView(R.layout.activity_drawer_dashboard);

        ButterKnife.bind(this);

        if (getIntent().hasExtra(FILTER_CONNECTION_EXTRA)) {
            FILTER_CONNECTION = (Connection) getIntent().getSerializableExtra(FILTER_CONNECTION_EXTRA);
            setTitle(FILTER_CONNECTION.getDisplayName());
        }
        else {
            FILTER_CONNECTION = null;
            if (!FirebaseListener.isStarted()) {
                FirebaseListener.startAllListeners();
            }
            if (!EventBus.getDefault().isRegistered(User.getCurrentUser())) {
                User.getCurrentUser().initializeData();
                EventBus.getDefault().register(User.getCurrentUser());
            }
            setTitle(getString(R.string.dashboard));
        }

        Toolbar toolbar = setupToolbar();
        NavigationView navigationView = setupNavDrawer(toolbar);

        TextView tvEmail = (TextView) navigationView.getHeaderView(0).findViewById(R.id.tvEmail);
        tvEmail.setText(getUserEmail());

        setupPager();
    }

    private void setupPager() {
        pagerAdapter = new DashboardPagerAdapter(getSupportFragmentManager(), getApplicationContext());
        pager.setOffscreenPageLimit(3);
        pager.setAdapter(pagerAdapter);
        tabLayout.addTab(tabLayout.newTab().setText("Events"));
        tabLayout.addTab(tabLayout.newTab().setText("Payments"));
        tabLayout.addTab(tabLayout.newTab().setText("Messages"));
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private Toolbar setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.appBarToolbar);
        setSupportActionBar(toolbar);
        return toolbar;
    }

    @NonNull
    private NavigationView setupNavDrawer(Toolbar toolbar) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        return navigationView;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!getIntent().hasExtra(FILTER_CONNECTION_EXTRA)) {
            EventBus.getDefault().unregister(User.getCurrentUser());
            FirebaseListener.stopAllListeners();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        if (hasFilterConnection()) {
            inflater.inflate(R.menu.menu_limited, menu);
        }
        else {
            inflater.inflate(R.menu.menu_main, menu);
        }
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
                return true;
            default:
                return true;
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_logout) {
            FirebaseAuth.getInstance().signOut();
            EventBus.getDefault().unregister(User.getCurrentUser());
            User.getCurrentUser().initializeData();
            FirebaseListener.stopAllListeners();
            startActivity(new Intent(Dashboard.this, LoginActivity.class));
            finish();
        }
        else if (id == R.id.nav_about) {
            Toast.makeText(this, R.string.about_message, Toast.LENGTH_LONG).show();
        }
        else if (id == R.id.nav_summary) {
            new PaymentSummaryFragment().show(getSupportFragmentManager(), PAYMENT_SUMMARY);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private Fragment findFragmentByTag(int page) {
        return getSupportFragmentManager().findFragmentByTag(
                "android:switcher:" + R.id.dashboardPager + ":" + pagerAdapter.getItemId(page)
        );
    }
}
