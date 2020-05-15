package com.example.tom.test;

import com.example.tom.diary.data.UserData;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class UserDataTest
{
    @Test
    public void toString_equals()
    {
        UserData data = new UserData("Some string that exceeds length of 25 characters");
        String title = data.toString();
        assertEquals("Some string that exceeds ", title);
    }

    @Test
    public void getDate_equals()
    {
        UserData data = new UserData("Some string that exceeds length of 25 characters", "14:06:47 10/06/2019#4504");
        String date = data.getDate();
        assertEquals("14:06:47 10/06/2019", date);
    }

    @Test
    public void getText_return5CharactersFromStart()
    {
        UserData data = new UserData("Some string that exceeds length of 25 characters", "14:06:47 10/06/2019#4504");
        String date = data.getText(5);
        assertEquals("Some ", date);
    }

    @Test
    public void getText_return5CharactersFrom10Offset()
    {
        UserData data = new UserData("Some string that exceeds length of 25 characters", "14:06:47 10/06/2019#4504");
        String date = data.getText(5, 10);
        assertEquals("strin", date);
    }

    @Test
    public void getText_return5CharactersFrom5Offset_emptyString()
    {
        UserData data = new UserData("Some string that exceeds length of 25 characters", "14:06:47 10/06/2019#4504");
        String date = data.getText(5, 5);
        assertEquals("", date);
    }

    @Test(expected = StringIndexOutOfBoundsException.class)
    public void getText_return10CharactersFrom5Offset_throwException()
    {
        UserData data = new UserData("Some string that exceeds length of 25 characters", "14:06:47 10/06/2019#4504");
        String date = data.getText(10, 5);
    }
}
