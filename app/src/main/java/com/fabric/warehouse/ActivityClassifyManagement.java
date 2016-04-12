package com.fabric.warehouse;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.fabric.warehouse.Model.Contract;
import com.fabric.warehouse.adapter.ContactsAdapter;

import butterknife.ButterKnife;

/**
 * Created by 6193 on 2016/4/12.
 */
public class ActivityClassifyManagement extends FabricBaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classify_management);
        ButterKnife.inject(this);

        setTitle(getString(R.string.commodity_management));
        showBackButton();

        RecyclerView rvContacts = (RecyclerView) findViewById(R.id.recyclerView);
        ContactsAdapter adapter = new ContactsAdapter(Contract.generateSampleList());
        rvContacts.setAdapter(adapter);
        rvContacts.setLayoutManager(new LinearLayoutManager(this));
    }

}
