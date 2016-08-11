package com.qianfeng.qiongyou.Utils;

import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 * Created by Jimmy on 16/3/10.
 */
public class MyImageCache implements ImageLoader.ImageCache {
    //提高内存的复用性,构建当前的类为单例模式
    private static MyImageCache myImageCache;
    //内存缓存(LRU全称:Least recently use 近期使用做少的置换算法)
    //key是url的地址,保持图片唯一
    //value是Bitmap
    private static LruCache<String, Bitmap> lruCache;
    //SoftReference软引用的缓存区
   // private static LinkedHashMap<String,SoftReference<Bitmap>> cacheB;

    private MyImageCache() {

    }
    /**
     * 采用单例模式
     *
     * @return
     */
    public static MyImageCache newInstance() {
        if (myImageCache == null) {
            myImageCache = new MyImageCache();
            //要内存(分配大小)
            long maxMemory = Runtime.getRuntime().maxMemory(); //问系统要内存(当前可用)
            //正常情况分配可用内存的1/8
            int useMemory = (int) (maxMemory / 8);
            Log.d("123", "useMemory-->" + useMemory);
            lruCache = new LruCache<String, Bitmap>(useMemory) {
                //计算Bitmap存入内存的大小
                @Override
                protected int sizeOf(String key, Bitmap value) { //能够计算内存的大小改变
                    //计算图片的大小
                    return value.getByteCount();
                    //return value.getWidth() * value.getHeight() * 4;
                }
            };
        }
        return myImageCache;
    }

    //图片的获取
    @Override
    public Bitmap getBitmap(String url) {
        Bitmap bm = null;
        if (url != null) {
            bm = lruCache.get(url);
        }
        return bm;
    }

    //图片的存放
    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        //放入定义的内存去
        if (url != null) {
            lruCache.put(url, bitmap);
        }
    }
}
