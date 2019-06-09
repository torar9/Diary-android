package com.example.tom.diary;

import android.content.Intent;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.SharedPreferences;
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
    private String dialogOption;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_data_activity);//saveButtonClick cancelButtonClick

        SharedPreferences shr = PreferenceManager.getDefaultSharedPreferences(MainActivity.getAppContext());
        float textSize = Float.parseFloat(shr.getString("editor_text", "12"));

        etext = (EditText)findViewById(R.id.newText);
        etext.setTextSize(textSize);

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

    /**
     * Cancel current activity and return to parent activity
     */
    private void cancelActivity()
    {
        Intent intent = new Intent();
        setResult(RESULT_CANCELED, intent);
        finish();
    }

    /**
     * According to mode method saves, edist or do nothing with data. Then it cancel current
     * activity and returns to parent activity
     */
    private void reactToUser()
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

    /**
     * According to mode it saves or edits user data
     * @param v
     */
    public void saveButtonClick(View v)
    {
        if(!mode.equals("new"))
        {
            CheckDialog dialog = new CheckDialog("Overwrite?", getString(R.string.msg_check_save), this, this);
            dialogOption = "SAVE";
            dialog.show();
        }
        else
        {
            reactToUser();
        }
    }

    /**
     * Shows dialog and sks user if he really wants to delete the data
     * @param v
     */
    public void deleteButtonClick(View v)
    {
        CheckDialog dialog = new CheckDialog("Delete?", getString(R.string.msg_check_delete), this, this);
        dialogOption = "DELETE";
        dialog.show();
    }

    /**
     * Cancels current activity, does not save changes done by user
     * @param v
     */
    public void cancelButtonClick(View v)
    {
        CheckDialog dialog = new CheckDialog("Cancel?", getString(R.string.msg_check_cancel), this, this);
        dialogOption = "CANCEL";
        dialog.show();
    }

    /**
     * Reacts to cancel button in dialog
     */
    @Override
    public void onDialogCancelClick()
    {
    }

    /**
     * Reacts to ok button in dialog
     */
    @Override
    public void onDialogOkClick()
    {
        if(dialogOption.equals("DELETE"))
        {
            Intent intent = new Intent();
            intent.putExtra("text", data.getText());
            intent.putExtra("id", data.getId());
            intent.putExtra("MODE", "delete");
            setResult(RESULT_OK, intent);
            finish();
        }
        else if(dialogOption.equals("CANCEL"))
        {
            cancelActivity();
        }
        else
        {
            reactToUser();
        }
    }
}
