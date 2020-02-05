package com.gamechangesolutions.assignment.viewmodel;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import com.gamechangesolutions.assignment.network.SyncDataWorker;
import com.gamechangesolutions.assignment.utils.Constants;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SplashViewModel extends AndroidViewModel {
    private static final String TAG = SplashViewModel.class.getSimpleName();
    private LiveData<List<WorkInfo>> mWorkInfo;

    public SplashViewModel(@NonNull Application application) {
        super(application);
        startSyncDataWorker(application);
    }

    public void startSyncDataWorker(Context context) {
        WorkManager workManager = syncData(context, false);
        mWorkInfo = workManager.getWorkInfosByTagLiveData(Constants.WORK_MANAGER_TASK_TAG);
    }

    /**
     * @return Constraints for the work manager
     */
    private static Constraints getConstraints() {
        return new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();
    }

    private static long getTimeDelay() {
        Calendar currentTime = Calendar.getInstance();
        //set time at 2AM
        Calendar dueTime = Calendar.getInstance();
        dueTime.set(Calendar.HOUR_OF_DAY, 2);
        dueTime.set(Calendar.MINUTE, 0);
        dueTime.set(Calendar.SECOND, 0);

        if (dueTime.before(currentTime)) {
            dueTime.add(Calendar.HOUR_OF_DAY, 24);
        }

        return dueTime.getTimeInMillis() - currentTime.getTimeInMillis();
    }


    public static WorkManager syncData(Context context, boolean delaySync) {
        Log.i(TAG, "started syncData delayed : " + delaySync);
        WorkManager workManager = WorkManager.getInstance(context);

        OneTimeWorkRequest.Builder syncDataRequestBuilder = new OneTimeWorkRequest.Builder(SyncDataWorker.class);
        syncDataRequestBuilder.setConstraints(getConstraints());
        syncDataRequestBuilder.addTag(Constants.WORK_MANAGER_TASK_TAG);
        if (delaySync) {
            //delay to 24 hours
            syncDataRequestBuilder.setInitialDelay(1000 * 60 * 60 * 24, TimeUnit.MILLISECONDS);
        }
        workManager.enqueue(syncDataRequestBuilder.build());

        return workManager;
    }

    public LiveData<List<WorkInfo>> getOutputWorkInfo() {
        return mWorkInfo;
    }


}
