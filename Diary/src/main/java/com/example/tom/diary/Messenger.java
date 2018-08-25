package com.example.tom.diary;

import android.content.Context;
import android.widget.Toast;

public class Messenger
{
    public static void PostMessage(Context context, String message)
    {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
