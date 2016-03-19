package com.example.jason.helloworld.fragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.example.jason.helloworld.activities.MainActivity;

/**
 * Created by Jay on 2015/8/31 0031.
 */
public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    private final int PAGER_COUNT = 4;
    private LatestTvShowsFragment latestTvShowsFragment = null;
    private SquareFragment squareFragment = null;
    private CollectionFragment collectionFragment = null;
    private PersonalFragment personalFragment = null;


    public MyFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        latestTvShowsFragment = new LatestTvShowsFragment();
        squareFragment = new SquareFragment();
        collectionFragment = new CollectionFragment();
        personalFragment = new PersonalFragment();
    }


    @Override
    public int getCount() {
        return PAGER_COUNT;
    }

    @Override
    public Object instantiateItem(ViewGroup vg, int position) {
        return super.instantiateItem(vg, position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        System.out.println("position Destory" + position);
        super.destroyItem(container, position, object);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case MainActivity.PAGE_ONE:
                fragment = latestTvShowsFragment;
                break;
            case MainActivity.PAGE_TWO:
                fragment = squareFragment;
                break;
            case MainActivity.PAGE_THREE:
                fragment = collectionFragment;
                break;
            case MainActivity.PAGE_FOUR:
                fragment = personalFragment;
                break;
        }
        return fragment;
    }


}

