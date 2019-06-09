package com.example.tom.diary;

import android.app.Activity;
import android.content.DialogInterface;
import androidx.fragment.app.DialogFragment;

import com.example.tom.test.R;

/**
 * Creates new dialog with 2 buttons and message, when you need to confirm action by user
 */
public class CheckDialog extends DialogFragment
{
    private android.app.AlertDialog.Builder builder;
    private DialogClickListener listener;

    /**
     * Creates new dialog
     * @param listener
     */
    CheckDialog(String title, String msg, Activity activity, DialogClickListener listener)
    {
        this.listener = listener;
        setDialog(title, msg, activity);
    }

    /**
     * Sets dialog
     * @param title Title of the dialog
     * @param msg Message for user
     * @param activity Current activity in which you want to display dialog
     */
    public void setDialog(String title, String msg, Activity activity)
    {
        builder = new android.app.AlertDialog.Builder(activity);
        builder.setTitle(title);//Sets title for the dialog

        //Sets messages of buttons
        builder.setMessage(msg).setPositiveButton(R.string.msg_ok, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id)
            {
                listener.onDialogOkClick();
            }
        }).setNegativeButton(R.string.msg_cancel, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int id)
            {
                listener.onDialogCancelClick();
            }
        });

        builder.create();
    }

    /**
     * Shows dialog
     */
    public void show()
    {
        builder.show();
    }
}
