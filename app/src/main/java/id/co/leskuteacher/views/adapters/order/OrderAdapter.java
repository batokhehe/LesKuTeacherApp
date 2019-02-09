package id.co.leskuteacher.views.adapters.order;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class OrderAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragments;

    public OrderAdapter(FragmentManager fm) {
        super(fm);
        mFragments = new ArrayList<>();
    }

    public OrderAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        mFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String[] titleList = {"Menunggu", "Kelas Mendatang", "Selesai"};
        // TODO: implement your own page title.
//        return mFragments.get(position).getClass().getSimpleName();
        return titleList[position];
    }
}