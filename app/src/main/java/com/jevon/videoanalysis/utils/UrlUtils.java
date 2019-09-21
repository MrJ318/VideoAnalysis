package com.jevon.videoanalysis.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.jevon.videoanalysis.been.AnalysisUrl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * @Author: Mr.J
 * @CreateDate: 2019/9/21 16:05
 */
public class UrlUtils {

    private static final String FILE_NAME = "url_analysis";

    public static Map getUrl(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        Map<String, String> map = new HashMap<>();
        map.put("url1", Objects.requireNonNull(sharedPreferences.getString("url1", "https://jx.618g.com/?url=")));
        map.put("url2", Objects.requireNonNull(sharedPreferences.getString("url2", "https://beaacc.com/api.php?url=")));
        map.put("url3", Objects.requireNonNull(sharedPreferences.getString("url3", "https://660e.com/?url=")));
        map.put("url4", Objects.requireNonNull(sharedPreferences.getString("url4", "http://jiexi.071811.cc/jx2.php?url=")));
        map.put("url5", Objects.requireNonNull(sharedPreferences.getString("url5", "http://www.fantee.net/fantee/?url=")));
        return map;
    }

    public static void updateUrl(final Context context) {
        final Map<String, String> map = new HashMap<>();
        BmobQuery<AnalysisUrl> bmobQuery = new BmobQuery<>();
        bmobQuery.findObjects(new FindListener<AnalysisUrl>() {
            @Override
            public void done(List<AnalysisUrl> list, BmobException e) {
                if (e != null) {
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                Log.d("Mr.J", String.valueOf(list.size()));
                if (list != null && list.size() > 0) {
                    for (AnalysisUrl url : list) {
                        map.put(url.getOrder().trim(), url.getUrl());
                    }
                }
                Log.d("Mr.J", String.valueOf(map.size()));

                if (map != null && map.size() > 0) {
                    SharedPreferences sharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    for (String name : map.keySet()) {
                        editor.putString(name, map.get(name));
                    }
                    editor.commit();
                }
                Toast.makeText(context, "成功更新：" + map.size() + "条解析地址", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
