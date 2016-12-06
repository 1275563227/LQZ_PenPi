package gdin.com.penpi.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2016/11/30.
 */
public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {

    private Context context;
    private List<Fragment> mFragments;
    private static final String[] mTitles = {"发单记录", "抢单记录"};

    public SimpleFragmentPagerAdapter(FragmentManager fm,  List<Fragment> fragments) {
        super(fm);
        this.mFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}