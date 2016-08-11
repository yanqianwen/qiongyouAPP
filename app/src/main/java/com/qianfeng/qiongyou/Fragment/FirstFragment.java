package com.qianfeng.qiongyou.Fragment;


import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import com.facebook.drawee.view.SimpleDraweeView;
import com.qianfeng.qiongyou.Activity.MoreTopicActivity;
import com.qianfeng.qiongyou.Activity.WebViewActivity;
import com.qianfeng.qiongyou.Adapter.MyFirstListAdapter;
import com.qianfeng.qiongyou.Adapter.MyNextListAdapter;
import com.qianfeng.qiongyou.Adapter.MyRecyclerAdapter;
import com.qianfeng.qiongyou.Bean.FirstListInfo.FirstListData.FirstListData;
import com.qianfeng.qiongyou.Bean.FirstListInfo.FirstListData.FirstListHeaderParent;
import com.qianfeng.qiongyou.Bean.FirstListInfo.FirstListData.FirstListHeaderRecycler;
import com.qianfeng.qiongyou.Bean.FirstListInfo.FirstListData.FirstListRecyclerColor;
import com.qianfeng.qiongyou.Bean.FirstListInfo.FirstListData.FirstListTopic;
import com.qianfeng.qiongyou.Bean.NextListInfo.NextListData;
import com.qianfeng.qiongyou.Bean.NextListInfo.NextListDataBean;
import com.qianfeng.qiongyou.MainActivity;
import com.qianfeng.qiongyou.MyListView;
import com.qianfeng.qiongyou.R;
import com.qianfeng.qiongyou.Utils.FastJsonRequest;
import com.qianfeng.qiongyou.Utils.MyHelper;

import java.util.ArrayList;
import java.util.List;


public class FirstFragment extends Fragment implements View.OnClickListener{
    private SQLiteDatabase db;
    private RequestQueue queue;
    private ContentResolver cr;
    private MyHelper myHelper;
    //控件
    private MyListView list_first;
    private MyListView list_next;
    private SimpleDraweeView image_huiwan;
    private SimpleDraweeView image_tejia;
    private SimpleDraweeView image_chaozhi;
    private SimpleDraweeView image_qianggou;
    private RecyclerView recyclerView;
    private Button first_button;

    //数据
    private List<NextListDataBean> list_nextDataBean;//第二个list的数据
    private List<FirstListTopic> list_topic;//热门主题的数据
    private List<FirstListHeaderRecycler> list_header_recycler;//头部recycler的数据
    private List<FirstListRecyclerColor> list_header_recycler_color;//头部recycler的背景颜色
    private List<FirstListHeaderParent> list_promo;//会玩  数据
    //设配器
    private MyNextListAdapter adapter_nextList;
    private MyFirstListAdapter adapter_firstList;
    private MyRecyclerAdapter adapter_recycler;



