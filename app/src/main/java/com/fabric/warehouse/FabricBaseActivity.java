package com.fabric.warehouse;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fabric.warehouse.di.ApplicationModule;

import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.Optional;

/**
 * Created by 6193 on 2016/4/11.
 */
public abstract class FabricBaseActivity extends AppCompatActivity {

    @InjectView(R.id.toolbar_title)
    protected TextView toolbarTitle;

    @Optional
    @InjectView(R.id.toolbarLeftButton)
    Button leftButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void setTitle(String title) {
        if (toolbarTitle == null) return;
        toolbarTitle.setText(title);
    }

    public ApplicationModule getAppModule() {
        return ((WarehouseApp) getApplication()).appModule();
    }

    protected void showBackButton() {
        if (leftButton == null) return;
        leftButton.setText(getString(R.string.btn_back));
        leftButton.setVisibility(View.VISIBLE);
    }

    @Optional
    @OnClick(R.id.toolbarLeftButton)
    void onBackButtonClicked() {
        onBackPressed();
    }

}
