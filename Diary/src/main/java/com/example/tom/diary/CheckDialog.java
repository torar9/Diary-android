package com.example.tom.diary;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v4.app.DialogFragment;

import com.example.tom.test.R;

public class CheckDialog extends DialogFragment
{
    private DialogClickListener listener;

    CheckDialog(DialogClickListener listener)
    {
        this.listener = listener;
    }

    /**
     * Creates and shows new dialog
     * @param title Title of the dialog
     * @param msg Message for user
     * @param activity Current activity in which you want to display dialog
     */
    public void showDialog(String title, String msg, Activity activity)
    {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(activity);
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

        builder.create().show();
    }
}
