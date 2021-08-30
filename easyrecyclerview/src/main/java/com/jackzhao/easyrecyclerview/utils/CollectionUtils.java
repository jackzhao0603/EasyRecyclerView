package com.jackzhao.easyrecyclerview.utils;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * Created by jack_zhao on 2018/3/30.
 */

public class CollectionUtils {

    public static int position(List collection, Object target) {
        if (collection == null || collection.size() == 0)
            return -1;
        for (int i = 0;i < collection.size();i ++) {
            if (target.equals(collection.get(i)))
                return i;
        }
        return -1;
    }

    public static <T> boolean pass(List<T> collection, @NonNull PassCallback<T> callBack) {
        if (ValidateUtil.isEmpty(collection))
            return false;
        for (int i = 0;i < collection.size(); i ++) {
            T t = collection.get(i);
            if (t != null) {
                if (!callBack.each(t))
                    return false;
            }
        }
        return true;
    }

    public static <T> void forEach(List<T> collection, @NonNull PassCallback<T> callBack) {
        if (ValidateUtil.isEmpty(collection))
            return;
        for (int i = 0;i < collection.size(); i ++) {
            T t = collection.get(i);
            if (t != null) {
                if (!callBack.each(t))
                    return;
            }
        }
    }

    public static <T> List<T> filter(List<T> collection, @NonNull PassCallback<T> callBack) {
        if (ValidateUtil.isEmpty(collection))
            return collection;
        List<T> shouldRemove = new ArrayList<>();
        for (T t:collection) {
            if (!callBack.each(t)) {
                shouldRemove.add(t);
            }
        }
        collection.removeAll(shouldRemove);
        return collection;
    }

    public static <T> Collection<T> filter(Collection<T> collection, @NonNull PassCallback<T> callBack) {
        if (ValidateUtil.isEmpty(collection))
            return collection;
        List<T> shouldRemove = new ArrayList<>();
        for (T t:collection) {
            if (!callBack.each(t)) {
                shouldRemove.add(t);
            }
        }
        collection.removeAll(shouldRemove);
        return collection;
    }

    public static <T>  List<T> subList(List<T> collection, @NonNull PassCallback<T> callBack) {
        List<T> res = new ArrayList<>();
        if (ValidateUtil.isEmpty(collection))
            return res;
        for (T t:collection) {
            if (t != null) {
                if (callBack.each(t)) res.add(t);
            }
        }
        return res;
    }

    public static <T>  Collection<T> subList(Collection<T> collection, @NonNull PassCallback<T> callBack) {
        List<T> res = new ArrayList<>();
        if (ValidateUtil.isEmpty(collection))
            return res;
        for (T t:collection) {
            if (t != null) {
                if (callBack.each(t)) res.add(t);
            }
        }
        return res;
    }

    public static <K,V> HashMap<K,V> getKeyMap(List<V> collection, @NonNull KeyCallback<K,V> callback) {
        if (ValidateUtil.isEmpty(collection))
            return null;
        HashMap<K,V> map = new HashMap<>(collection.size());
        for (V v:collection) {
            map.put(callback.key(v), v);
        }
        return map;
    }

    public static <K,V> HashMap<K,V> getKeyMap(V[] collection, @NonNull KeyCallback<K,V> callback) {
        if (ValidateUtil.isEmpty(collection))
            return null;
        HashMap<K,V> map = new HashMap<>(collection.length);
        for (V v:collection) {
            map.put(callback.key(v), v);
        }
        return map;
    }

    public static <K,V> ArrayList<K> getElemets(List<V> collection, @NonNull KeyCallback<K,V> callback) {
        if (ValidateUtil.isEmpty(collection))
            return null;
        ArrayList<K> res = new ArrayList<>(collection.size());
        for (V v:collection) {
            K k = callback.key(v);
            if (k != null) {
                res.add(callback.key(v));
            }
        }
        return res;
    }

    public static <K,V> ArrayList<K> getElemets(List<V> collection, int max, @NonNull KeyCallback<K,V> callback) {
        if (ValidateUtil.isEmpty(collection))
            return null;
        ArrayList<K> res = new ArrayList<>(collection.size());
        int i = 0;
        for (V v:collection) {
            K k = callback.key(v);
            if (k != null) {
                res.add(callback.key(v));
                i ++;
            }
            if (i > max)
                break;
        }
        return res;
    }

    @FunctionalInterface
    public interface PassCallback<T> {
        boolean each(T t);
    }

    @FunctionalInterface
    public interface KeyCallback<K,T> {
        K key(T t);
    }

}
