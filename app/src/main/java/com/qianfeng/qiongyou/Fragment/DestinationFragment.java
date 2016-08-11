package com.qianfeng.qiongyou.Fragment;

import android.app.AlertDialog;
import android.content.Context;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import android.support.v4.app.Fragment;

import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;

import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.qianfeng.qiongyou.Activity.SearchActivity;
import com.qianfeng.qiongyou.Adapter.MyDestinationListViewAdapter;
import com.qianfeng.qiongyou.Adapter.MyDestinationRecycleViewAdapter;
import com.qianfeng.qiongyou.Bean.DestinationDataBean;
import com.qianfeng.qiongyou.MyApp;
import com.qianfeng.qiongyou.R;
import com.qianfeng.qiongyou.Utils.RecycleItemDecoration;


import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DestinationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DestinationFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String url = "http://open.qyer.com/lastminute/conf/destination?client_id=qyer_discount_androi&client_secret=227097da1d07a2a9860f&track_user_id=&track_deviceid=000000000000000&track_app_version=1.9.3&track_app_channel=baidu&track_device_info=vbox86p&track_os=Android6.0&app_installtime=1457923891193&size=1080x1794&ra_referer=app_home";

    private RequestQueue queue;

    private MyDestinationListViewAdapter mAdapter;
    private MyDestinationRecycleViewAdapter mRecycleAdapter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ListView listView;
    private RecyclerView recyclerView;
    private List<DestinationDataBean.DataBean> data;
    private OnFragmentInteractionListener mListener;

    public DestinationFragment() {
        // Required empty public constructor

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DestinationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DestinationFragment newInstance(String param1, String param2) {
        DestinationFragment fragment = new DestinationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        destinations = data.get(0).getDestinations();
        View itemView = inflater.inflate(R.layout.fragment_destination,null);
        getInfo();
        return itemView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = (ListView) view.findViewById(R.id.list_destination_fragment);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycleview_destination_fragment);
        listView.setVerticalScrollBarEnabled(false);
        listView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2, OrientationHelper.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new RecycleItemDecoration(getActivity(), OrientationHelper.VERTICAL));
        recyclerView.addItemDecoration(new RecycleItemDecoration(getActivity(), OrientationHelper.HORIZONTAL));

//        mRecycleAdapter.notifyDataSetChanged();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                destinations = null;
                mAdapter.setMySelection(position);

//                mRecycleAdapter.notifyDataSetChanged();
                mRecycleAdapter.setRecycleShow(position);
            }
        });

    }


    private void getInfo() {
        queue = MyApp.getRequestQueue();
        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        DestinationDataBean destinationDataBean = JSONObject.parseObject(s, DestinationDataBean.class);
                        data = destinationDataBean.getData();
                        mAdapter = new MyDestinationListViewAdapter(data, getActivity());
                        listView.setAdapter(mAdapter);
                        mRecycleAdapter = new MyDestinationRecycleViewAdapter(data);
                        recyclerView.setAdapter(mRecycleAdapter);
                        mRecycleAdapter.setOnRecycleViewItemClickListener(new MyDestinationRecycleViewAdapter.MyRecycleViewItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
//                                Toast.makeText(getActivity(), "item :: " + position, Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getActivity(), SearchActivity.class));
                            }
                        });
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


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        queue.cancelAll(this);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
