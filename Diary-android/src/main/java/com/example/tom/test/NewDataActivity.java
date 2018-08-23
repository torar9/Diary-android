package com.example.tom.test;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class NewDataActivity extends AppCompatActivity
{
    private EditText etext;
    private FloatingActionButton floatingRemoveButton;
    private String mode;
    private UserData data;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_data_activity);//saveButtonClick cancelButtonClick
        etext = (EditText)findViewById(R.id.newText);
        floatingRemoveButton = (FloatingActionButton)findViewById(R.id.floatingDeleteButton);

        Intent intent = getIntent();
        mode = intent.getStringExtra("MODE");
        System.out.println("The mode is: " + mode);

        switch(mode)
        {
            case "new":
                floatingRemoveButton.hide();
                break;
            case "edit":
                String text = intent.getStringExtra("text");
                String id = intent.getStringExtra("id");
                data = new UserData(text, id);
                break;
            default:
                floatingRemoveButton.hide();
                break;
        }

        String text = getIntent().getStringExtra("text");

        if(text != null)
        {
            etext.setText(text, TextView.BufferType.EDITABLE);
        }

    }

    private void cancelActivity()
    {
        Intent intent = new Intent();
        setResult(RESULT_CANCELED, intent);
        finish();
    }

    public void saveButtonClick(View v)
    {
        switch(mode)
        {
            case "new":
            {
                String text = etext.getText().toString();
                if(!text.isEmpty())
                {
                    Intent intent = new Intent();
                    intent.putExtra("text", text);
                    intent.putExtra("MODE", "new");
                    setResult(RESULT_OK, intent);
                    finish();
                }
                else
                {
                    cancelActivity();
                }
            }
                break;

            case "edit":
            {
                String text = etext.getText().toString();
                String id = data.getId();

                if(text != null || !text.isEmpty())
                {
                    Intent intent = new Intent();
                    intent.putExtra("text", text);
                    intent.putExtra("id", id);
                    intent.putExtra("MODE", "edit");
                    setResult(RESULT_OK, intent);
                    finish();
                }
                else
                {
                    cancelActivity();
                }
            }
                break;

            default:
                cancelActivity();
                break;
        }
    }

    public void deleteButtonClick(View v)
    {
        System.out.println("delete button new");
        Intent intent = new Intent();
        intent.putExtra("text", data.getText());
        intent.putExtra("id", data.getId());
        intent.putExtra("MODE", "delete");
        setResult(RESULT_OK, intent);
        finish();
    }

    public void cancelButtonClick(View v)
    {
        cancelActivity();
    }
}
