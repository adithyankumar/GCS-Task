package com.gamechangesolutions.assignment.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.gamechangesolutions.assignment.utils.CommentListConverter;

import java.io.Serializable;
import java.util.ArrayList;

@Entity(tableName = "comment_table")
public class CommentList implements Serializable {
    @PrimaryKey
    private int id;
    @TypeConverters(CommentListConverter.class)
    @ColumnInfo(name = "comment_list")
    private ArrayList<Comment> commentList;

    public CommentList(int id, ArrayList<Comment> commentList) {
        this.id = id;
        this.commentList = commentList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(ArrayList<Comment> commentList) {
        this.commentList = commentList;
    }
}
