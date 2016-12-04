package samandsimons.adventure.aittenantfragmentdemo.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import samandsimons.adventure.aittenantfragmentdemo.R;
import samandsimons.adventure.aittenantfragmentdemo.fragment.EventFragment;
import samandsimons.adventure.aittenantfragmentdemo.fragment.PaymentFragment;
import samandsimons.adventure.aittenantfragmentdemo.fragment.PostItFragment;
import samandsimons.adventure.aittenantfragmentdemo.model.User;

/**
 * Created by Simon on 12/4/2016.
 */

public class DashboardPagerAdapter extends FragmentPagerAdapter {

    private Context context;
    private EventFragment eventFragment;
    private PaymentFragment paymentFragment;
    private PostItFragment postItFragment;

    public DashboardPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
        eventFragment = new EventFragment();
        paymentFragment = new PaymentFragment();
        postItFragment = new PostItFragment();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return context.getString(R.string.events);
            case 1:
                return context.getString(R.string.payments);
            case 2:
                return context.getString(R.string.messages);
            default:
                return context.getString(R.string.events);
        }
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return eventFragment;
            case 1:
                return paymentFragment;
            case 2:
                return postItFragment;
            default:
                return eventFragment;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    public void onDataLoaded(User user) {
        eventFragment.refreshData(user);
        paymentFragment.refreshData(user);
        postItFragment.refreshData(user);
    }
}
