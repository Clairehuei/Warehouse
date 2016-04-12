package com.fabric.warehouse;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.widget.Button;

import com.fabric.warehouse.di.ApplicationComponent;
import com.fabric.warehouse.di.DaggerApplicationComponent;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by 6193 on 2016/4/11.
 */
public abstract class ActivityMainAbstract extends FabricBaseActivity {

    @Inject
    protected Context context;

    @InjectView(R.id.classify)
    protected Button classify;

    protected abstract void setMainMenu();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        ButterKnife.inject(this);
        ApplicationComponent applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(getAppModule())
                .build();
        applicationComponent.inject(this);

        toolbarTitle.setText(R.string.app_name);
        setMainMenu();
    }

    @OnClick(R.id.sing_out)
    void signOut() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.make_sure_to_sign_out);
        builder.setPositiveButton(R.string.confirm_sign_out, (dialog, which) -> {
            ((WarehouseApp) getApplication()).restartApp(this);
        });
        builder.setNegativeButton(R.string.cancel_sign_out, ((dialog, which) -> dialog.dismiss()));
        builder.show();
    }

}
