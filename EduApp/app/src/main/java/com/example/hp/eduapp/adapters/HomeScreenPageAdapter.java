package com.example.hp.eduapp.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.hp.eduapp.fragments.CoursesFragment;
import com.example.hp.eduapp.fragments.NotificationsFragment;

/**
 * Created by radman on 9/15/2016.
 */
public class HomeScreenPageAdapter extends FragmentPagerAdapter {

    public HomeScreenPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new CoursesFragment();
            case 1:
                return new NotificationsFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

//    @Override
//    public CharSequence getPageTitle(int position) {
//        switch (position) {
//            case 0:
//                return "COURSES";
//            case 1:
//                return "GROUPS";
//            default:
//                return null;
//        }
//    }

}
