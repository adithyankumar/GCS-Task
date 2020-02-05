package com.gamechangesolutions.assignment.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.gamechangesolutions.assignment.model.Comment;
import com.gamechangesolutions.assignment.repository.DataRepository;

import java.util.ArrayList;

public class CommentListViewModel extends AndroidViewModel {
    private final DataRepository dataRepository;

    public CommentListViewModel(@NonNull Application application) {
        super(application);
        dataRepository = new DataRepository(application);
    }

    public LiveData<ArrayList<Comment>> getCommentList(int issueId) {
        return dataRepository.getMutableCommentLiveData(issueId);
    }
}
