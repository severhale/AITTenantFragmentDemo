package samandsimons.adventure.aittenantfragmentdemo.adapter.pager;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import samandsimons.adventure.aittenantfragmentdemo.R;
import samandsimons.adventure.aittenantfragmentdemo.fragment.ConfirmedConnectionsFragment;
import samandsimons.adventure.aittenantfragmentdemo.fragment.PendingConnectionsFragment;

/**
 * Created by samgrund on 12/9/16.
 */
public class ConnectionsPagerAdapter extends FragmentPagerAdapter {

    private Context context;

    public ConnectionsPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return context.getString(R.string.connections);
            case 1:
                return context.getString(R.string.pending);
            default:
                return context.getString(R.string.connections);
        }
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new ConfirmedConnectionsFragment();
            case 1:
                return new PendingConnectionsFragment();
            default:
                return new ConfirmedConnectionsFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
