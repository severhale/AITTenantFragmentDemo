package samandsimons.adventure.aittenantfragmentdemo;

import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import samandsimons.adventure.aittenantfragmentdemo.adapter.DashboardPagerAdapter;

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

        tvStatus.setText("Logged in as: " + getUserEmail());

        pagerAdapter = new DashboardPagerAdapter(getSupportFragmentManager(), getApplicationContext());
        pager.setAdapter(pagerAdapter);
    }
}
