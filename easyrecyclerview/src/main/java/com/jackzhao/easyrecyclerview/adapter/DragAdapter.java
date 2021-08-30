package com.jackzhao.easyrecyclerview.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.jackzhao.easyrecyclerview.ViewHolder.BaseViewHolder;
import com.jackzhao.easyrecyclerview.utils.CollectionUtils;
import com.jackzhao.easyrecyclerview.utils.ItemTouchHelper;
import com.jackzhao.easyrecyclerview.utils.ValidateUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TimerTask;

/**
 * Created by jackzhao on 2018/4/19.
 */

public class DragAdapter extends RecyclerView.Adapter implements IBaseAdapter {
    private static final String TAG = "DragAdapter";
    BaseAdapter mAdapter = null;
    Context context;

    TimerTask task;
    private ItemTouchHelper itemTouchHelper = null;
    private boolean canItemMove = false;
    private List<Integer> posChangelessType = new ArrayList<>();

    private ItemTouchHelper.ItemOperationListener itemOperationListener;

    public void setItemOperationListener(ItemTouchHelper.ItemOperationListener itemOperationListener) {
        this.itemOperationListener = itemOperationListener;
    }



    public void setOriginalAdpater(BaseAdapter originalAdpater) {
        mAdapter = originalAdpater;
        mAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public boolean isPrivatePosition(int position) {
        return false;
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
    public List getDataList() {
        return mAdapter.getDataList();
    }

    @Override
    public void registerViewHolderType(Class data, Class<BaseViewHolder> viewHolder) {

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder viewHolder = mAdapter.onCreateViewHolder(parent, viewType);
//        viewHolder.getView().setOnTouchListener((v, event) -> {
//
//            for (int dataType : posChangelessType) {
//                if (mAdapter.getViewHolderTypeByDataType(dataType) == viewHolder.getClass()) {
//                    canItemMove = false;
//                    return false;
//                }
//            }
//
//            canItemMove = true;
//            return false;
//        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: " + mAdapter);
        mAdapter.onBindViewHolder((BaseViewHolder) holder, position);
    }

    @Override
    public int getItemCount() {
        return mAdapter.getItemCount();
    }

    @Override
    public int getItemViewType(int position) {
        return mAdapter.getItemViewType(position);
    }

    public DragAdapter addTouchHelperRecyclerView(RecyclerView recyclerView, BaseAdapter.ItemChangedListener listener) {
        addTouchHelperRecyclerView(recyclerView, listener, true, true);
        context = recyclerView.getContext();
        return this;
    }


    public DragAdapter addTouchHelperRecyclerView(RecyclerView recyclerView, BaseAdapter.ItemChangedListener listener, boolean dragEnable, boolean swipeEnable) {
        int dragDirs = 0;
        int swipeDirs = 0;
        if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
            if (dragEnable) {
                dragDirs = ItemTouchHelper.UP | ItemTouchHelper.DOWN |
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            }
        } else {
            if (dragEnable) {
                dragDirs = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            }
            if (swipeEnable) {
                swipeDirs = ItemTouchHelper.LEFT;
            }
        }

        itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(dragDirs, swipeDirs) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder target) {
                int fromPosition = viewHolder.getAdapterPosition();
                int toPosition = target.getAdapterPosition();
                if (fromPosition < toPosition) {
                    for (int i = fromPosition; i < toPosition; i++) {
                        Collections.swap(mAdapter.getDataList(), mAdapter.getOuterPosition(i),
                                mAdapter.getOuterPosition(i + 1));
                    }
                } else {
                    for (int i = fromPosition; i > toPosition; i--) {
                        Collections.swap(mAdapter.getDataList(), mAdapter.getOuterPosition(i),
                                mAdapter.getOuterPosition(i - 1));
                    }
                }
                notifyItemMoved(fromPosition, toPosition);
                if (!isPrivatePosition(fromPosition) || !isPrivatePosition(toPosition)) {
                    listener.itemMoved(getInnerPosition(fromPosition), getInnerPosition(toPosition));
                }
                return true;
            }

            @Override
            public boolean canDropOver(RecyclerView recyclerView, RecyclerView.ViewHolder current, RecyclerView.ViewHolder target) {
                int cPos = current.getAdapterPosition();
                int tPos = target.getAdapterPosition();
                if (!canMove(cPos, current) || !canMove(tPos, current)) {
                    return false;
                }
                return super.canDropOver(recyclerView, current, target);
            }

//            @Override
//            public boolean isItemViewSwipeEnabled() {
//                if (canItemMove) {
//                    if (task != null) {
//                        task.cancel();
//                    }
//                    task = new TimerTask() {
//                        @Override
//                        public void run() {
//                            canItemMove = false;
//                        }
//                    };
//                    Timer timer = new Timer();
//                    timer.schedule(task, 500);
//                    return true;
//                }
//                return false;
//            }
//
//            @Override
//            public boolean isLongPressDragEnabled() {
//                return isItemViewSwipeEnabled();
//            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int pos = viewHolder.getAdapterPosition();
//                if (isPrivatePosition(pos) || !canMove(pos, null)) {
//                    notifyDataSetChanged();
//                    return;
//                }
                Object data = removeDataWithAnim(pos);
                listener.itemSwiped(getInnerPosition(pos), data);
            }
        }).setOperationListener(new ItemTouchHelper.ItemOperationListener() {
            @Override
            public boolean canDrag(RecyclerView.ViewHolder viewHolder) {
                return !posChangelessType.contains(viewHolder.getItemViewType()) && (itemOperationListener == null || itemOperationListener.canDrag(viewHolder));
            }

            @Override
            public boolean canSwipe(RecyclerView.ViewHolder viewHolder) {
                return !posChangelessType.contains(viewHolder.getItemViewType()) && (itemOperationListener == null || itemOperationListener.canSwipe(viewHolder));
            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView);
        return this;
    }

    private boolean canMove(int pos, RecyclerView.ViewHolder current) {
        if (posChangelessType.contains(getItemViewType(pos))) {
            return false;
        }
        if (isPrivatePosition(pos)) {
            return false;
        }
        return true;
    }

    public Object removeDataWithAnim(Object target) {
        if (ValidateUtil.isEmpty(mAdapter.getDataList())) {
            return null;
        }
        return removeDataWithAnim(CollectionUtils.position(mAdapter.getDataList(), target));
    }


    public Object removeDataWithAnim(int index) {
        if (mAdapter.getDataList().size() <= index) {
            return null;
        }
        Object data = mAdapter.getDataList().remove(mAdapter.getOuterPosition(index));
        notifyItemRemoved(index);
        notifyItemRangeChanged(index, mAdapter.getDataList().size() - index);
        return data;
    }


    public void addPosChangelessType(int viewType) {
        posChangelessType.add(viewType);
    }

}
