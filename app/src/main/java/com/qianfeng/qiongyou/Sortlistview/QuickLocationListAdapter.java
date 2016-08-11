package com.qianfeng.qiongyou.Sortlistview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.qianfeng.qiongyou.R;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zuo
 *         �б�������
 */
public class QuickLocationListAdapter extends BaseAdapter
{
    private LayoutInflater layoutInflater;
    private OnClickListener onClickListener;
    private String[] stringArr;
    private Map<String, String> map = new HashMap<String, String>();

    public QuickLocationListAdapter(Context context, String[] arr, OnClickListener listener, Map<String, String> map)
    {
        layoutInflater = LayoutInflater.from(context);
        this.onClickListener = listener;
        stringArr = arr;
        this.map = map;
    }

    public int getCount()
    {
        return stringArr == null ? 0 : stringArr.length;
    }

    public Object getItem(int position)
    {
        if (stringArr != null) {
            String string = map.get(stringArr[position]);
            return string;
        }
        return null;
    }

    public long getItemId(int position)
    {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_item, null);
            holder = new ViewHolder();
            holder.firstCharHintTextView = (TextView) convertView
                    .findViewById(R.id.text_first_char_hint);
            holder.nameTextView = (TextView) convertView.findViewById(R.id.text_website_name);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.nameTextView.setText(map.get(stringArr[position]));
        int idx = position - 1;
        char previewChar = idx >= 0 ? stringArr[idx].charAt(0) : ' ';  //ǰһ���ַ�
        char currentChar = stringArr[position].charAt(0);                //��ǰ�ַ�
        if (currentChar != previewChar) {                                     //������ʱ��ʾ
            if (isWord(currentChar)) {
                if (position != 0) {
                    holder.firstCharHintTextView.setVisibility(View.VISIBLE);
                }
                else {
                    holder.firstCharHintTextView.setVisibility(View.GONE);
                }
                holder.firstCharHintTextView.setText(String.valueOf(currentChar));
            }
            else {
                if (isWord(previewChar)) {
                    holder.firstCharHintTextView.setVisibility(View.VISIBLE);
                    holder.firstCharHintTextView.setText("*");
                }
                else {
                    holder.firstCharHintTextView.setVisibility(View.GONE);
                }
            }
        }
        else {
            holder.firstCharHintTextView.setVisibility(View.GONE);
        }
        return convertView;
    }

    public final class ViewHolder
    {
        public TextView firstCharHintTextView;
        public TextView nameTextView;
    }

    public boolean isWord(char c)
    {
        Pattern pattern = Pattern.compile("^[A-Za-z]+$");
        Matcher isNum = pattern.matcher(String.valueOf(c));
        if (!isNum.matches()) {
            return false;
        }
        else {
            return true;
        }
    }
}