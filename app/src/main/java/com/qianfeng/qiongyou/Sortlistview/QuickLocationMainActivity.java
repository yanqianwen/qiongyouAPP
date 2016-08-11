package com.qianfeng.qiongyou.Sortlistview;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.*;


import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.qianfeng.qiongyou.Bean.CityBean;
import com.qianfeng.qiongyou.R;
import com.qianfeng.qiongyou.Utils.FastJsonRequest;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zuowen
 * @time 2013.05.08
 * @email 782287169@qq.com
 * <p/>
 * ����ַ���  ��Ӣ������   �Ҳ�����   ���������ʾ��   ��������ʾ
 */
public class QuickLocationMainActivity extends AppCompatActivity implements
        ListView.OnScrollListener,
        AdapterView.OnItemClickListener, android.view.View.OnClickListener
{
    private QuickLocationRightTool letterListView;
    private Handler handler;
    private DisapearThread disapearThread;
    private int scrollState;
    private QuickLocationListAdapter quickLocationListAdapter;
    private ListView listMain;
    private TextView txtOverlay, title;
    private WindowManager windowManager;
    private RequestQueue queue;

    private String[] stringArr = {"ai", "zuo", "wn", "g黄真伊", "he河智苑", "@", "&&*(*", "?? ??? ???", "?", "擬好",
            "上饶", "厦门", "深圳", "武林", "text1", "+*())*&%$^", "11112", "6666", "898和",
            "阿拉伯", "阿镇", "下午", "责打", "浙江", "浙江", "阿布", "北京", "北城", "成", "城市", "123a", "234b", "678c", "得", "额",
            "方", "搞", "广州", "黄石", "黄冈", "杭州", "上海", "武林"};
    private  String url="http://open.qyer.com/lastminute/page/lol_city_list?client_id=qyer_discount_androi&client_secret=227097da1d07a2a9860f&track_user_id=&track_deviceid=000000000000000&track_app_version=1.9.3&track_app_channel=baidu&track_device_info=vbox86p&track_os=Android6.0&app_installtime=1457923891193&size=1080x1794&ra_referer=app_home";
    private String[] stringArr3 = new String[0];
   // private String[] stringArr;
    private ArrayList arrayList = new ArrayList();
    private ArrayList arrayList2 = new ArrayList();
    private ArrayList arrayList3 = new ArrayList();
    private Map<String, String> map = new HashMap<String, String>();
    private List<String> citys=new ArrayList<>();


    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        queue= Volley.newRequestQueue(this);
        //getCityinfo();
        for (int i = 0; i < stringArr.length; i++) {
            String pinyin = converterToPinYin(citys.get(i));
            Log.i("tag","citys.get(i)------------>"+stringArr[i]);
            arrayList.add(pinyin);                              //���б�����ƴ��
            Collections.sort(arrayList, new MixComparator());
            if (!arrayList2.contains(pinyin.substring(0, 1)) && isWord(pinyin.substring(0, 1))) {
                arrayList2.add(pinyin.substring(0, 1));        //���б����ƴ������ĸ
                Collections.sort(arrayList2, new MixComparator());
            }
            map.put(pinyin, stringArr[i]);
        }
//        stringArr=new String[citys.size()];
//       stringArr=citys.toArray(new String[citys.size()]);
//        Log.i("tag", "stringArr------------>" + citys.size());
        stringArr = (String[]) arrayList.toArray(stringArr);


        arrayList3.add("#");                                     //���б���Ӳ������ַ�
        for (int i = 0; i < arrayList2.size(); i++) {
            String string = (String) arrayList2.get(i);
            arrayList3.add(string.toUpperCase());       //toUpperCase��д��ĸ
        }
        arrayList3.add("*");

        stringArr3 = (String[]) arrayList3.toArray(stringArr3); // �õ��Ҳ�Ӣ����ĸ�б�
        letterListView = (QuickLocationRightTool) findViewById(R.id.rightCharacterListView);
        letterListView.setB(stringArr3);
        letterListView.setOnTouchingLetterChangedListener(new LetterListViewListener());

        textOverlayout();


        // ��ʼ��ListAdapter
        quickLocationListAdapter = new QuickLocationListAdapter(this, stringArr,this, map);
        listMain = (ListView) findViewById(R.id.listInfo);
//        listMain.setOnItemClickListener(this);
//        listMain.setOnScrollListener(this);
        listMain.setAdapter(quickLocationListAdapter);
        disapearThread = new DisapearThread();
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
                            Log.i("tag", "tag--------------------->" + city_name);
                        }
                        for (int i = 0; i < motherland_cities.size(); i++) {
                            CityBean.DataBean.CityListBean.MotherlandCitiesBean motherlandCitiesBean = motherland_cities.get(i);
                            String city_name = motherlandCitiesBean.getCity_name();
                            citys.add(city_name);

                        }
