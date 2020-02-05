package com.gamechangesolutions.assignment.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;

import androidx.appcompat.app.AppCompatActivity;

import com.gamechangesolutions.assignment.R;

/**
 * {@link AlertBox} is utility class as Singleton {@link AlertDialog} throughout the app
 */
public class AlertBox {
    private static AlertDialog alertDialog;

    /**
     * dismiss the alert dialog is already showing
     */
    public static void dismissAlert() {
        if (isShowing())
            alertDialog.dismiss();
    }

    /**
     * @param context context
     * @param message show only error alert message based on input string resource id
     */
    private static void showAlert(Context context, int message) {
        dismissAlert();
        alertDialog = new AlertDialog.Builder(context)
                .setTitle(R.string.error)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .create();
        alertDialog.show();
    }

    /**
     * @param context context
     * @param message show only error alert message based on input string
     */
    private static void showAlert(Context context, String message) {
        dismissAlert();
        alertDialog = new AlertDialog.Builder(context)
                .setTitle(R.string.error)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .create();
        alertDialog.show();
    }

    /**
     * @param context context
     * @param message show only error alert message based on input string
     */
    public static void showErrorAlert(Context context, int message) {
        showAlert(context, message);
    }

    /**
     * @param context context
     * @param message show only error alert message based on input string resource id
     */
    public static void showErrorAlert(Context context, String message) {
        showAlert(context, message);
    }

    /**
     * @return check whether {@link AlertDialog} is already shown or not
     */
    private static boolean isShowing() {
        return alertDialog != null && alertDialog.isShowing();
    }

    /**
     * @param context                       context
     * @param msg                           Message to be shown
     * @param dialogInterfaceNegativeButton Negative button click of this alert dialog
     */
    public static void dataSyncFailedErrorAlert(Context context, String msg, DialogInterface.OnClickListener dialogInterfaceNegativeButton) {
        dismissAlert();
        if (TextUtils.isEmpty(msg)) {
            msg = context.getString(R.string.error_data_sync_failed);
        }
        alertDialog = new AlertDialog.Builder(context)
                .setTitle(R.string.error)
                .setMessage(msg)
                .setPositiveButton(R.string.exit, (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                    ((AppCompatActivity) context).finishAffinity();

                })
                .setNegativeButton(R.string.try_again, dialogInterfaceNegativeButton)
                .create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }

}
