package com.example.tom.test;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class MainActivity extends AppCompatActivity implements recycleItemOnClickListener
{
    private static Context context;
    private static ListAdapter lad;
    private FloatingActionButton floatingNewButton;
    private FloatingActionButton floatingRemoveButton;
    private boolean isInDeleteMode;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainActivity.context = getApplicationContext();

        floatingNewButton = (FloatingActionButton)findViewById(R.id.floatingSaveButton);
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
            System.out.println(e.getMessage());
        }

        lad.setClickListener(this);
    }

    public void newButtonClick(View v)
    {
        System.out.println("New Button :)");

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
                    System.out.println("main new");
                    try
                    {
                        String text = data.getStringExtra("text");
                        lad.addData(new UserData(text));
                    }
                    catch(Exception e)
                    {
                        System.out.println(e.getMessage());
                    }
                }
                else if(mode.equals("edit"))
                {
                    System.out.println("main edit");
                    String text = data.getStringExtra("text");
                    String id = data.getStringExtra("id");

                    try
                    {
                        System.out.println("edit in main text: " + text + " id: " + id);
                        lad.editData(new UserData(text, id));
                    }
                    catch(Exception e)
                    {
                        System.out.println(e.getMessage());
                    }
                }
                else if(mode.equals("delete"))
                {
                    System.out.println("main delete");
                    String text = data.getStringExtra("text");
                    String id = data.getStringExtra("id");

                    try
                    {
                        lad.removeData(new UserData(text, id));
                    }
                    catch(Exception e)
                    {
                        System.out.println(e.getMessage());
                    }
                }
            }
        }
    }

    public void deleteInListButtonClick(View v)
    {
        System.out.println("Remove Button :)");
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