    public static FirstFragment newInstance() {
        FirstFragment fragment = new FirstFragment();
        return fragment;
    }
    public FirstFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();//各种初始化
    }

    private void init() {
        queue= Volley.newRequestQueue(getContext());
        list_nextDataBean=new ArrayList<>();
        list_topic=new ArrayList<>();
        list_header_recycler=new ArrayList<>();
        list_header_recycler_color=new ArrayList<>();
        list_promo=new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //找控件
        initView(view);
        initListen();
        //初始化设配器
        initAdapter();
        //初始化第一个list的数据
        initFirstData();
        //初始化第二个list的数据
        initNextData();
    }

    private void initListen() {
        image_huiwan.setOnClickListener(this);
        image_chaozhi.setOnClickListener(this);
        image_qianggou.setOnClickListener(this);
        image_tejia.setOnClickListener(this);
        first_button.setOnClickListener(this);

        list_first.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MainActivity ma = (MainActivity) getActivity();
                Intent intent = new Intent(ma, WebViewActivity.class);
                intent.putExtra("url", list_topic.get(position-1).getUrl());
                ma.startActivity(intent);
            }
        });
        list_next.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MainActivity ma = (MainActivity) getActivity();
                Intent intent = new Intent(ma, WebViewActivity.class);
                intent.putExtra("url", list_nextDataBean.get(position).getUrl());
                ma.startActivity(intent);
            }
        });
    }

    private void initView(View view) {
        list_first= (MyListView) view.findViewById(R.id.list_first);
        list_first.addHeaderView(LayoutInflater.from(getContext()).inflate(R.layout.first_header, null));

        list_next= (MyListView) view.findViewById(R.id.list_next);
        list_next.addHeaderView(LayoutInflater.from(getContext()).inflate(R.layout.item_next_header, null));
        recyclerView= (RecyclerView) view.findViewById(R.id.recycler);

        first_button= (Button) view.findViewById(R.id.first_button);
        image_chaozhi= (SimpleDraweeView) view.findViewById(R.id.image_chaozhi);
        image_huiwan= (SimpleDraweeView) view.findViewById(R.id.image_huiwan);
        image_qianggou= (SimpleDraweeView) view.findViewById(R.id.image_qianggou);
        image_tejia= (SimpleDraweeView) view.findViewById(R.id.image_tejia);

    }

    private void initAdapter(){
        adapter_nextList=new MyNextListAdapter(list_nextDataBean,getContext());
        list_next.setDividerHeight(0);
        list_next.setAdapter(adapter_nextList);

        adapter_firstList=new MyFirstListAdapter(list_topic,getContext());
        list_first.setDividerHeight(0);
        list_first.setAdapter(adapter_firstList);

        adapter_recycler=new MyRecyclerAdapter(getContext(),list_header_recycler,list_header_recycler_color);
        RecyclerView.LayoutManager layoutManager=new GridLayoutManager(
                getContext(),
                2,
                LinearLayoutManager.HORIZONTAL,
                false
        );
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter_recycler);
    }


    private void initNextData() {

        myHelper=new MyHelper(getContext());
        db=myHelper.getReadableDatabase();
        queryDB();
        if(list_nextDataBean.size()==0) {
            db=myHelper.getWritableDatabase();
            downLoad();
        }else {
            adapter_nextList.notifyDataSetChanged();
        }
    }

    private void downLoad() {
        String url="http://open.qyer.com/lastminute/app_selected_product?client_id=qyer_discount_androi&client_secret=227097da1d07a2a9860f&track_user_id=&track_deviceid=000000000000000&track_app_version=1.9.3&track_app_channel=baidu&track_device_info=vbox86p&track_os=Android6.0&app_installtime=1457923891193&size=1080x1794&ra_referer=app_home&page=1&pageSize=10";
        FastJsonRequest<NextListData> request=new FastJsonRequest<NextListData>(
                url,
                NextListData.class,
                new Response.Listener<NextListData>() {
                    @Override
                    public void onResponse(NextListData nextListData) {
                        for (int i = 0; i <nextListData.getData().size() ; i++) {
                            NextListDataBean bean=nextListData.getData().get(i);
                           db.execSQL("insert into jingxuan(img,title,price) values (?,?,?)",
                                   new Object[]{ bean.getPic(),bean.getTitle(),bean.getPrice()});
                            //list_nextDataBean.add(bean);
                        }
                        //刷新设配器
                        // adapter_nextList.notifyDataSetChanged();
                        queryDB();
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

    private void queryDB() {
        Cursor cursor=db.query("jingxuan",null,null,null,null,null,null);
        while(cursor.moveToNext()){
            NextListDataBean bean=new NextListDataBean();
            bean.setPic(cursor.getString(cursor.getColumnIndex("img")));
            bean.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            bean.setPrice(cursor.getString(cursor.getColumnIndex("price")));
            list_nextDataBean.add(bean);
        }
    }

    private void initFirstData() {
        String url="http://open.qyer.com/lastminute/home/major?client_id=qyer_discount_androi&client_secret=227097da1d07a2a9860f&track_user_id=&track_deviceid=000000000000000&track_app_version=1.9.3&track_app_channel=baidu&track_device_info=vbox86p&track_os=Android6.0&app_installtime=1457923891193&size=1080x1794&ra_referer=app_home";
        FastJsonRequest<FirstListData> request=new FastJsonRequest<FirstListData>(
                url,
                FirstListData.class,
                new Response.Listener<FirstListData>() {
                    @Override
                    public void onResponse(FirstListData firstListData) {
                        //热门主题
                        for (int i = 0; i <firstListData.getData().getHot_topic().size() ; i++) {
                            list_topic.add(firstListData.getData().getHot_topic().get(i));
                        }
                        adapter_firstList.notifyDataSetChanged();

                        //会玩
                        for (int i = 0; i <firstListData.getData().getPromo().size() ; i++) {
                            list_promo.add(firstListData.getData().getPromo().get(i));
                        }
                        //设置图片
                        setImage();

                        //recycler
                        for (int i = 0; i <firstListData.getData().getBar().size() ; i++) {
                            list_header_recycler.add(firstListData.getData().getBar().get(i));
                        }
                        //recyclerColor
                        for (int i = 0; i <firstListData.getData().getPtype_icon().size() ; i++) {
                            list_header_recycler_color.add(firstListData.getData().getPtype_icon().get(i));
                        }
                        adapter_recycler.notifyDataSetChanged();
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

    private void setImage() {
        image_huiwan.setImageURI(Uri.parse(list_promo.get(0).getImg()));
        image_tejia.setImageURI(Uri.parse(list_promo.get(1).getImg()));
        image_chaozhi.setImageURI(Uri.parse(list_promo.get(2).getImg()));
        image_qianggou.setImageURI(Uri.parse(list_promo.get(3).getImg()));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.image_huiwan:
                sendValue(0);
                break;
            case R.id.image_tejia:
                sendValue(1);
                break;
            case R.id.image_chaozhi:
                sendValue(2);
                break;
            case R.id.image_qianggou:
                sendValue(3);
                break;
            case R.id.first_button:
                Toast.makeText(getContext(),"button",Toast.LENGTH_SHORT).show();
                MainActivity ma= (MainActivity) getActivity();
                Intent intent=new Intent(ma, MoreTopicActivity.class);
                ma.startActivity(intent);
                break;
        }
    }
    public void sendValue(int position){
        MainActivity ma= (MainActivity) getActivity();
        Intent intent=new Intent(ma, WebViewActivity.class);
        intent.putExtra("url", list_promo.get(position).getUrl());
        ma.startActivity(intent);

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        queue.cancelAll(this);
    }

}
