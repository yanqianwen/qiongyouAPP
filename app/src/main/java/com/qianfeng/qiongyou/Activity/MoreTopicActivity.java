package com.qianfeng.qiongyou.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.qianfeng.qiongyou.Adapter.MyFirstListGetMoreAdapter;
import com.qianfeng.qiongyou.Bean.FirstListInfo.FirstListData.FirstListData;
import com.qianfeng.qiongyou.Bean.FirstListInfo.FirstListData.FirstListTopicListen;
import com.qianfeng.qiongyou.MyListView;
import com.qianfeng.qiongyou.R;
import com.qianfeng.qiongyou.Utils.FastJsonRequest;

import java.util.ArrayList;
import java.util.List;

public class MoreTopicActivity extends AppCompatActivity {
    private MyListView myListView;
    private List<FirstListTopicListen> listTopicListens;
    private MyFirstListGetMoreAdapter adapter;
    private Context context;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_more_topic);
        queue= Volley.newRequestQueue(this);
        myListView = (MyListView) findViewById(R.id.more_topic);
        listTopicListens = new ArrayList<>();
        initData();
        adapter = new MyFirstListGetMoreAdapter(listTopicListens, this);
        myListView.setAdapter(adapter);
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(MoreTopicActivity.this, WebViewActivity.class);
                intent.putExtra("url", listTopicListens.get(position).getUrl());
                startActivity(intent);
            }
        });
    }

    private void initData() {
        String url = "http://open.qyer.com/lastminute/home/major?client_id=qyer_discount_androi&client_secret=227097da1d07a2a9860f&track_user_id=&track_deviceid=000000000000000&track_app_version=1.9.3&track_app_channel=baidu&track_device_info=vbox86p&track_os=Android6.0&app_installtime=1457923891193&size=1080x1794&ra_referer=app_home";
        FastJsonRequest<FirstListData> request = new FastJsonRequest<FirstListData>(
                url,
                FirstListData.class,
                new Response.Listener<FirstListData>() {
                    @Override
                    public void onResponse(FirstListData firstListData) {
                        //热门主题点击事件数据
                        for (int i = 0; i < firstListData.getData().getSlide().size(); i++) {
                            listTopicListens.add(firstListData.getData().getSlide().get(i));
                        }
                        adapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }
        );
        request.setTag(this);
        queue.add(request);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        queue.cancelAll(this);
    }
}
