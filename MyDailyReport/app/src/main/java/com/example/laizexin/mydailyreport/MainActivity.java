package com.example.laizexin.mydailyreport;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.astuetz.PagerSlidingTabStrip;

import fragment.DbFragment;
import fragment.ZhFragment;
import utils.ButtonAnimatiorUtils;

public class MainActivity extends AppCompatActivity {

    private PagerSlidingTabStrip pt;
    private ViewPager pager;

    FloatingActionButton fab_all;
    FloatingActionButton fab_movie;
    FloatingActionButton fab_game;
    FloatingActionButton fab_sports;
    FloatingActionButton fab_home;

    private boolean flag = true;
    private MyAdapter adapter;

    private int theme_id = 0;

    private String theme_name;

    private String[] titles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab_all = (FloatingActionButton) findViewById(R.id.fab_all);
        fab_movie = (FloatingActionButton) findViewById(R.id.fab_movie);
        fab_game = (FloatingActionButton) findViewById(R.id.fab_game);
        fab_sports = (FloatingActionButton) findViewById(R.id.fab_sports);
        fab_home = (FloatingActionButton) findViewById(R.id.fab_home);

        setFloatingActionButton();

        theme_name = getString(R.string.zh_daily);

        titles = new String[]{theme_name, getString(R.string.unKnow)};

        pt = (PagerSlidingTabStrip) findViewById(R.id.pt);
        pager = (ViewPager) findViewById(R.id.pager);

        adapter = new MyAdapter(getSupportFragmentManager());

        pager.setAdapter(adapter);
        pt.setViewPager(pager);
    }


    private void setFloatingActionButton() {
        fab_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ButtonAnimatiorUtils.BindButtonAnimatior(MainActivity.this.getWindowManager(),fab_movie,8);
                ButtonAnimatiorUtils.BindButtonAnimatior(MainActivity.this.getWindowManager(),fab_game,4);
                ButtonAnimatiorUtils.BindButtonAnimatior(MainActivity.this.getWindowManager(),fab_sports,2.7f);
                ButtonAnimatiorUtils.BindButtonAnimatior(MainActivity.this.getWindowManager(),fab_home,2);
            }
        });

        fab_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(theme_id == 0)
                    return;
                theme_name = getString(R.string.zh_daily);
                theme_id = 0;
                notifyFragmentChange();
            }
        });

        fab_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "游戏专栏", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                if(theme_id == 2)
                    return;
                theme_name = getString(R.string.game);
                theme_id = 2;
                notifyFragmentChange();
                //https://news-at.zhihu.com/api/4/theme/2
            }
        });

        fab_movie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "电影专栏", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                if(theme_id == 3)
                    return;
                theme_name = getString(R.string.movie);
                theme_id = 3;
                notifyFragmentChange();
            }
        });

        fab_sports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "体育专栏", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                if(theme_id == 8)
                    return;
                theme_name = getString(R.string.sports);
                theme_id = 8;
                notifyFragmentChange();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class MyAdapter extends FragmentStatePagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: {
                    return ZhFragment.newInstance(theme_id);
                }
                case 1: {
                    return DbFragment.newInstance(position);
                }
            }
            return null;
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }

    private void notifyFragmentChange(){
        titles = new String[]{theme_name, getString(R.string.unKnow)};
        adapter.notifyDataSetChanged();
        pager.setAdapter(adapter);
    }
}
