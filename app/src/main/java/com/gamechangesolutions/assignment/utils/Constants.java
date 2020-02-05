package com.gamechangesolutions.assignment.utils;

public interface Constants {
    String INTENT_EXTRA_ISSUE = "intent_extra_issue";
    int DESCRIPTION_MIN_LINES = 6;
    String TIME_STAMP_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    String DATA_SYNC_TIME_STAMP_FORMAT = "yyyy-MM-dd";
    String PREF_SYNC_TIME = "pref_sync_time";
    String ISSUE_STATE_OPEN = "open";
    String ISSUE_STATE_CLOSED = "closed";
    String DB_NAME = "GCS_database";
    String WORK_MANAGER_TASK_TAG = "SyncData";
    int NOTIFICATION_ID = 1;
    String CHANNEL_ID = "gcs_channel";
    String CHANNEL_NAME = "gcs_notify";
    String RESULT_MSG = "result_msg";
    String DATA_SYNCED_OFFLINE = "data_synced_offline";
    String LATEST_DATA_SYNCED_OFFLINE = "latest_data_synced_offline";
    long SPLASH_SCREEN_DELAY = 1000;

    interface DataSyncResultMsg {
        String PLEASE_TRY_AGAIN = "Please try again";
        String DATA_SYNCED_SUCCESS = "Data sync completed!!!";
        String DATA_SYNCED_PROGRESS = "Data sync in progress...";
        String DATA_SYNCED_FAILED = "Data sync in failed";
        String NO_DATA = "No data available";
    }
}
