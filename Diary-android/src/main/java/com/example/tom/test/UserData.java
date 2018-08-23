package com.example.tom.test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * The class is used to pack the user datas.
 * @author Tomáš Silber
 */
public class UserData
{
    private String text;
    private String id;
    private static final int MAX_TITLE_LENGHT = 25;

    /**
     * Constructor which sets name of the data and text
     * @param text String content
     */
    public UserData(String text, String id)
    {
        this.text = text;
        this.id = id;
    }

    public UserData(String text)
    {
        this.text = text;
        this.id = genId();
    }

    /**
     * Returns saved text
     * @return text
     */
    public String getText()
    {
        return text;
    }

    public String getId()
    {
        return id;
    }

    public String getText(int end)
    {
        return getText(0, end);
    }

    public String getText(int start, int end)
    {
        if(text.length() < end)
            return text;

        return text.substring(start, end);
    }

    public void setText(String text)
    {
        this.text = text;
    }

    @Override
    public String toString()
    {
        return getText(MAX_TITLE_LENGHT);
    }

    private String genId()
    {
        Random rand = new Random();
        int num = rand.nextInt(10001);

        DateFormat dateFormat = new SimpleDateFormat("HH:mm::ss_dd/MM/yyyy");
        Date date = new Date();

        String atrb = dateFormat.format(date) + "#" + num;

        return atrb;
    }
}
