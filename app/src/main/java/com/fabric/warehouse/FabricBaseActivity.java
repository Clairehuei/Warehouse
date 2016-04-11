package com.fabric.warehouse;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.fabric.warehouse.di.ApplicationModule;

import butterknife.InjectView;

/**
 * Created by 6193 on 2016/4/11.
 */
public abstract class FabricBaseActivity extends AppCompatActivity {

    @InjectView(R.id.toolbar_title)
    protected TextView toolbarTitle;

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
}
