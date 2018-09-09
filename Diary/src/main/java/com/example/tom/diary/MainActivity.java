package com.example.tom.diary;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.tom.test.R;

public class MainActivity extends AppCompatActivity implements RecycleItemOnClickListener
{
    private static Context context;
    private static ListAdapter lad;
    private FloatingActionButton floatingNewButton;
    private FloatingActionButton floatingRemoveButton;
    private FloatingActionButton floatingSettingsButton;
    private boolean isInDeleteMode;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainActivity.context = getApplicationContext();

        floatingNewButton = (FloatingActionButton)findViewById(R.id.floatingSaveButton);
        floatingSettingsButton = (FloatingActionButton)findViewById(R.id.floatingSettingsButton);
        floatingSettingsButton.hide();
        floatingRemoveButton = (FloatingActionButton)findViewById(R.id.floatingDeleteInListButton);
        floatingRemoveButton.hide();
        isInDeleteMode = false;

        try
        {
            lad = new ListAdapter
                    (
                            MainActivity.getAppContext(),
                            new LinearLayoutManager(this),
                            (RecyclerView) findViewById(R.id.list)
                    );
        }
        catch(Exception e)
        {
            Messenger.showError("", e.getMessage(), this);
        }

        lad.setClickListener(this);
    }

    public void newButtonClick(View v)
    {
        Intent intent = new Intent(MainActivity.this, NewDataActivity.class);
        intent.putExtra("MODE", "new");
        this.startActivityForResult(intent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1)
        {
            if(resultCode == RESULT_OK)
            {
                String mode = data.getStringExtra("MODE");

                if(mode.equals("new"))
                {
                    try
                    {
                        String text = data.getStringExtra("text");
                        lad.addData(new UserData(text));
                    }
                    catch(Exception e)
                    {
                        Messenger.showError("", e.getMessage(), this);
                    }
                }
                else if(mode.equals("edit"))
                {
                    String text = data.getStringExtra("text");
                    String id = data.getStringExtra("id");

                    try
                    {
                        lad.editData(new UserData(text, id));
                    }
                    catch(Exception e)
                    {
                        Messenger.showError("", e.getMessage(), this);
                    }
                }
                else if(mode.equals("delete"))
                {
                    String text = data.getStringExtra("text");
                    String id = data.getStringExtra("id");

                    try
                    {
                        lad.removeData(new UserData(text, id));
                    }
                    catch(Exception e)
                    {
                        Messenger.showError("", e.getMessage(), this);
                    }
                }
            }
        }
    }

    public void SettingsButtonClick(View v)
    {//to be done...
        Messenger.showError("","xD", this);
    }

    public void deleteInListButtonClick(View v)
    {//to be done...
    }

    public static ListAdapter getAdapter()
    {
        return lad;
    }

    public static Context getAppContext()
    {
        return MainActivity.context;
    }

    @Override
    public void onItemClick(View v, int position)
    {
        UserData data = lad.getData(position);

        Intent intent = new Intent(MainActivity.this, NewDataActivity.class);
        intent.putExtra("text", data.getText());
        intent.putExtra("id", data.getId());
        intent.putExtra("MODE", "edit");
        this.startActivityForResult(intent, 1);
    }
}
