package com.jude.easyrecyclerview;

import androidx.recyclerview.widget.RecyclerView;

import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

public class EasyDataObserver extends RecyclerView.AdapterDataObserver {
    private EasyRecyclerView recyclerView;
    private RecyclerArrayAdapter adapter;

    public EasyDataObserver(EasyRecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        if (recyclerView.getAdapter() instanceof RecyclerArrayAdapter) {
            adapter = (RecyclerArrayAdapter) recyclerView.getAdapter();
        }
    }

    private boolean isHeaderFooter(int position) {
        return adapter != null && (position < adapter.getHeaderCount() || position >= adapter.getHeaderCount() + adapter.getCount());
    }

    @Override
    public void onItemRangeChanged(int positionStart, int itemCount) {
        super.onItemRangeChanged(positionStart, itemCount);
        if (!isHeaderFooter(positionStart)) {
            update();
        }
    }

    @Override
    public void onItemRangeInserted(int positionStart, int itemCount) {
        super.onItemRangeInserted(positionStart, itemCount);
        if (!isHeaderFooter(positionStart)) {
            update();
        }
    }

    @Override
    public void onItemRangeRemoved(int positionStart, int itemCount) {
        super.onItemRangeRemoved(positionStart, itemCount);
        if (!isHeaderFooter(positionStart)) {
            update();
        }
    }

    @Override
    public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
        super.onItemRangeMoved(fromPosition, toPosition, itemCount);
        update();//header&footer不会有移动操作
    }

    @Override
    public void onChanged() {
        super.onChanged();
        update();//header&footer不会引起changed
    }


    //自动更改Container的样式
    private void update() {
        int count;
        if (recyclerView.getAdapter() instanceof RecyclerArrayAdapter) {
            RecyclerArrayAdapter adapter = ((RecyclerArrayAdapter) recyclerView.getAdapter());
            // 有Header Footer就不显示Empty,但排除EventFooter。
            count = adapter.getCount()+adapter.getHeaderCount()+adapter.getFooterCount()-(adapter.hasEventFooter()?1:0);
        } else {
            count = recyclerView.getAdapter().getItemCount();
        }
        if (count == 0) {
            recyclerView.showEmpty();
        } else {
            recyclerView.showRecycler();
        }
    }
}
