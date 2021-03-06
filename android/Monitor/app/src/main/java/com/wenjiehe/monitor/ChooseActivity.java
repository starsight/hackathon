package com.wenjiehe.monitor;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class ChooseActivity extends BaseActivity {

    Button bt_login;
    Button bt_register;
    //TextView tv_loginForgetPassword;//login
    EditText et_loginUserName, et_loginPassword;

    EditText et_regUserName, et_regPassword;//register

    Button bt_chooseLogin, bt_chooseRegister;//启动选择登陆or注册
    ImageView iv_choose_icon;

    boolean isEnterLoginOrReg = false;

    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        //AVAnalytics.trackAppOpened(getIntent());

        //AVService.initPushService(this);


        bt_chooseLogin = (Button) findViewById(R.id.bt_choose_login);
        bt_chooseRegister = (Button) findViewById(R.id.bt_choose_register);
        iv_choose_icon = (ImageView) findViewById(R.id.iv_choose_icon);

        if (getUserId() != null) {
            Intent mainIntent = new Intent(activity, MainActivity.class);
            mainIntent.putExtra("username", getUserName());

            startActivity(mainIntent);
            activity.finish();
        }


        bt_chooseLogin.setOnClickListener(chooseLoginListener);
        bt_chooseRegister.setOnClickListener(chooseRegisterListener);

        bt_chooseRegister.setOnTouchListener(regTouchListener);
        bt_chooseLogin.setOnTouchListener(loginTouchListener);

    }

    View.OnTouchListener loginTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    System.out.print(bt_chooseRegister.getY());
                    System.out.print("---" + event.getY());
                    //按钮按下逻辑
                    bt_chooseLogin.setTextColor(getResources().getColor(R.color.white));
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (event.getY() > 25 + bt_chooseLogin.getHeight() || event.getY() < -25) {
                        bt_chooseLogin.setTextColor(getResources().getColor(R.color.black));
                    }
                    if (event.getX() > 25 + bt_chooseLogin.getWidth() || event.getX() < -25) {
                        bt_chooseLogin.setTextColor(getResources().getColor(R.color.black));
                    }
                    break;
            }

            return false;
        }
    };

    View.OnTouchListener regTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    //按钮按下逻辑
                    bt_chooseRegister.setTextColor(getResources().getColor(R.color.white));
                    break;

                case MotionEvent.ACTION_MOVE:
                    if (event.getY() > 25 + bt_chooseRegister.getHeight() || event.getY() < -25) {
                        bt_chooseRegister.setTextColor(getResources().getColor(R.color.black));
                    }
                    if (event.getX() > 25 + bt_chooseRegister.getWidth() || event.getX() < -25) {
                        bt_chooseRegister.setTextColor(getResources().getColor(R.color.black));
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    bt_chooseRegister.setTextColor(getResources().getColor(R.color.black));
                    //按钮弹起逻辑
                    break;
            }
            return false;
        }
    };

    View.OnClickListener loginListener = new View.OnClickListener() {

        @SuppressLint("NewApi")
        @SuppressWarnings({"unchecked", "rawtypes"})
        @Override
        public void onClick(View arg0) {
            String username = et_loginUserName.getText().toString();
            if (username.equals("")) {
                showUserNameEmptyError();
                return;
            }
            String passwd = et_loginPassword.getText().toString();
            if (passwd.isEmpty()) {
                showUserPasswordEmptyError();
                return;
            }
            progressDialogShow();
            login(username, passwd);
        }

    };

    //选择登陆
    View.OnClickListener chooseLoginListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //iv_choose_icon.setVisibility(View.GONE);

            //bt_chooseLogin.setBackground(R.color.choose_log_reg_background);
            isEnterLoginOrReg = true;
            setContentView(R.layout.choose_login);
            bt_login = (Button) findViewById(R.id.bt_login);
            //tv_loginForgetPassword = (TextView) findViewById(R.id.tv_loginForgetPassword);
            et_loginUserName = (EditText) findViewById(R.id.et_loginUserName);
            et_loginPassword = (EditText) findViewById(R.id.et_loginPassword);
            bt_login.setOnClickListener(loginListener);
            //tv_loginForgetPassword.setOnClickListener(forgetPasswordListener);
        }
    };

    //选择注册
    View.OnClickListener chooseRegisterListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            isEnterLoginOrReg = true;

            setContentView(R.layout.choose_register);
            bt_register = (Button) findViewById(R.id.bt_register);

            et_regUserName = (EditText) findViewById(R.id.et_regUserName);
            et_regPassword = (EditText) findViewById(R.id.et_regPasswd);

            bt_register.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (!et_regUserName.getText().toString().isEmpty()) {
                        if (!et_regPassword.getText().toString().isEmpty()) {
                            progressDialogShow();
                            register(et_regUserName.getText().toString(), et_regPassword.getText().toString());
                        } else {
                            showError(activity
                                    .getString(R.string.error_register_password_null));
                        }
                    } else {
                        showError(activity
                                .getString(R.string.error_register_user_name_null));
                    }

                }
            });


        }
    };

    private void progressDialogDismiss() {
        if (progressDialog != null)
            progressDialog.dismiss();
    }

    private void progressDialogShow() {
        progressDialog = ProgressDialog
                .show(activity,
                        activity.getResources().getText(
                                R.string.dialog_message_title),
                        activity.getResources().getText(
                                R.string.dialog_text_wait), true, false);
    }

    private void showLoginError() {
        new AlertDialog.Builder(activity)
                .setTitle(
                        activity.getResources().getString(
                                R.string.dialog_message_title))
                .setMessage(
                        activity.getResources().getString(
                                R.string.error_login_error))
                .setNegativeButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                dialog.dismiss();
                            }
                        }).show();
    }

    private void showUserPasswordEmptyError() {
        new AlertDialog.Builder(activity)
                .setTitle(
                        activity.getResources().getString(
                                R.string.dialog_error_title))
                .setMessage(
                        activity.getResources().getString(
                                R.string.error_register_password_null))
                .setNegativeButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                dialog.dismiss();
                            }
                        }).show();
    }

    private void showUserNameEmptyError() {
        new AlertDialog.Builder(activity)
                .setTitle(
                        activity.getResources().getString(
                                R.string.dialog_error_title))
                .setMessage(
                        activity.getResources().getString(
                                R.string.error_register_user_name_null))
                .setNegativeButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                dialog.dismiss();
                            }
                        }).show();
    }

    public void login(final String uname, final String upasswd) {
        new Thread() {
            @Override
            public void run() {
                OkHttpClient okHttpClient = new OkHttpClient();
                final Request request = new Request.Builder().get()
                        .url("http://123.206.214.17:8080/Supervisor/user.do?method=login&uname="+uname+"&upwd="+upasswd+"&userRight=1")
                        .build();
                try {
                    okHttpClient.newCall(request).execute();
                    //// TODO: 2016/12/9
                    Message m = new Message();
                    m.what = IS_LOG_OK;
                    handler.sendMessage(m);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public void register(final String uname, final String upasswd) {
        //Log.d("register",uname+upasswd);
        new Thread() {
            @Override
            public void run() {
                OkHttpClient okHttpClient = new OkHttpClient();

                final Request request = new Request.Builder().get()
                        .url("http://123.206.214.17:8080/Supervisor/user.do?method=register&uname=" + uname + "&upwd=" + upasswd)
                        .build();
                try {
                    okHttpClient.newCall(request).execute();
                    //// TODO: 2016/12/9
                    Message m = new Message();
                    m.what = IS_REG_OK;
                    handler.sendMessage(m);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }


    private void showRegisterSuccess() {
        new AlertDialog.Builder(activity)
                .setTitle(
                        activity.getResources().getString(
                                R.string.dialog_message_title))
                .setMessage(
                        activity.getResources().getString(
                                R.string.success_register_success))
                .setNegativeButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                dialog.dismiss();
                            }
                        }).show();
    }

    @Override
    public void onBackPressed() {
        //
        if (isEnterLoginOrReg == true) {
            setContentView(R.layout.activity_choose);
            bt_chooseLogin = (Button) findViewById(R.id.bt_choose_login);
            bt_chooseRegister = (Button) findViewById(R.id.bt_choose_register);
            iv_choose_icon = (ImageView) findViewById(R.id.iv_choose_icon);

            //bt_chooseRegister.setVisibility(View.VISIBLE);
            //Button bt_chooseRegister2 = (Button) findViewById(R.id.bt_choose_register2);
            //bt_chooseRegister2.setVisibility(View.GONE);

            bt_chooseLogin.setOnClickListener(chooseLoginListener);
            bt_chooseRegister.setOnClickListener(chooseRegisterListener);
            bt_chooseRegister.setOnTouchListener(regTouchListener);
            bt_chooseLogin.setOnTouchListener(loginTouchListener);
        } else
            super.onBackPressed();
        isEnterLoginOrReg = false;
        //System.out.println("按下了back键   onBackPressed()");
    }

    public final static int IS_REG_OK = 0;
    public final static int IS_LOG_OK = 1;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case IS_REG_OK:
                    progressDialogDismiss();
                    Intent intent = new Intent(ChooseActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case IS_LOG_OK:
                    progressDialogDismiss();
                    Intent intent2 = new Intent(ChooseActivity.this, MainActivity.class);
                    startActivity(intent2);
                    finish();
                    break;
                default:
                    break;

            }
        }
    };

}
