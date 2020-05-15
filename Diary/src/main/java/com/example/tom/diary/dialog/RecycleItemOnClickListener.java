package com.example.tom.diary.dialog;

import android.view.View;

public interface RecycleItemOnClickListener
{
    /**
     * Listener for each view of corresponding to position
     * @param v
     * @param position
     */
    public void onItemClick(View v, int position);
}
