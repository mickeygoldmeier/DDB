package com.example.ddb.UI.AddParcelProcces;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.HashMap;
import java.util.LinkedList;

public class AddParcelAdapter extends FragmentPagerAdapter implements GetDataInterface {
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

    @Override
    public HashMap<String, Object> getData() {
        HashMap<String, Object> hashMap = new HashMap<>();
        for (Fragment fragment:fragments) {
            for (String key: ((GetDataInterface)fragment).getData().keySet())
            {
                hashMap.put(key, ((GetDataInterface)fragment).getData().get(key));
            }
        }
        return hashMap;
    }
}
