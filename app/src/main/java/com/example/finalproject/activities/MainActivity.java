package com.example.finalproject.activities;
import android.animation.Animator;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
//import android.support.design.widget.Snackbar;
import com.example.finalproject.adapters.DayAdapter;
import com.example.finalproject.fragments.DayFragment;
import com.example.finalproject.fragments.SharedTransitionSet;
import com.google.android.material.snackbar.Snackbar;
//import android.support.design.widget.TabLayout;
//import android.support.v4.view.ViewPager;
//import android.support.v7.preference.PreferenceManager;
import androidx.preference.PreferenceManager;
import android.text.TextUtils;
import android.transition.Fade;
import android.util.Log;
import android.view.View;
//import android.support.design.widget.NavigationView;
import com.google.android.material.navigation.NavigationView;
//import android.support.v4.view.GravityCompat;
import androidx.core.view.GravityCompat;
//import android.support.v4.widget.DrawerLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
//import android.support.v7.app.ActionBarDrawerToggle;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;
//import android.support.v7.widget.RecyclerView;
//import android.support.v7.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;


import com.cleveroad.fanlayoutmanager.FanLayoutManager;
import com.cleveroad.fanlayoutmanager.FanLayoutManagerSettings;
import com.cleveroad.fanlayoutmanager.callbacks.FanChildDrawingOrderCallback;

import com.example.finalproject.R;

import static com.example.finalproject.Utils.BrowserUtil.openUrlInChromeCustomTab;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private FanLayoutManager mFanLayoutManager;
    private DayAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initAll();
    }

    private void initAll(){
        NavigationView navigationView = findViewById(R.id.nav_view);  // side menu
        navigationView.setNavigationItemSelectedListener(this);
        PreferenceManager.setDefaultValues(this, R.xml.settings, false);// for settings page
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);  // thre lines
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        setupDays();

    }

    private void setupDays(){
        final RecyclerView recyclerView = findViewById(R.id.dayCards);
        FanLayoutManagerSettings fanLayoutManagerSettings = FanLayoutManagerSettings
                .newBuilder(this)
                .withFanRadius(true)
                .withAngleItemBounce(5)
                .withViewHeightDp(300)
                .withViewWidthDp(300)
                .build();
        mFanLayoutManager = new FanLayoutManager(this, fanLayoutManagerSettings);
        recyclerView.setLayoutManager(mFanLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new DayAdapter(this);
        mAdapter.setOnItemClickListener(new DayAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, final View view) {
                Log.d("click","clk1");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    onClick(view, itemPosition);
                } else {
                    onClick(itemPosition);
                }




//                if (mFanLayoutManager.getSelectedItemPosition() != itemPosition) {
//                    mFanLayoutManager.switchItem(recyclerView, itemPosition);
//                } else {
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                        onClick(view, mFanLayoutManager.getSelectedItemPosition());
//                    } else {
//                        onClick(mFanLayoutManager.getSelectedItemPosition());
//                    }
//                    mFanLayoutManager.straightenSelectedItem(new Animator.AnimatorListener() {
//                        @Override
//                        public void onAnimationStart(Animator animator) {
//
//                        }
//
//                        @Override
//                        public void onAnimationEnd(Animator animator) {
//                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                                onClick(view, mFanLayoutManager.getSelectedItemPosition());
//                            } else {
//                                onClick(mFanLayoutManager.getSelectedItemPosition());
//                            }
//                        }
//
//                        @Override
//                        public void onAnimationCancel(Animator animator) {
//
//                        }
//
//                        @Override
//                        public void onAnimationRepeat(Animator animator) {
//
//                        }
//                    });
//                }
            }
        });
        recyclerView.setAdapter(mAdapter);

        recyclerView.setChildDrawingOrderCallback(new FanChildDrawingOrderCallback(mFanLayoutManager));


    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        final NavigationView navigationView = findViewById(R.id.nav_view);
        switch (item.getItemId()) {
            case R.id.homework:
                Intent homework = new Intent(MainActivity.this, HomeworksActivity.class);
                startActivity(homework);
                return true;
            case R.id.schoolwebsitemenu:
                String schoolWebsite = PreferenceManager.getDefaultSharedPreferences(this).getString(SettingsActivity.KEY_SCHOOL_WEBSITE_SETTING, null);
                if(!TextUtils.isEmpty(schoolWebsite)) {
                    openUrlInChromeCustomTab(getApplicationContext(), schoolWebsite);
                } else {
                    Snackbar.make(navigationView, R.string.school_website_snackbar, Snackbar.LENGTH_SHORT).show();
                }
                return true;
            case R.id.exams:
                Intent exams = new Intent(MainActivity.this, ExamsActivity.class);
                startActivity(exams);
                return true;
            case R.id.notes:
                Intent note = new Intent(MainActivity.this, NotesActivity.class);
                startActivity(note);
                return true;
            case R.id.settings:
                Intent settings = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(settings);
                return true;
            default:
                DrawerLayout drawer = findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
        }
    }




    @androidx.annotation.RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void onClick(View view, int pos) {
        DayFragment fragment = new DayFragment();
        Bundle args = new Bundle();
        args.putInt("day", pos);
        fragment.setArguments(args);
//        fragment.setSharedElementEnterTransition(new SharedTransitionSet());
//        fragment.setEnterTransition(new Fade());
//        fragment.setExitTransition(new Fade());
//        fragment.setSharedElementReturnTransition(new SharedTransitionSet());
        this.getSupportFragmentManager()
                .beginTransaction()
                .addSharedElement(view, "shared")
                .replace(R.id.drawer_layout, fragment)
                .addToBackStack(null)
                .commit();

    }

    public void onClick(int pos) {
        Log.d("click","clk4");
        DayFragment fragment = new DayFragment();
        Bundle args = new Bundle();
        args.putInt("index", pos);
        fragment.setArguments(args);
        this.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.drawer_layout, fragment)
                .addToBackStack(null)
                .commit();
    }

    public boolean deselectIfSelected() {
        if (mFanLayoutManager.isItemSelected()) {
            mFanLayoutManager.deselectItem();
            return true;
        } else {
            return false;
        }
    }

}
