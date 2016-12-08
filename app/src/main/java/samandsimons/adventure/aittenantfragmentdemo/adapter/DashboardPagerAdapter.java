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

    public DashboardPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
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
                return new PostItFragment();
            default:
                return new EventFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

}
