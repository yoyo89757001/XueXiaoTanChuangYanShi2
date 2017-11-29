package com.example.xuexiaotanchuangyanshi.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.example.xuexiaotanchuangyanshi.interfaces.RecytviewCash;

/**
 * Created by Administrator on 2017/6/29.
 */

public class WrapContentLinearLayoutManager extends LinearLayoutManager {
    private RecytviewCash recytviewCash;
    public WrapContentLinearLayoutManager(Context context) {
        super(context);
        this.recytviewCash=recytviewCash;
    }

    public WrapContentLinearLayoutManager(Context context, int orientation, boolean reverseLayout,RecytviewCash recytviewCash) {

        super(context, orientation, reverseLayout);
        this.recytviewCash=recytviewCash;
    }

    public WrapContentLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        try {
            super.onLayoutChildren(recycler, state);
        } catch (IndexOutOfBoundsException e) {
            recytviewCash.reset();
        }
    }
}