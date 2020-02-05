package com.gamechangesolutions.assignment.repository;


import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.work.Data;
import androidx.work.ListenableWorker;

import com.gamechangesolutions.assignment.database.CommentDao;
import com.gamechangesolutions.assignment.database.GCSDatabase;
import com.gamechangesolutions.assignment.database.IssueDao;
import com.gamechangesolutions.assignment.model.Comment;
import com.gamechangesolutions.assignment.model.CommentList;
import com.gamechangesolutions.assignment.model.Issue;
import com.gamechangesolutions.assignment.network.DataService;
import com.gamechangesolutions.assignment.network.RetrofitClient;
import com.gamechangesolutions.assignment.utils.Constants;
import com.gamechangesolutions.assignment.utils.NotificationMessage;
import com.gamechangesolutions.assignment.utils.PrefUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Response;

public class DataRepository {
    private static final String TAG = DataRepository.class.getSimpleName();
    private final MutableLiveData<List<Issue>> mutableIssueLiveData = new MutableLiveData<>();
    private final MutableLiveData<ArrayList<Comment>> mutableCommentLiveData = new MutableLiveData<>();
    private final DataService dataService;
    private final IssueDao issueDao;
    private final CommentDao commentDao;
    private final GCSDatabase gcsDatabase;
    private final Context mContext;

    public DataRepository(Context context) {
        dataService = RetrofitClient.geDataService();
        gcsDatabase = GCSDatabase.getInstance(context);
        issueDao = gcsDatabase.getIssueDao();
        commentDao = gcsDatabase.getCommentDao();
        mContext = context;
    }

    /**
     * @return Sync the data from server
     */
    public ListenableWorker.Result syncData() {
        // reset the @Constants.DATA_SYNCED_OFFLINE value to false before syncing the data
        PrefUtils prefUtils = new PrefUtils(mContext);
        prefUtils.setBoolean(Constants.DATA_SYNCED_OFFLINE, false);
        final ListenableWorker.Result[] result = {ListenableWorker.Result.failure()};
        try {
            Call<List<Issue>> serviceIssueList = dataService.getIssueList();
            Response<List<Issue>> response = serviceIssueList.execute();
            NotificationMessage.showNotificationProgressIndeterminate(mContext, Constants.DataSyncResultMsg.DATA_SYNCED_PROGRESS);
            if (response.isSuccessful()) {
                final List<Issue> issueList = response.body();
                Log.i(TAG, "syncData:  issueList success" + Objects.requireNonNull(issueList).size());
                gcsDatabase.clearAllTables();
                issueDao.insertIssueList(issueList);
                for (Issue issue : issueList) {
                    if (issue.getComments() != 0) {
                        Call<ArrayList<Comment>> serviceCommentList = dataService.getCommentList(issue.getNumber());
                        Response<ArrayList<Comment>> commentListResponse;
                        try {
                            commentListResponse = serviceCommentList.execute();
                            if (commentListResponse.isSuccessful()) {
                                final ArrayList<Comment> commentList = commentListResponse.body();
                                Log.i(TAG, "syncData: commentList issue success:" + issue.getNumber()
                                        + " cmt size: " + Objects.requireNonNull(commentList).size());
                                CommentList commentListObj = new CommentList(issue.getNumber(), commentList);
                                commentDao.insetCommentList(commentListObj);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            return result[0] = ListenableWorker.Result.failure(resultMsg(e.getMessage()));
                        }
                    }
                }
                // set the @Constants.DATA_SYNCED_OFFLINE value to true after successful sync
                prefUtils.setBoolean(Constants.DATA_SYNCED_OFFLINE, true);
                return result[0] = ListenableWorker.Result.success(resultMsg(Constants.DataSyncResultMsg.DATA_SYNCED_SUCCESS));
            }
            return result[0] = ListenableWorker.Result.failure(resultMsg(Objects.requireNonNull(response.errorBody()).string()));
        } catch (Exception e) {
            e.printStackTrace();
            return result[0] = ListenableWorker.Result.failure(resultMsg(e.getMessage()));
        }
    }

    public MutableLiveData<List<Issue>> getMutableIssueLiveData() {
        GCSDatabase.dbExecutorService.execute(() -> {
            List<Issue> issueList = issueDao.getIssueListBasedState(Constants.ISSUE_STATE_OPEN);
            mutableIssueLiveData.postValue(issueList);
        });
        return mutableIssueLiveData;
    }

    public MutableLiveData<ArrayList<Comment>> getMutableCommentLiveData(int issueId) {

        GCSDatabase.dbExecutorService.execute(() -> {
            CommentList commentList = commentDao.getCommentList(issueId);
            if (commentList != null) {
                ArrayList<Comment> list = commentList.getCommentList();
                mutableCommentLiveData.postValue(list);
            }
        });
        return mutableCommentLiveData;
    }

    /**
     * @param msg message
     * @return message send to UI through work manager result
     */
    private Data resultMsg(String msg) {
        //show notification message based on the message
        NotificationMessage.showNotification(mContext, msg);
        return new Data.Builder()
                .putString(Constants.RESULT_MSG, msg)
                .build();
    }

}
