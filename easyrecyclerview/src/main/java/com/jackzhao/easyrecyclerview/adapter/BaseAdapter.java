package com.jackzhao.easyrecyclerview.adapter;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.jackzhao.easyrecyclerview.ViewHolder.BaseViewHolder;
import com.jackzhao.easyrecyclerview.utils.CollectionUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jack_zhao on 2018/2/5.
 */

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> implements IBaseAdapter {
    private static final String TAG = "BaseAdapter";

    protected Map<Integer, Class> viewHolderTypeMap = new HashMap<>();
    protected Map<Integer, ViewHolderGenerator> viewHolderGeneratorMap = new HashMap<>();

    protected List<T> dataList = new ArrayList<>();


    public BaseAdapter() {
        super();
        registerType();
    }


    public Class getViewHolderTypeByDataType(int dataType) {
        return viewHolderTypeMap.get(dataType);
    }


    public interface ItemChangedListener<T> {
        void itemMoved(int fromPosition, int toPosition);

        void itemSwiped(int pos, T data);
    }

    public abstract void registerType();


    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        if (viewType == -1) {
            return null;
        }

        ViewHolderGenerator generator = viewHolderGeneratorMap.get(viewType);

        if (generator != null) {
            return generator.getViewHolder(parent);
        }

        Class clazz = viewHolderTypeMap.get(viewType);
        BaseViewHolder viewHolder = null;
        try {
            Constructor c = clazz.getConstructor(ViewGroup.class);
            viewHolder = (BaseViewHolder) c.newInstance(parent);

        } catch (Exception e) {
            Log.e(TAG, "onCreateViewHolder: ", e);
            throw new RuntimeException(e);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.bindDataSafe(dataList.get(position));
    }

    //has bug
    @Override
    public int getItemViewType(int position) {
        if (dataList.size() <= position || position < 0) {
            return -1;
        }
        Log.d(TAG, "getItemViewType: " + dataList.get(position).getClass() + "-->" + dataList.get(position).getClass().hashCode());
        return dataList.get(position).getClass().hashCode();
    }

    public Object removeItemWithAnimation(Object target) {
        return removeItemWithAnimation(CollectionUtils.position(getDataList(), target));
    }

    public Object removeItemWithAnimation(int position) {
        if (position < 0)
            return null;
        Object o = getDataList().remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getDataList().size() - position);
        return o;
    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }


    public List bindData(Context context, List dataList) {
        this.dataList = dataList;
        ListProxy proxy = new ListProxy();
        proxy.newProxyInstance(dataList,
                () -> new Handler(context.getMainLooper()).post(() -> this.notifyDataSetChanged()));
        List resultList = (List<T>) proxy.getProxyInstance();
        return resultList;
    }

    @Override
    public List<T> getDataList() {
        return dataList;
    }


    public <T> void addHolderGenerator(Class<T> dataType, ViewHolderGenerator<T> viewHolderGenerator) {
        viewHolderGeneratorMap.put(dataType.hashCode(), viewHolderGenerator);
    }

    public <T> void addHolderGenerator(int key, ViewHolderGenerator<T> viewHolderGenerator) {
        viewHolderGeneratorMap.put(key, viewHolderGenerator);
    }

    @FunctionalInterface
    public interface ViewHolderGenerator<T> {
        BaseViewHolder<T> getViewHolder(ViewGroup parent);
    }

    @Override
    public boolean isPrivatePosition(int position) {
        return true;
    }

    @Override
    public int getInnerPosition(int position) {
        return position;
    }

    @Override
    public int getOuterPosition(int position) {
        return position;
    }

    @Override
    public void registerViewHolderType(Class data, Class<BaseViewHolder> viewHolder) {
        viewHolderTypeMap.put(data.hashCode(), viewHolder);
    }


    interface DataListener {
        void onDataChanged();
    }

    class ListProxy implements InvocationHandler {
        private List realObject;
        private DataListener listener;
        private Handler handler = new Handler();
        private Runnable  runnable = () -> listener.onDataChanged();

        public Object newProxyInstance(List realObject, DataListener listener) {
            this.realObject = realObject;
            this.listener = listener;
            return Proxy.newProxyInstance(realObject.getClass().getClassLoader(),
                    realObject.getClass().getInterfaces(), this);
        }

        public Object getProxyInstance() {
            return Proxy.newProxyInstance(realObject.getClass().getClassLoader(),
                    realObject.getClass().getInterfaces(), this);
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] objects) throws Throwable {
            Log.e(TAG, "invoke: " + method);
            Object result = method.invoke(realObject, objects);
            if (method.getName().equals("set")) {
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable, 100);

            }
            return result;
        }
    }
}
