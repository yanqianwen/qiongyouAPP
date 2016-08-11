package com.qianfeng.qiongyou;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.qianfeng.qiongyou.Activity.MyBaseActivity;
import com.qianfeng.qiongyou.Fragment.DestinationFragment;
import com.qianfeng.qiongyou.Fragment.FirstFragment;


import com.qianfeng.qiongyou.Fragment.WanleFragment;
import com.qianfeng.qiongyou.Fragment.WodeFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends MyBaseActivity {
    List<Fragment> fragmnets = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_main);
        initToolbar();
        initFragment();
        initTablayout();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setToolbar(toolbar);
        HideTitleText();
    }


    private void initFragment() {
       fragmnets.add(FirstFragment.newInstance());
        fragmnets.add(DestinationFragment.newInstance("a", "b"));
        fragmnets.add(WanleFragment.newInstance("http://open.qyer.com/lastminute/page/local_leisure?client_id=qyer_discount_androi&client_secret=227097da1d07a2a9860f&track_user_id=&track_deviceid=000000000000000&track_app_version=1.9.3&track_app_channel=baidu&track_device_info=vbox86p&track_os=Android6.0&app_installtime=1457923891193&size=1080x1794&ra_referer=app_home&city_id="));
        fragmnets.add(WodeFragment.newInstance("a", "b"));
        for (int i = 0; i < fragmnets.size(); i++) {
            getFragmentTransaction().add(R.id.framelayout, fragmnets.get(i)).commit();
            if (i == 0) {
                getFragmentTransaction().show(fragmnets.get(i)).commit();
            } else {
                getFragmentTransaction().hide(fragmnets.get(i)).commit();
            }
        }
    }

    private void initTablayout() {
        TabLayout tab = (TabLayout) findViewById(R.id.tablayout);
        tab.addTab(tab.newTab().setIcon(R.drawable.select_shouye).setText("首页"));
        tab.addTab(tab.newTab().setIcon(R.drawable.select_mudidi).setText("目的地"));
        tab.addTab(tab.newTab().setIcon(R.drawable.select_wanle).setText("当地玩乐"));
        tab.addTab(tab.newTab().setIcon(R.drawable.select_wode).setText("我的"));
        tab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                ControlFragmentShow(tab);
                switch (tab.getPosition()) {
                    case 0:
                        ShowToolbar();
                        HideTitleText();
                        ShowButton();
                        ShowImagetitle();
                        break;
                    case 1:
                      HideToolbar();
                        break;
                    case 2:
                        HideImagetitle();
                        HideButton();
                        ShowTitleText();
                        ShowToolbar();
                        break;
                    case 3:
                      HideToolbar();
                        break;


                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }
    public void ControlFragmentShow(TabLayout.Tab tab){
        for (int i = 0; i < fragmnets.size(); i++) {
            if (i == tab.getPosition()) {
                getFragmentTransaction().show(fragmnets.get(i)).commit();
            } else {
                getFragmentTransaction().hide(fragmnets.get(i)).commit();
            }
        }
    }
}
