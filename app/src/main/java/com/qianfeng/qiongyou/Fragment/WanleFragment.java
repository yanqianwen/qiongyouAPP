package com.qianfeng.qiongyou.Fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.qianfeng.qiongyou.Activity.CityActivity;
import com.qianfeng.qiongyou.Activity.CitywalkActivity;
import com.qianfeng.qiongyou.Activity.FindcityActivity;
import com.qianfeng.qiongyou.Activity.OnedayyouActivity;
import com.qianfeng.qiongyou.Activity.WebViewActivity;
import com.qianfeng.qiongyou.Bean.DangdiwanleBean;
import com.qianfeng.qiongyou.MainActivity;
import com.qianfeng.qiongyou.MyListView;
import com.qianfeng.qiongyou.R;
import com.qianfeng.qiongyou.Sortlistview.QuickLocationMainActivity;
import com.qianfeng.qiongyou.Utils.FastJsonRequest;

import java.util.ArrayList;
import java.util.List;


public class WanleFragment extends Fragment implements View.OnClickListener {

    private static final String ARG_PARAM1 = "param1";

    private String mParam1;
    private RelativeLayout yiriyou,citywalk,faxiancity;
    private MyListView lv;
    private RequestQueue queue;
    private Myadapter adapter ;
    private TextView tv,text_time;
    private SimpleDraweeView image_tou;
    private List<DangdiwanleBean.DataBean.TopSellBean> top_sell;
    private List<DangdiwanleBean.DataBean.TopSellBean> datas = new ArrayList<>();


    public WanleFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static WanleFragment newInstance(String param1) {
        WanleFragment fragment = new WanleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);

        }
       // Fresco.initialize(getContext());
        queue = Volley.newRequestQueue(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_wanle, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lv = (MyListView) view.findViewById(R.id.lv);
        yiriyou= (RelativeLayout) view.findViewById(R.id.yiriyou);
        citywalk= (RelativeLayout) view.findViewById(R.id.citywalk);
        faxiancity= (RelativeLayout) view.findViewById(R.id.findcity);
        yiriyou.setOnClickListener(this);
        citywalk.setOnClickListener(this);
        faxiancity.setOnClickListener(this);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MainActivity ma= (MainActivity) getActivity();
                Intent intent=new Intent(ma, WebViewActivity.class);
                intent.putExtra("url",top_sell.get(position).getUrl());
                ma.startActivity(intent);
            }
        });
        text_time= (TextView) view.findViewById(R.id.time);
        image_tou= (SimpleDraweeView) view.findViewById(R.id.image_tou);
        tv= (TextView) view.findViewById(R.id.qiehuancity);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity().getApplicationContext(), CityActivity.class));
            }
        });
        adapter=new Myadapter(this);
        lv.setAdapter(adapter);
        getInfo(mParam1);
    }
    public void getInfo(String mParam1) {
        FastJsonRequest<DangdiwanleBean> request = new FastJsonRequest<DangdiwanleBean>(
                mParam1,
                DangdiwanleBean.class,
                new Response.Listener<DangdiwanleBean>() {
                    @Override
                    public void onResponse(DangdiwanleBean dangdiwanleBean) {
                        DangdiwanleBean.DataBean data = dangdiwanleBean.getData();
                        text_time.setText(data.getLocal_time());
                        image_tou.setImageURI(Uri.parse(data.getCover_pic()));
                        top_sell = data.getTop_sell();
                        for (int i = 0; i < top_sell.size(); i++) {
                            DangdiwanleBean.DataBean.TopSellBean topSellBean = top_sell.get(i);
                            datas.add(topSellBean);
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
    public void onDestroy() {
        super.onDestroy();
        queue.cancelAll(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.yiriyou:
                startActivity(new Intent(getActivity().getApplicationContext(), OnedayyouActivity.class));
                break;
            case R.id.citywalk:
                startActivity(new Intent(getActivity().getApplicationContext(), CitywalkActivity.class));
                break;
            case R.id.findcity:
                startActivity(new Intent(getActivity().getApplicationContext(), FindcityActivity.class));
                break;
        }
    }

    public class Myadapter extends BaseAdapter {
        private LayoutInflater inflater;

        public Myadapter(WanleFragment fragment) {
            inflater = LayoutInflater.from(getContext());
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
                DangdiwanleBean.DataBean.TopSellBean topsellbean = datas.get(i);
                holder.cte_short.setText(topsellbean.getCate_short_name());
                holder.imageView.setImageURI(Uri.parse(topsellbean.getPic()));
                String substring = topsellbean.getPrice().substring(4, 6);
                holder.price.setText(substring +"元起");
                holder.city_name.setText(topsellbean.getCity_name());
                holder.sale_count.setText(topsellbean.getSale_count()+"已售");
                holder.title.setText(topsellbean.getTitle());

            }

            return view;
        }

        class ViewHolder {
            SimpleDraweeView imageView;
            TextView cte_short, city_name, title, sale_count, price;
        }
    }
}
