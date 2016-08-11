package com.qianfeng.qiongyou.Utils;

import com.alibaba.fastjson.JSONObject;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import java.io.UnsupportedEncodingException;

/**
 * Created by Jimmy on 16/3/10.
 * 定义泛型T,不写死请求体
 * 谁调用该类的,谁知道泛型T填写什么
 * GSON ,XML
 */
public class FastJsonRequest<T> extends Request<T> {
    protected final String DEFALUT_CHARSET = "utf-8";
    //定义全局的clazz由调用者决定
    protected Class<T> clazz;
    //定义成泛型T
    protected Response.Listener<T> mListener;

    /**
     * 不对外提供构造方法
     */
    private FastJsonRequest(int method, String url, Response.ErrorListener listener) {
        super(method, url, listener);
    }

    /**
     * 构造方法初始化clazz
     */
    private FastJsonRequest(int method, String url, Class<T> clazz, Response.ErrorListener listener) {
        super(method, url, listener);
        //完成了clazz的初始化
        this.clazz = clazz;
    }

    /**
     * 构造方法初始化clazz
     * 强制规定要接口实现
     */
    public FastJsonRequest(int method, String url, Class<T> clazz, Response.Listener<T> listener, Response.ErrorListener errorListener) {
        this(method, url, clazz, errorListener);
        mListener = listener;
    }

    /**
     * 默认GET请求方式
     * @param url
     * @param clazz
     * @param listener
     * @param errorListener
     */
    public FastJsonRequest(String url, Class<T> clazz, Response.Listener<T> listener, Response.ErrorListener errorListener) {
        this(Method.GET, url, clazz, listener,errorListener);
    }



    /**
     * 发起网络请求的
     *
     * @param response
     * @return
     */
    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        //结果直接通过入参返回了(最根本返回了btye[])
        //1,实际情况我们需要转换成字符串
        byte[] data = response.data;
        //千万要注意编码格式.,选用2个参数的构造方法填写编码格式
        //规范的编码格式从文件中可以获取(头文件如果没有定义,就默认utf-8)
        try {
            String s = new String(data, HttpHeaderParser.parseCharset(response.headers, DEFALUT_CHARSET));
            //解析(FastJson解析)
            T t = JSONObject.parseObject(s, clazz); //返回的就是泛型T
            /**
             * 第一个参数:返回泛型结果
             * 第二个参数:结果放入本地缓存
             */
            return Response.success(t, HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            //返回错误的信息
            return Response.error(new ParseError(e));
        }
    }

    /**
     * 返回请求的结果
     * 通过入参的方式返回结果
     * Response.Listener可以实现
     *
     * @param response
     */
    @Override
    protected void deliverResponse(T response) {
        if(mListener != null)
            mListener.onResponse(response); //通过回调接口传出参数
    }
}