//                        stringArr=citys.toArray(new String[citys.size()]);
//                        System.out.print(stringArr);
                        Log.i("tag", "tag--------------------->" + citys.toString());

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

    /**
     * ��������ĸ
     */
    public void textOverlayout()
    {
        handler = new Handler();
        //������
        title = (TextView) findViewById(R.id.list_title);
        // ��ʼ������ĸ����ʾ��
        txtOverlay = (TextView) LayoutInflater.from(this).inflate(
                R.layout.popup_char, null);
        txtOverlay.setVisibility(View.INVISIBLE);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                PixelFormat.TRANSLUCENT);
        windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        windowManager.addView(txtOverlay, lp);
    }

    /**
     * �Ҳർ��������б����ָ��λ��
     */
    public class LetterListViewListener implements
            QuickLocationRightTool.OnTouchingLetterChangedListener
    {
        public void onTouchingLetterChanged(final String s)
        {
            int num = 0;
            for (int i = 0; i < stringArr.length; i++) {
                if ("a".equals(s) || "#".equals(s)) {      //����
                    num = 0;
                }
                else if ("*".equals(s)) {                      //�ײ�
                    num = stringArr.length;
                }
                else if (isWord(stringArr[i].substring(0, 1)) && (character2ASCII(stringArr[i].substring(0, 1)) < (character2ASCII(s) + 32))) {
                    num += 1;                                     //�����ж�����ĸ����ĸ��ascllֵС��s�ǣ�����λ��+1�������10�����С��s���͹���10��
                }

            }
            if (num < 2) {
                listMain.setSelectionFromTop(num, 0);
            }
            else {
                listMain.setSelectionFromTop(num, 5);    //������
            }
        }
    }


    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount)
    {
        title.setVisibility(View.VISIBLE);
        if (firstVisibleItem != 0) {
            title.setText(map.get(stringArr[firstVisibleItem]));
        }
        else {
            title.setText("a");
        }
        title.setText(map.get(stringArr[firstVisibleItem]));
        txtOverlay.setText(String.valueOf(stringArr[firstVisibleItem].charAt(0)));// ���������Ե�һ���ɼ��б�Ϊ׼

    }

    public void onScrollStateChanged(AbsListView view, int scrollState)
    {
        this.scrollState = scrollState;
        if (scrollState == ListView.OnScrollListener.SCROLL_STATE_IDLE) {
            handler.removeCallbacks(disapearThread);
            // ��ʾ�ӳ�1.0s����ʧ
            boolean bool = handler.postDelayed(disapearThread, 1000);
        }
        else {
            txtOverlay.setVisibility(View.VISIBLE);
        }
    }

    /**
     * �б���
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id)
    {
        String personalName = map.get(stringArr[position]);
        Toast.makeText(QuickLocationMainActivity.this, personalName, Toast.LENGTH_SHORT).show();
    }

    public void onClick(View view)
    {

    }

    private class DisapearThread implements Runnable
    {
        public void run()
        {
            // ������1.5s�ڣ��û��ٴ��϶�ʱ��ʾ����ִ���������
            if (scrollState == ListView.OnScrollListener.SCROLL_STATE_IDLE) {
                txtOverlay.setVisibility(View.INVISIBLE);
            }
        }
    }

    public void onDestroy()
    {
        super.onDestroy();
        txtOverlay.setVisibility(View.INVISIBLE);
        title.setVisibility(View.INVISIBLE);
        windowManager.removeView(txtOverlay);
    }


    /**
     * ����ƴ��ת������
     *
     * @param chinese
     * @return
     */
    public String converterToPinYin(String chinese)
    {
        String pinyinString = "";
        char[] charArray = chinese.toCharArray();
        // �����Ҫ���������ʽ������Ĭ�ϵļ���
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        try {
            // �������飬ASC�����128����ת��
            for (int i = 0; i < charArray.length; i++) {
                if (charArray[i] > 128) {
                    // charAt(0)ȡ������ĸ
                    if (charArray[i] >= 0x4e00 && charArray[i] <= 0x9fa5) {    //�ж��Ƿ�����
                        pinyinString += PinyinHelper.toHanyuPinyinStringArray(
                                charArray[i], defaultFormat)[0].charAt(0);
                    }
                    else {                          //�������ĵĴ���δ֪�������޷����?���ձ��ȵ���������
                        pinyinString += "?";
                    }
                }
                else {
                    pinyinString += charArray[i];
                }
            }
            return pinyinString;
        }
        catch (BadHanyuPinyinOutputFormatCombination e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * �ѵ���Ӣ����ĸ�����ַ�ת��������ASCII��
     *
     * @param input
     * @return
     */
    public static int character2ASCII(String input)
    {
        char[] temp = input.toCharArray();
        StringBuilder builder = new StringBuilder();
        for (char each : temp) {
            builder.append((int) each);
        }
        String result = builder.toString();
        return Integer.parseInt(result);
    }

    /**
     * ������򹤾�
     */
    public class MixComparator implements Comparator<String>
    {
        public int compare(String o1, String o2)
        {
            // �ж��Ƿ�Ϊ��""
            if (isEmpty(o1) && isEmpty(o2))
                return 0;
            if (isEmpty(o1))
                return -1;
            if (isEmpty(o2))
                return 1;
            String str1 = "";
            String str2 = "";
            try {
                str1 = (o1.toUpperCase()).substring(0, 1);
                str2 = (o2.toUpperCase()).substring(0, 1);
            }
            catch (Exception e) {
                System.out.println("ĳ��strΪ\" \" ��");
            }
            if (isWord(str1) && isWord(str2)) {               //��ĸ
                return str1.compareTo(str2);
            }
            else if (isNumeric(str1) && isWord(str2)) {     //������ĸ
                return 1;
            }
            else if (isNumeric(str2) && isWord(str1)) {
                return -1;
            }
            else if (isNumeric(str1) && isNumeric(str2)) {       //��������
                if (Integer.parseInt(str1) > Integer.parseInt(str2)) {
                    return 1;
                }
                else {
                    return -1;
                }
            }
            else if (isAllWord(str1) && (!isAllWord(str2))) {      //������ĸ  �����ַ�
                return -1;
            }
            else if ((!isAllWord(str1)) && isWord(str2)) {
                return 1;
            }
            else {
                return 1;
            }
        }
    }

    /**
     * �жϿ�
     *
     * @param str
     * @return
     */
    private boolean isEmpty(String str)
    {
        return "".equals(str.trim());
    }

    /**
     * �ж�����
     *
     * @param str
     * @return
     */
    public boolean isNumeric(String str)
    {
        Pattern pattern = Pattern.compile("^[0-9]*$");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * �ж���ĸ
     *
     * @param str
     * @return
     */
    public boolean isWord(String str)
    {
        Pattern pattern = Pattern.compile("^[A-Za-z]+$");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * �ж���ĸ���ֻ��
     *
     * @param str
     * @return
     */
    public boolean isAllWord(String str)
    {
        Pattern pattern = Pattern.compile("^[A-Za-z0-9]+$");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        else {
            return true;
        }
    }

}