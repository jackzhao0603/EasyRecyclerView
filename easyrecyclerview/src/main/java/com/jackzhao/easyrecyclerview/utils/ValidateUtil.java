package com.jackzhao.easyrecyclerview.utils;

import android.text.TextUtils;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by jack_zhao on 2018/1/15.
 */

public class ValidateUtil {

    public static boolean isEmpty(String string) {
        return TextUtils.isEmpty(string);
    }

    public static boolean isEmpty(List list) {
        if (list == null)
            return true;
        if (list.size() == 0)
            return true;
        return false;
    }

    public static boolean isEmpty(Collection list) {
        if (list == null)
            return true;
        if (list.size() == 0)
            return true;
        return false;
    }

    public static boolean isEmpty(Set list) {
        if (list == null)
            return true;
        if (list.size() == 0)
            return true;
        return false;
    }

    public static boolean isEmpty(Object[] list) {
        if (list == null)
            return true;
        if (list.length == 0)
            return true;
        return false;
    }

    public static boolean isEmpty(int[] list) {
        if (list == null)
            return true;
        if (list.length == 0)
            return true;
        return false;
    }

    public static boolean isEmpty(Map map) {
        if (map == null)
            return true;
        if (map.size() == 0)
            return true;
        return false;
    }

    public static boolean isUrl(String urlString){
        if (isEmpty(urlString))
            return false;
        URI uri = null;
        try {
            uri = new URI(urlString);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return false;
        }

        if(uri.getHost() == null){
            return false;
        }
        if("http".equalsIgnoreCase(uri.getScheme()) || "https".equalsIgnoreCase(uri.getScheme())){
            return true;
        }
        return false;
    }

}
