package com.qianfeng.qiongyou.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;


import com.qianfeng.qiongyou.MainActivity;
import com.qianfeng.qiongyou.R;

import java.util.ArrayList;

public class GuideActivity extends AppCompatActivity {
    ViewPager vp;
    RadioGroup rg;
    LayoutInflater inflater;
    ArrayList<View> list=new ArrayList<View>();
    Myviewpageradpater adapter;
    int[] imags={R.mipmap.bg_splash1,R.mipmap.bg_splash2, R.mipmap.bg_splash4};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        vp= (ViewPager) findViewById(R.id.viepage);
        rg= (RadioGroup) findViewById(R.id.rg);
        inflater=getLayoutInflater();
        adapter=new Myviewpageradpater();
        for (int i = 0; i <imags.length ; i++) {
            View v=inflater.inflate(R.layout.guidepager_item,null);
            ImageView iv= (ImageView) v.findViewById(R.id.iv);
            iv.setImageResource(imags[i]);
            list.add(v);
        }
        vp.setAdapter(adapter);
        vp.setOnPageChangeListener(new  ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                RadioButton selectbtn= (RadioButton) rg.getChildAt(position);
                rg.check(selectbtn.getId());
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

    }
    class Myviewpageradpater extends PagerAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View v=list.get(position);
            container.addView(v);
            if(position==list.size()-1){
                Button btn= (Button) v.findViewById(R.id.btn);
                btn.setVisibility(View.VISIBLE);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(GuideActivity.this, MainActivity.class));
                        finish();
                    }
                });
            }
            return v;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
           container.removeView(list.get(position));
        }
    }
}
