package com.king.girl;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.king.base.BaseActivity;
import com.king.base.adapter.ViewPagerFragmentAdapter;
import com.king.base.model.EventMessage;
import com.king.base.util.SystemUtils;
import com.king.girl.fragment.GirlsFragment;
import com.king.widget.SuperSlidingPaneLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private TextView tvVersion;

    private SuperSlidingPaneLayout superSlidingPaneLayout;

    private Toolbar toolbar;

    private TabLayout tabLayout;

    private ViewPager viewPager;

    private FloatingActionButton fab;

    private ViewPagerFragmentAdapter viewPagerFragmentAdapter;

    private List<Fragment> listData;

    private List<CharSequence> listTabs;

    private OnScrollListener onScrollListener;

    public interface OnScrollListener {
         void onScroll();
    }


    @Override
    public void initUI() {

        setContentView(R.layout.activity_main);

        tvVersion = findView(R.id.tvVersion);

        tvVersion.setText(String.format("V%s", SystemUtils.getVersionName(context)));

        superSlidingPaneLayout = findView(R.id.superSlidingPaneLayout);

        toolbar = findView(R.id.toolBar);
        setSupportActionBar(toolbar);
        tabLayout = findView(R.id.tabLayout);

        viewPager = findView(R.id.viewPager);

        fab = findView(R.id.fab);

    }

    @Override
    public void addListeners() {
        fab.setOnClickListener(this);


    }



    @Override
    public void initData() {


        listData = new ArrayList<>();

        listTabs = new ArrayList<>();

        for (int i = 0; i < 7; i++) {
            listTabs.add("Girls"+(i+1));
            switch (i){
                case 0:
                    listData.add(GirlsFragment.newInstance(GirlsFragment.LayoutType.StaggeredGridLayout));
                    onScrollListener = (OnScrollListener) listData.get(0);
                    break;
                case 1:
                    listData.add(GirlsFragment.newInstance(GirlsFragment.LayoutType.GridLayout));
                    break;
                case 2:
                    listData.add(GirlsFragment.newInstance(GirlsFragment.LayoutType.LinearLayout));
                    break;
                default:
                    listData.add(GirlsFragment.newInstance(GirlsFragment.LayoutType.StaggeredGridLayout));
                    break;
            }

        }

        viewPagerFragmentAdapter = new ViewPagerFragmentAdapter(getSupportFragmentManager(),listData,listTabs);
        viewPager.setAdapter(viewPagerFragmentAdapter);

        tabLayout.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                if(listData.get(position) instanceof  OnScrollListener){
                    onScrollListener = (OnScrollListener) listData.get(position);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public void onEventMessage(EventMessage em) {

    }


    private void clickFab(){

        if(onScrollListener!=null){
            onScrollListener.onScroll();
        }

//        Uri uri = Uri.parse("mailto:jenly1314@gmail.com");
//        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
//        startActivity(Intent.createChooser(intent, "Choose Email Client"));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fab:
                clickFab();
                break;
        }
    }
}
