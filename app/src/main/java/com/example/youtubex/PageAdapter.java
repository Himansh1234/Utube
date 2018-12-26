package com.example.youtubex;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class PageAdapter extends FragmentStatePagerAdapter {

    int tabno;

    public PageAdapter (FragmentManager fm, int tabno)
    {
        super(fm);
        this.tabno=tabno;
    }
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                Views views = new Views();
                return views;

            case 1:
                Likes likes = new Likes();
                return likes;

            case 2:
                Suscribes suscribes = new Suscribes();
                return suscribes;

            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return tabno;
    }
}
