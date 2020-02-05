package com.gamechangesolutions.assignment.network;


import com.gamechangesolutions.assignment.model.Comment;
import com.gamechangesolutions.assignment.model.Issue;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DataService {

    @GET("issues")
    Call<List<Issue>> getIssueList();


    @GET("issues/{issue_id}/comments")
    Call<ArrayList<Comment>> getCommentList(@Path("issue_id") int issueId);
}
