package com.qianfeng.qiongyou.Activity;


import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.qianfeng.qiongyou.MyApp;
import com.qianfeng.qiongyou.R;

/**
 * Created by Administrator on 2016/3/29.
 */
public  abstract class MyBaseActivity extends AppCompatActivity {
    private int click_count=0;
    private ActionBar bar;
    private TextView tv;
    private ImageView iv_title;
    private Button btn_serach,btn_messeage;
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }
    public void setToolbar(Toolbar toolbar){
        setSupportActionBar(toolbar);
        bar=getSupportActionBar();
        bar.setDisplayShowTitleEnabled(false);
        bar.setDisplayShowCustomEnabled(true);
        View view=getLayoutInflater().inflate(R.layout.toolbar_item,null);
        iv_title= (ImageView) view.findViewById(R.id.iv_title);
        btn_serach= (Button) view.findViewById(R.id.btn_serach);
        btn_messeage= (Button) view.findViewById(R.id.btn_message);
        tv= (TextView) view.findViewById(R.id.tv_title);
        bar.setCustomView(view, new ActionBar.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.WRAP_CONTENT
        ));
    }

    public void setTitleText(String msg) {
        if (tv.getVisibility() != View.VISIBLE) {
            tv.setVisibility(View.VISIBLE);
        }
        tv.setText(msg);
    }
    public FragmentTransaction getFragmentTransaction(){
        return getSupportFragmentManager().beginTransaction();
    }
    public void HideTitleText() {
        tv.setVisibility(View.GONE);
    }
    public void ShowTitleText() {
        tv.setVisibility(View.VISIBLE);
    }

    public void HideImagetitle() {
        iv_title.setVisibility(View.GONE);
    }
    public void ShowImagetitle() {
        iv_title.setVisibility(View.VISIBLE);
    }
    public void HideToolbar(){

        bar.hide();
    }
    public void ShowToolbar(){
        bar.show();
    }
   public void HideButton(){
       btn_messeage.setVisibility(View.GONE);
       btn_serach.setVisibility(View.GONE);
   }
    public void ShowButton(){
       btn_messeage.setVisibility(View.VISIBLE);
       btn_serach.setVisibility(View.VISIBLE);
   }
    @Override
    public void onBackPressed() {
        click_count++;
        if (click_count <= 1) {
            Toast.makeText(this,"再次点击退出",Toast.LENGTH_SHORT).show();

        }else {
            super.onBackPressed();
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                click_count = 0;
            }
        }, 1500);

    }
}
