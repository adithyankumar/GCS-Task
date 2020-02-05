package com.gamechangesolutions.assignment.database;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.gamechangesolutions.assignment.model.CommentList;
import com.gamechangesolutions.assignment.model.Issue;
import com.gamechangesolutions.assignment.utils.Constants;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@androidx.room.Database(entities = {Issue.class, CommentList.class}, version = 1, exportSchema = false)
public abstract class GCSDatabase extends RoomDatabase {
    public abstract IssueDao getIssueDao();

    public abstract CommentDao getCommentDao();

    private static volatile GCSDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService dbExecutorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static GCSDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (GCSDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), GCSDatabase.class, Constants.DB_NAME)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
