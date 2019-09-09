package com.lescepat.teacher.views.adapters.order;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

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