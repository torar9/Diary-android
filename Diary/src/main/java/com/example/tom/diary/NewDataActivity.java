package com.example.tom.diary;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.tom.test.R;

public class NewDataActivity extends AppCompatActivity implements DialogClickListener
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
        CheckDialog dialog = new CheckDialog(this);
        dialog.showDialog("", getString(R.string.msg_check_delete), this);
    }

    public void cancelButtonClick(View v)
    {
        cancelActivity();
    }

    @Override
    public void onDialogCancelClick()
    {
    }

    @Override
    public void onDialogOkClick()
    {
        Intent intent = new Intent();
        intent.putExtra("text", data.getText());
        intent.putExtra("id", data.getId());
        intent.putExtra("MODE", "delete");
        setResult(RESULT_OK, intent);
        finish();
    }
}
