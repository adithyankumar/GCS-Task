package com.gamechangesolutions.assignment.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.gamechangesolutions.assignment.model.Issue;
import com.gamechangesolutions.assignment.repository.DataRepository;

import java.util.List;

public class IssueListViewModel extends AndroidViewModel {
    private final DataRepository dataRepository;

    public IssueListViewModel(@NonNull Application application) {
        super(application);
        dataRepository = new DataRepository(application);
    }

    public LiveData<List<Issue>> getIssueList() {
        return dataRepository.getMutableIssueLiveData();
    }
}
