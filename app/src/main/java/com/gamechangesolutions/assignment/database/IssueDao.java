package com.gamechangesolutions.assignment.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.gamechangesolutions.assignment.model.Issue;

import java.util.List;

@Dao
public interface IssueDao {
    @Query("select * from issue_table where state =:state order by updated_at desc")
    List<Issue> getIssueListBasedState(String state);

    @Insert
    void insertIssueList(List<Issue> issueList);
}
