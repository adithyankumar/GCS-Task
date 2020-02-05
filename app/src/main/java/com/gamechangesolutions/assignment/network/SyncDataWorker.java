package com.gamechangesolutions.assignment.network;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.gamechangesolutions.assignment.repository.DataRepository;

/**
 * {@link SyncDataWorker} is used to sync data from server in background
 */
public class SyncDataWorker extends Worker {
    private final DataRepository dataRepository;

    public SyncDataWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        dataRepository = new DataRepository(context);
    }

    @NonNull
    @Override
    public Result doWork() {
        return dataRepository.syncData();
    }

}
