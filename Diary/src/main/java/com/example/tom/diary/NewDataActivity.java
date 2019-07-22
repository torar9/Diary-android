package com.example.tom.diary;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import com.example.tom.test.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class NewDataActivity extends AppCompatActivity implements DialogClickListener
{
    private EditText etext;
    private FloatingActionButton floatingRemoveButton;
    private IntentOption mode;
    private UserData data;
    private IntentOption dialogOption;

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
        mode = (IntentOption) intent.getSerializableExtra("MODE");

        switch(mode)
        {
            case NEW:
                floatingRemoveButton.hide();
                break;
            case EDIT:
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
            case NEW:
            {
                String text = etext.getText().toString();
                if(!text.isEmpty())
                {
                    Intent intent = new Intent();
                    intent.putExtra("text", text);
                    intent.putExtra("MODE", IntentOption.NEW);
                    setResult(RESULT_OK, intent);
                    finish();
                }
                else
                {
                    cancelActivity();
                }
            }
            break;

            case EDIT:
            {
                String text = etext.getText().toString();
                String id = data.getId();

                if(text != null && !text.isEmpty())
                {
                    Intent intent = new Intent();
                    intent.putExtra("text", text);
                    intent.putExtra("id", id);
                    intent.putExtra("MODE", IntentOption.EDIT);
                    setResult(RESULT_OK, intent);
                    finish();
                }
                else//Text is empty, delete it instead of overwrite to empty text
                {
                    finishIntentWithDelete();
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
        if(mode != IntentOption.NEW)
        {
            CheckDialog dialog = new CheckDialog("Overwrite?", getString(R.string.msg_check_save), this, this);
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
        dialogOption = IntentOption.DELETE;
        dialog.show();
    }

    /**
     * Cancels current activity, does not save changes done by user
     * @param v
     */
    public void cancelButtonClick(View v)
    {
        CheckDialog dialog = new CheckDialog("Cancel?", getString(R.string.msg_check_cancel), this, this);
        dialogOption = IntentOption.CANCEL;
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
     * Finishes intent and pass DELETE mode
     */
    private void finishIntentWithDelete()
    {
        Intent intent = new Intent();
        intent.putExtra("text", data.getText());
        intent.putExtra("id", data.getId());
        intent.putExtra("MODE", IntentOption.DELETE);
        setResult(RESULT_OK, intent);
        finish();
    }

    /**
     * Reacts to ok button in dialog
     */
    @Override
    public void onDialogOkClick()
    {
        if(dialogOption == IntentOption.DELETE)
        {
            finishIntentWithDelete();
        }
        else if(dialogOption == IntentOption.CANCEL)
        {
            cancelActivity();
        }
        else
        {
            reactToUser();
        }
    }
}
