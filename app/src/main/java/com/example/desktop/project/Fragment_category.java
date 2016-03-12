package com.example.desktop.project;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Fragment_category extends Fragment {

    private ViewPager mViewPager;
    private SectionsPagerAdapter mSectionsPagerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_category, null);
        Log.e("frag1", "onCreateView");
        mSectionsPagerAdapter = new SectionsPagerAdapter(getActivity().getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) root.findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOffscreenPageLimit(1);

        TabLayout tabLayout = (TabLayout) root.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        return root;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("frag1", "onDestroy");
    }

    @Override
    public void onStart() {
        Log.e("frag1", "onStart");
        super.onStart();
    }

    @Override
    public void onDetach() {
        Log.e("frag1", "onDetach");
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        Log.e("frag1", "onDestroyView");
        super.onDestroyView();
    }

    @Override
    public void onResume() {
        Log.e("frag1", " onResume");
        super.onResume();
    }

    @Override
    public void onAttach(Context context) {
        Log.e("frag1", "onAttach");
        super.onAttach(context);
    }

    @Override
    public void onPause() {
        Log.e("frag1", "onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.e("frag1", "onStop");
        super.onStop();
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 2) {
                return new Fragment_category_map();
            } else if (position == 1) {
                return new Fragment_category_eventList();
            } else {
                return new Fragment_category_create();
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Create Event";
                case 1:
                    return "Event List";
                case 2:
                    return "Map";
            }
            return null;
        }
    }

}
