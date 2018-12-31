package com.example.tom.diary;

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

    /**
     * Returns data ID
     * @return
     */
    public String getId()
    {
        return id;
    }

    /**
     * Returns letters from text until number of letters matches end
     * @param end
     * @return
     */
    public String getText(int end)
    {
        return getText(0, end);
    }

    /**
     * Returns text from start to end
     * @param start
     * @param end
     * @return
     */
    public String getText(int start, int end)
    {
        if(text.length() < end)
            return text;

        return text.substring(start, end);
    }

    /**
     * Returns data text
     * @param text
     */
    public void setText(String text)
    {
        this.text = text;
    }

    /**
     * Returns date of data in this format: 'HH:mm:ss dd/MM/yyyy'
     * @return
     */
    public String getDate()
    {
        int pos = id.indexOf('#');
        return id.substring(0, pos);
    }

    /**
     * Returns representation of data, maximum is 25 letters
     * @return
     */
    @Override
    public String toString()
    {
        return getText(MAX_TITLE_LENGHT);
    }

    /**
     * Generate unique ID for each data according to current time
     * @return
     */
    private String genId()
    {
        Random rand = new Random();
        int num = rand.nextInt(10001);

        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
        Date date = new Date();

        String atrb = dateFormat.format(date) + "#" + num;

        return atrb;
    }
}
