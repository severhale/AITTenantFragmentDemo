package samandsimons.adventure.aittenantfragmentdemo;

import android.support.v4.app.Fragment;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import samandsimons.adventure.aittenantfragmentdemo.adapter.DashboardPagerAdapter;
import samandsimons.adventure.aittenantfragmentdemo.fragment.EventFragment;
import samandsimons.adventure.aittenantfragmentdemo.fragment.PaymentFragment;
import samandsimons.adventure.aittenantfragmentdemo.fragment.PostItFragment;
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

        FirebaseDatabase.getInstance().getReference().child("users").child(getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                setUser(user);
                refreshFragments(user);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(Dashboard.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        tvStatus.setText("Logged in as: " + getUserEmail());

        pagerAdapter = new DashboardPagerAdapter(getSupportFragmentManager(), getApplicationContext());
        pager.setOffscreenPageLimit(3);
        pager.setAdapter(pagerAdapter);
    }


    private void refreshFragments(User user) {
        EventFragment eventFragment = (EventFragment) findFragmentByTag(0);
        eventFragment.refreshData(user);


        PaymentFragment paymentFragment = (PaymentFragment) findFragmentByTag(1);
        paymentFragment.refreshData(user);

        PostItFragment postItFragment = (PostItFragment) findFragmentByTag(2);
        postItFragment.refreshData(user);
    }

    private Fragment findFragmentByTag(int page) {
        return getSupportFragmentManager().findFragmentByTag(
                "android:switcher:" + R.id.dashboardPager + ":" + pagerAdapter.getItemId(page)
        );
    }
}
