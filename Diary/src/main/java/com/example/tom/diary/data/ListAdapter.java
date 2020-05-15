package com.example.tom.diary.data;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tom.diary.dialog.RecycleItemOnClickListener;
import com.example.tom.diary.activities.MainActivity;
import com.example.tom.test.R;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder>
{
    private ArrayList<UserData> mDataset;//ArrayList of stored user data
    private RecyclerView list;
    private RecycleItemOnClickListener clickListener;
    private IDatabase db;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public TextView title;
        public TextView date;

        @SuppressLint("WrongConstant")
        public ViewHolder(View v)
        {
            super(v);

            SharedPreferences shr = PreferenceManager.getDefaultSharedPreferences(MainActivity.getAppContext());
            float titleSize = Float.parseFloat(shr.getString("title_text", "34"));
            float dateSize = Float.parseFloat(shr.getString("date_text", "14"));
            boolean isCentered = shr.getString("gravity_text", "CENTER").equals("CENTER");

            title = (TextView) v.findViewById(R.id.ItemContent);
            title.setTextSize(titleSize);

            if(isCentered)
            {
                title.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
            }
            else
            {
                title.setGravity(Gravity.CENTER_HORIZONTAL);
                title.setTextAlignment(Gravity.RIGHT);
            }
            date = (TextView) v.findViewById(R.id.DateText);
            date.setTextSize(dateSize);
            date.setKeyListener(null);

            v.setOnClickListener(this);
        }

        /**
         * On click listener for each view in RecyclerView
         * @param view
         */
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

    /**
     * Creates new ListAdapter, sets RecyclerView and its manager
     * @param context
     * @param recManager manager for RecyclerView
     * @param list
     * @throws Exception
     */
    public ListAdapter(Context context, RecyclerView.LayoutManager recManager, RecyclerView list) throws Exception
    {
        this.list = list;
        list.setLayoutManager(recManager);
        list.setAdapter(this);
        list.setHasFixedSize(true);

        db = new Database(context);
        mDataset = db.getContent();
    }

    /**
     * Edits corresponding data according to (String) data.getId()
     * @param data
     * @throws Exception
     */
    public void editData(UserData data) throws Exception
    {
        this.removeData(data);
        this.addData(data);
    }

    /**
     * Creates new ViewHolder
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);
        ViewHolder vh = new ViewHolder(view);

        return vh;
    }

    /**
     * Sets holder on bind
     * @param holder
     * @param position Position of holder
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        holder.title.setText(mDataset.get(position).toString().replace("\n", " "));
        holder.date.setText(mDataset.get(position).getDate());

        //Sets different color for odd and even rows
        if(position % 2 == 0)
        {
            holder.itemView.setBackgroundColor(Color.parseColor("#ffffff"));
        }
        else
        {
            holder.itemView.setBackgroundColor(Color.parseColor("#eaeaea"));
        }
    }

    /**
     * Returns number of stored data
     * @return
     */
    @Override
    public int getItemCount()
    {
        return mDataset.size();
    }

    /**
     * Adds new data
     * @param data
     * @throws Exception
     */
    public void addData(UserData data) throws Exception
    {
        db.addData(data);//Adds new data into file
        mDataset.add(data);//Adds data into database
        this.notifyItemInserted(mDataset.size() - 1);//Notify RecyclerView about changes
    }

    /**
     * Sets click listener for RecycleItem
     * @param clickListener
     */
    public void setClickListener(RecycleItemOnClickListener clickListener)
    {
        this.clickListener = clickListener;
    }

    /**
     * Removes data from RecyclerView and file
     * @param data
     * @throws Exception
     */
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

    /**
     * Return current data at position
     * @param pos positon of data
     * @return
     */
    public UserData getData(int pos)
    {
        return mDataset.get(pos);
    }

    /**
     * Return current position of data stored in mDataset
     * @param data
     * @return
     */
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
