package com.example.tom.diary.data;

import java.util.ArrayList;

/**
 * Interface for database, contains basic operations for adding, removing, restoring user data.
 * Methods throws Exception when user input is not valid.
 * Exception contains message for user.
 * @author Tomáš Silber
 */
public interface IDatabase
{
    /**
     * Adds new user data into
     * @param data
     * @throws Exception if something went wrong, contains message for user.
     */
    void addData(UserData data) throws Exception;

    /**
     * Replaces the old data with new data.
     * @param newData new data
     * @throws Exception if something went wrong, contains message for user.
     */
    void editData(UserData newData) throws Exception;

    /**
     * Removes user data.
     * @param data data to be removed
     * @throws Exception if something went wrong, contains message for user.
     */
    void removeData(UserData data) throws Exception;

    ArrayList<UserData> getContent() throws Exception;
}
