package com.kaymkassai.learnit;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class HskFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

//
//        BottomNavigationView bottomNavigationView = getView().findViewById(R.id.bottom_navigation);
//        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
//
//        //I added this if statement to keep the selected fragment when rotating the device
//        if (savedInstanceState == null) {
//            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                    new HskFragment()).commit();
//        }


        return inflater.inflate(R.layout.fragment_hsk, container, false);
    }


//    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
//            new BottomNavigationView.OnNavigationItemSelectedListener() {
//                @Override
//                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                    Fragment selectedFragment = null;
//
//                    switch (item.getItemId()) {
//                        case R.id.nav_hsklist:
//                            selectedFragment = new WordListFragment();
//                            break;
//                        case R.id.nav_favorites:
//                            selectedFragment = new FavoriteFragment();
//                            break;
//                        case R.id.nav_search:
//                            selectedFragment = new SearchFragment();
//                            break;
//                    }
//                    return true;
//                }
//            };

}
