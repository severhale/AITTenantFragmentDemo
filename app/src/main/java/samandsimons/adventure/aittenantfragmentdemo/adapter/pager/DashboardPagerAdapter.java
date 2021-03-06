package samandsimons.adventure.aittenantfragmentdemo.adapter.pager;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import samandsimons.adventure.aittenantfragmentdemo.R;
import samandsimons.adventure.aittenantfragmentdemo.fragment.dashboard.EventFragment;
import samandsimons.adventure.aittenantfragmentdemo.fragment.dashboard.PaymentFragment;
import samandsimons.adventure.aittenantfragmentdemo.fragment.dashboard.MessageFragment;

/**
 * Created by Simon on 12/4/2016.
 */

public class DashboardPagerAdapter extends FragmentPagerAdapter {

    private Context context;

    public DashboardPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;

    }

    public DashboardPagerAdapter(FragmentManager supportFragmentManager, Context applicationContext, String filterId) {
        super(supportFragmentManager);
        this.context = applicationContext;

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
                return new EventFragment();
            case 1:
                return new PaymentFragment();
            case 2:
                return new MessageFragment();
            default:
                return new EventFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

}
