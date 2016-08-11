package com.qianfeng.qiongyou.Activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.qianfeng.qiongyou.Bean.CityBean;
import com.qianfeng.qiongyou.R;
import com.qianfeng.qiongyou.Utils.FastJsonRequest;

import java.util.ArrayList;
import java.util.List;

public class CityActivity extends AppCompatActivity {
ListView list_city,list_letter;
    String[] letter={"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
    List<String> letters=new ArrayList<>();
    List<String> citys=new ArrayList<>();
    List<String> tags=new ArrayList<>();
    RequestQueue queue;
    LayoutInflater inflater;
    String url="http://open.qyer.com/lastminute/page/lol_city_list?client_id=qyer_discount_androi&client_secret=227097da1d07a2a9860f&track_user_id=&track_deviceid=000000000000000&track_app_version=1.9.3&track_app_channel=baidu&track_device_info=vbox86p&track_os=Android6.0&app_installtime=1457923891193&size=1080x1794&ra_referer=app_home";
    Myletteradapter letteradapter=new Myletteradapter();
    GroupListAdapter cityadapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
        list_city= (ListView) findViewById(R.id.list_city);
        list_letter= (ListView) findViewById(R.id.list_lateer);
        inflater=getLayoutInflater();
        queue= Volley.newRequestQueue(this);
        getCityinfo();
        for (int i = 0; i <letter.length; i++) {
            letters.add(letter[i]);
            tags.add(letter[i]);
        }
        cityadapter=new GroupListAdapter(this,citys,tags);
        list_letter.setAdapter(letteradapter);
        list_city.setAdapter(cityadapter);
    }

    private void getCityinfo() {
        FastJsonRequest<CityBean> request=new FastJsonRequest<CityBean>(
                url,
                CityBean.class,
                new Response.Listener<CityBean>() {
                    @Override
                    public void onResponse(CityBean cityBean) {
                        CityBean.DataBean data = cityBean.getData();
                        CityBean.DataBean.CityListBean city_list = data.getCity_list();
                        List<CityBean.DataBean.CityListBean.AbroadCitiesBean> abroad_cities = city_list.getAbroad_cities();
                        List<CityBean.DataBean.CityListBean.MotherlandCitiesBean> motherland_cities = city_list.getMotherland_cities();
                        for (int i = 0; i < abroad_cities.size(); i++) {
                            CityBean.DataBean.CityListBean.AbroadCitiesBean abroadCitiesBean = abroad_cities.get(i);
                            String city_name = abroadCitiesBean.getCity_name();
                            citys.add(city_name);
                        }
                        for (int i = 0; i < motherland_cities.size(); i++) {
                            CityBean.DataBean.CityListBean.MotherlandCitiesBean motherlandCitiesBean = motherland_cities.get(i);
                            String city_name = motherlandCitiesBean.getCity_name();
                            citys.add(city_name);

                        }
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

    public class Myletteradapter extends BaseAdapter{

        @Override
        public int getCount() {
            return letters==null?0:letters.size();
        }

        @Override
        public Object getItem(int i) {
            return letters.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
       ViewHolder holder=null;
            if(view==null){
                view=inflater.inflate(R.layout.letter_item,viewGroup,false);
                holder=new ViewHolder();
                holder.tv= (TextView) view.findViewById(R.id.tv_letter);
                view.setTag(holder);
            }else {
                holder= (ViewHolder) view.getTag();
            }
            String letter=letters.get(i);
            holder.tv.setText(letter);
            return view;
        }
        class ViewHolder{
            TextView tv;
        }
    }
    public  class GroupListAdapter extends ArrayAdapter<String>{
        List<String> listTag=null;
        public GroupListAdapter(Context context, List<String> objects, List<String> tags) {
            super(context, 0, objects);
            this.listTag=tags;
        }

        @Override
        public boolean isEnabled(int position) {
            if(listTag.contains(getItem(position))){
                return false;
            }
            return super.isEnabled(position);
        }

        @Override
        public int getItemViewType(int position) {
            return super.getItemViewType(position);

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view=convertView;

            if(listTag.contains(getItem(position))){
                view=inflater.inflate(R.layout.tag_item,null);
            }else {
                view=inflater.inflate(R.layout.city_item,null);
            }
            TextView tv= (TextView) view.findViewById(R.id.tv_text);
            tv.setText(getItem(position));
            return view;
        }
    }

}
