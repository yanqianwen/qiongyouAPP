package com.qianfeng.qiongyou.Activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.facebook.drawee.view.SimpleDraweeView;
import com.qianfeng.qiongyou.Bean.FaxiancityBean;
import com.qianfeng.qiongyou.MainActivity;
import com.qianfeng.qiongyou.MyListView;
import com.qianfeng.qiongyou.R;
import com.qianfeng.qiongyou.Utils.FastJsonRequest;

import java.util.ArrayList;
import java.util.List;

public class FindcityActivity extends AppCompatActivity {
    MyListView lv;
    SimpleDraweeView iv;
    Myadapter adapter;
    RequestQueue queue;
    List<FaxiancityBean.DataBean.LastminutesBean> datas=new ArrayList<>();
    String url="http://open.qyer.com/lastminute/app_lastminute_list?client_id=qyer_discount_androi&client_secret=227097da1d07a2a9860f&track_user_id=&track_deviceid=000000000000000&track_app_version=1.9.3&track_app_channel=baidu&track_device_info=vbox86p&track_os=Android6.0&app_installtime=1457923891193&size=1080x1794&ra_referer=&page_size=10&page=1&product_type=2378&continent_id=0&country_id=0&departure=&times=&city_id=54&orderName=&orderValue=&kw=&sequence=";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findcity);
        lv= (MyListView) findViewById(R.id.lv_findcity_click);
        iv= (SimpleDraweeView) findViewById(R.id.iv_findcity_click);

        queue= Volley.newRequestQueue(this);
        adapter=new Myadapter(this);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              
                Intent intent=new Intent(FindcityActivity.this, WebViewActivity.class);
                intent.putExtra("url",datas.get(position).getUrl());
                 startActivity(intent);
            }
        });
        getInfo();
    }
    private void getInfo() {
        FastJsonRequest<FaxiancityBean> request =new FastJsonRequest<>(
                url,
                FaxiancityBean.class,
                new Response.Listener<FaxiancityBean>() {
                    @Override
                    public void onResponse(FaxiancityBean faxiancityBean) {
                        FaxiancityBean.DataBean data = faxiancityBean.getData();
                        List<FaxiancityBean.DataBean.LastminutesBean> lastminutes = data.getLastminutes();
                        for (int i = 0; i < lastminutes.size(); i++) {
                            FaxiancityBean.DataBean.LastminutesBean lastminutesBean = lastminutes.get(i);
                            datas.add(lastminutesBean);
                        }
                        FaxiancityBean.DataBean.LastminutesBean lastminutesBean = datas.get(3);
                        iv.setImageURI(Uri.parse(lastminutesBean.getPic()));
                        adapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                    }
                }
        );
        queue.add(request);
        request.setTag(this);

    }
    public class Myadapter extends BaseAdapter {
        private LayoutInflater inflater;

        public Myadapter(Context context) {
            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return datas == null ? 0 : datas.size();
        }

        @Override
        public Object getItem(int i) {
            return datas.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder holder =null;
            if (view == null) {
                view = inflater.inflate(R.layout.wanle_list_item, viewGroup, false);
                holder = new ViewHolder();
                holder.cte_short = (TextView) view.findViewById(R.id.cte_short_name);
                holder.city_name = (TextView) view.findViewById(R.id.city_name);
                holder.title = (TextView) view.findViewById(R.id.title);
                holder.sale_count = (TextView) view.findViewById(R.id.sale_count);
                holder.price = (TextView) view.findViewById(R.id.price);
                holder.imageView = (SimpleDraweeView) view.findViewById(R.id.iv);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            for (int j = 0; j < datas.size(); j++) {
                FaxiancityBean.DataBean.LastminutesBean lastminutesBean = datas.get(i);
                holder.cte_short.setText(lastminutesBean.getCate_short_name());
                holder.imageView.setImageURI(Uri.parse(lastminutesBean.getPic()));
                String substring = lastminutesBean.getPrice().substring(4, 6);
                holder.price.setText(substring +"元起");
                holder.city_name.setText(lastminutesBean.getCity_name());
                holder.sale_count.setText(lastminutesBean.getSale_count()+"已售");
                holder.title.setText(lastminutesBean.getTitle());

            }

            return view;
        }

        class ViewHolder {
            SimpleDraweeView imageView;
            ImageView iv;
            TextView cte_short, city_name, title, sale_count, price;
        }
    }
}
