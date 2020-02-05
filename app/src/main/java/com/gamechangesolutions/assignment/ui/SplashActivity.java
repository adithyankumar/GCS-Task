package com.gamechangesolutions.assignment.ui;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkRequest;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.work.Data;
import androidx.work.WorkInfo;

import com.gamechangesolutions.assignment.R;
import com.gamechangesolutions.assignment.database.GCSDatabase;
import com.gamechangesolutions.assignment.databinding.ActivitySplashBinding;
import com.gamechangesolutions.assignment.model.Comment;
import com.gamechangesolutions.assignment.model.CommentList;
import com.gamechangesolutions.assignment.model.Issue;
import com.gamechangesolutions.assignment.utils.AlertBox;
import com.gamechangesolutions.assignment.utils.Constants;
import com.gamechangesolutions.assignment.utils.PrefUtils;
import com.gamechangesolutions.assignment.viewmodel.SplashViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SplashActivity extends AppCompatActivity {

    private ActivitySplashBinding activitySplashBinding;
    private final static String TAG = SplashActivity.class.getSimpleName();
    private Context mContext;
    private SplashViewModel splashViewModel;
    private ConnectivityManager connectivityManager;
    private boolean isInternetAvailable = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        activitySplashBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash);
        registerNetworkCallback();
    }

    @Override
    protected void onResume() {
        super.onResume();
//         check whether the data is sync offline first time or every 24 hours
//         if true then move to Issue listing screen
//         else  sync the data from server
        if (new PrefUtils(mContext).getBoolean(Constants.DATA_SYNCED_OFFLINE)) {
            moveToIssueListScreen();
        } else {
            syncData();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unRegisterNetworkCallback();
    }

    /**
     * move to the issue listing screen
     */
    private void moveToIssueListScreen() {
        startActivity(new Intent(SplashActivity.this, IssueListActivity.class));
        finish();
    }

    /**
     * Sync data from server
     */
    private void syncData() {
        // if internet is not available then show error popup
        if (!isInternetAvailable) {
            Log.i(TAG, "syncData: no internet");
            showInternetAlert(mContext.getString(R.string.error_no_internet));
        }


        splashViewModel = ViewModelProviders.of(this).get(SplashViewModel.class);
        splashViewModel.getOutputWorkInfo().observe(this, workInfoList -> {
            if (workInfoList == null) {
                Objects.requireNonNull(workInfoList);
            }
            if (workInfoList.size() != 0) {
                WorkInfo workInfo = workInfoList.get(0);
                WorkInfo.State currentState = workInfo.getState();
                boolean isFinished = currentState.isFinished();
                Data outputData = workInfo.getOutputData();
                String output = outputData.getString(Constants.RESULT_MSG);
                Log.i(TAG, "state = " + currentState);
                Log.i(TAG, "output = " + output);
                if (!isFinished) {
                    // If the data syncing is not finished , show the progress bar
                    activitySplashBinding.progressBar.setVisibility(View.VISIBLE);
                    Log.i(TAG, "Data sync in progress");
                } else {
                    // If the data syncing is finished , hide the progress bar
                    activitySplashBinding.progressBar.setVisibility(View.GONE);
                    if (currentState == WorkInfo.State.SUCCEEDED) {
                        Log.i(TAG, "Data sync completed");
                        // initiate the work manager to sync data after 24 hours
                        splashViewModel.syncData(mContext, true);
                        printData();
                        // if the State is Succeeded then move to next screen
                        moveToIssueListScreen();
                    } else if (currentState == WorkInfo.State.FAILED) {
                        Log.i(TAG, "Data sync failed.");
                        //else failed , then show error pop-up to sync data again from server
                        // or exit the app
                        AlertBox.dataSyncFailedErrorAlert(mContext, output, (dialogInterface, i) -> {
                            splashViewModel.startSyncDataWorker(mContext);
                            dialogInterface.dismiss();
                        });
                    }
                }
            }
        });

    }

    private final ConnectivityManager.NetworkCallback networkCallback = new ConnectivityManager.NetworkCallback() {

        @Override
        public void onAvailable(@NonNull Network network) {
            super.onAvailable(network);
            Log.i(TAG, "onAvailable: ");
            AlertBox.dismissAlert();
            isInternetAvailable = true;
        }

        @Override
        public void onLost(@NonNull Network network) {
            super.onLost(network);
            Log.i(TAG, "onLost:");
            showInternetAlert(getString(R.string.error_no_internet));
            isInternetAvailable = false;
        }
    };

    /**
     * @param message show error pop-up in background thread using @{@link Handler}
     */
    private void showInternetAlert(String message) {
        new Handler().post(() -> AlertBox.showErrorAlert(mContext, message));
    }

    /**
     * Register the networkCallback
     */
    private void registerNetworkCallback() {
        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        Objects.requireNonNull(connectivityManager).registerNetworkCallback(new NetworkRequest.Builder().build(), networkCallback);
    }

    /**
     * Unregister the networkCallback
     */
    private void unRegisterNetworkCallback() {
        connectivityManager.unregisterNetworkCallback(networkCallback);
    }


    /**
     * print the data synced from server
     */

    private void printData() {
        AsyncTask.execute(() -> {
            List<Issue> issueList = GCSDatabase.getInstance(SplashActivity.this).getIssueDao().getIssueListBasedState(Constants.ISSUE_STATE_OPEN);
            Log.i(TAG, ((issueList != null && !issueList.isEmpty()) ? issueList.size() : 0) + " issues ");

            for (Issue issue : Objects.requireNonNull(issueList)) {
                Log.i(TAG, " Number " + issue.getNumber() + "\tTotal Comment " + issue.getComments());
                if (issue.getComments() != 0) {
                    CommentList commentList = GCSDatabase.getInstance(SplashActivity.this).getCommentDao().
                            getCommentList(issue.getNumber());
                    if (commentList != null) {
                        ArrayList<Comment> comments = commentList.getCommentList();
                        for (Comment comment : comments) {
                            Log.i(TAG, " CommentUser : " + comment.getUser().getLogin());
                        }
                    }

                }
                Log.i(TAG, "\n----------------------------------------------------------------------------\n");
            }

        });
    }
}
