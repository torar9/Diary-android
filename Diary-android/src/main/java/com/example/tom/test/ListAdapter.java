package com.example.tom.test;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder>
{
    private ArrayList<UserData> mDataset;
    private RecyclerView list;
    private RecyclerView.LayoutManager RecManager;
    private recycleItemOnClickListener clickListener;
    private Database db;

    public class ViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener
    {
        public TextView title;
        private recycleItemOnClickListener OnClickListener;

        public ViewHolder(View v)
        {
            super(v);
            title = (TextView) v.findViewById(R.id.ItemTitle);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View view)
        {
            if(clickListener != null)
            {
                int pos = list.getChildAdapterPosition(view);
                clickListener.onItemClick(view, pos);
            }
        }
    }

    public ListAdapter(Context context, RecyclerView.LayoutManager RecManager, RecyclerView list) throws Exception
    {
        this.list = list;
        list.setLayoutManager(RecManager);
        list.setAdapter(this);
        list.setHasFixedSize(true);

        db = new Database(context);
        mDataset = db.getContent();
    }

    public void editData(UserData data) throws Exception
    {
        this.removeData(data);
        this.addData(data);
    }

    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);
        ViewHolder vh = new ViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        holder.title.setText(mDataset.get(position).toString());

        if(position % 2 == 0)
        {
            holder.itemView.setBackgroundColor(Color.parseColor("#ffffff"));
        }
        else
        {
            holder.itemView.setBackgroundColor(Color.parseColor("#eaeaea"));
        }
    }

    @Override
    public int getItemCount()
    {
        return mDataset.size();
    }

    public void addData(UserData data) throws Exception
    {
        db.addData(data);
        mDataset.add(data);
        this.notifyItemInserted(mDataset.size() - 1);
    }

    public void setClickListener(recycleItemOnClickListener clickListener)
    {
        this.clickListener = clickListener;
    }

    public void removeData(UserData data) throws Exception
    {
        int pos = getPos(data);
        if(pos < 0)
            throw new Exception("Unable to find item");

        db.removeData(data);
        mDataset.remove(pos);
        this.notifyItemRemoved(pos);
        this.notifyItemRangeChanged(pos, mDataset.size());
    }

    public UserData getData(int pos)
    {
        return mDataset.get(pos);
    }

    private int getPos(UserData data)
    {
        for(int i = 0; i < mDataset.size(); i++)
        {
            if(mDataset.get(i).getId().equals(data.getId()))
                return i;
        }

        return -1;
    }
}
