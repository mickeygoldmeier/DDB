package com.example.ddb.UI.AddParcelProcces;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.LinkedList;

public class AddParcelAdapter extends FragmentPagerAdapter {
    private static LinkedList<Fragment> fragments = new LinkedList<>();
    static {
        fragments.add(new AddParcelFragment1());
        fragments.add(new AddParcelFragment2());
        fragments.add(new AddParcelFragment3());
    }

    public AddParcelAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
