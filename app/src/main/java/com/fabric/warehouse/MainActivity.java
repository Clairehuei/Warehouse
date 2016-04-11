package com.fabric.warehouse;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;

import com.fabric.warehouse.Api.GetAuthToken;
import com.fabric.warehouse.Api.User;
import com.fabric.warehouse.Helper.PreferenceHelper;
import com.fabric.warehouse.Model.Token;
import com.fabric.warehouse.Model.UserInfo;
import com.fabric.warehouse.Model.UserInfoResponse;
import com.fabric.warehouse.di.ApiComponent;
import com.fabric.warehouse.di.ApiModule;
import com.fabric.warehouse.di.ApplicationModule;
import com.fabric.warehouse.di.DaggerApiComponent;

import java.net.SocketTimeoutException;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import rx.Observer;

import static android.content.DialogInterface.BUTTON_NEUTRAL;

public class MainActivity extends AppCompatActivity {

    private ProgressDialog progress;
    boolean isTest = true;

    @Inject
    Context context;
    @Inject
    PreferenceHelper preferenceHelper;
    @Inject
    User user;
    @Inject
    GetAuthToken authToken;
    @InjectView(R.id.edit_account)
    EditText editAccount;
    @InjectView(R.id.edit_password)
    EditText editPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        ButterKnife.inject(this);
        ApiComponent apiComponent = DaggerApiComponent.builder()
                .applicationModule(new ApplicationModule(getApplication()))
                .apiModule(new ApiModule())
                .build();
        apiComponent.inject(this);
//        setVersionName();
//        checkNewVersion();
//        setupDb();
    }

    @Override
    protected void onResume() {
        super.onResume();
        progress = new ProgressDialog(this);
        preferenceHelper.setCates("");
//        String acc = preferenceHelper.getAccountAndPassword();
//        if (acc != null) {
//            keeplogin.setChecked(true);
//            editAccount.setText(acc);
//        }
    }

    @OnClick(R.id.btn_confirm)
    void confirmClick() {
        progress.setMessage(getString(R.string.login_please_wait));
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.show();

        if(isTest){
            preferenceHelper.setTokenId("123456789");
            getUserInfo();
        }else{
            authToken.get(Config.API_VERSION, getEncryptionKey(), new Callback<Token>() {

                @Override
                public void success(Token token, Response response) {
                    preferenceHelper.setTokenId(token.getTokenId());
//                saveAccPass();
                    getUserInfo();
                }

                @Override
                public void failure(RetrofitError error) {
                    progress.dismiss();
                    AlertDialog dialog = new AlertDialog.Builder(MainActivity.this).create();
                    if (error.getCause() instanceof SocketTimeoutException) {
                        dialog.setMessage(getString(R.string.login_timeout));
                    } else if (error.getMessage().contains("authentication") || error.getMessage().contains("401")) {
                        dialog.setMessage(getString(R.string.login_error));
                    } else {
                        dialog.setMessage(getString(R.string.no_network));
                    }
                    dialog.setButton(BUTTON_NEUTRAL, getString(R.string.btn_confirm), (dialog1, which) -> {
                    });
                    dialog.show();
                }
            });
        }
    }

    private String getEncryptionKey() {
        String concatAccountAndPassword = TextUtils.join(":", new String[]{
                editAccount.getText().toString(),
                editPassword.getText().toString()
        });
//        return AuthBaseClient.encodedKey(concatAccountAndPassword, Config.SEED);
        return "EncryptionKey";
    }

    private void getUserInfo() {
        progress.setMessage(getString(R.string.get_user_info));

        if(isTest){
            progress.dismiss();
            toMainActivity(true);
        }else{
            user.getUserInfo(Config.API_VERSION, preferenceHelper.getTokenId(), null).subscribe(new Observer<UserInfoResponse>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    progress.dismiss();
                    AlertDialog dialog = new AlertDialog.Builder(context).create();
                    dialog.setMessage(getString(R.string.login_error));
                    dialog.setButton(BUTTON_NEUTRAL, getString(R.string.btn_confirm), (dialog1, which) -> {
                    });
                    dialog.show();
                }

                @Override
                public void onNext(UserInfoResponse userInfoResponse) {
                    UserInfo userInfo = userInfoResponse.getDataResult().getUserInfo();
//                preferenceHelper.setUserInfo(userInfo);
                    progress.dismiss();
//                toMainActivity(userInfo.getCompany().isBuyer());
                    toMainActivity(true);
                }
            });
        }
    }

    private void toMainActivity(boolean isAdmin) {
        Intent intent = new MainIntentFactory(isAdmin).get();
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    class MainIntentFactory {

        private final Intent intent;

        public MainIntentFactory(boolean isAdmin) {
            if (isAdmin) {
                intent = new Intent(context, ActivityMainAdmin.class);
                return;
            }
            intent = new Intent(context, ActivityMainAdmin.class);
        }

        public Intent get() {
            return this.intent;
        }
    }

}
