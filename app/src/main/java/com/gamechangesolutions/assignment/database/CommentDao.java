package com.gamechangesolutions.assignment.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.gamechangesolutions.assignment.model.CommentList;

@Dao
public interface CommentDao {
    @Query("select * from comment_table where id =:id")
    CommentList getCommentList(int id);

    @Insert
    void insetCommentList(CommentList commentList);
}
