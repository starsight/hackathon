package com.wenjiehe.monitor;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class BaseActivity extends AppCompatActivity {
    public BaseActivity activity;
    private String userId, userName;

    private int signNum = 0;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        userId = null;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public int getSignNum() {
        return signNum;
    }

    protected void showError(String errorMessage) {
        showError(errorMessage, activity);
    }

    public void showError(String errorMessage, Activity activity) {
        new AlertDialog.Builder(activity)
                .setTitle(
                        activity.getResources().getString(
                                R.string.dialog_message_title))
                .setMessage(errorMessage)
                .setNegativeButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                dialog.dismiss();
                            }
                        }).show();
    }

    protected void onPause() {
        super.onPause();
        //AVAnalytics.onPause(this);
    }

    protected void onResume() {
        super.onResume();
        //AVAnalytics.onResume(this);
    }
}
