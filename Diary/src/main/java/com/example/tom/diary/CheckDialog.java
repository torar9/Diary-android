package com.example.tom.diary;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;

import com.example.tom.test.R;

public class CheckDialog
{
    private DialogClickListener listener;

    CheckDialog(DialogClickListener listener)
    {
        this.listener = listener;
    }

    public void showDialog(String title, String msg, Activity activity)
    {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(activity);
        builder.setTitle(title);
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
