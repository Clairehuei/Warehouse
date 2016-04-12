package com.fabric.warehouse.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.fabric.warehouse.Model.Contract;
import com.fabric.warehouse.R;
import java.util.List;

/**
 * Created by 6193 on 2016/4/12.
 */
public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {

    public ContactsAdapter(List<Contract> contacts){
        mContacts = contacts;
    }

    private List<Contract> mContacts;

    @Override
    public ContactsAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        View contactView = LayoutInflater.from(context).inflate(R.layout.item_classify_content, viewGroup, false);

        return new ViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(ContactsAdapter.ViewHolder viewHolder, int position) {
        Contract contract = mContacts.get(position);
        TextView nameTextView = viewHolder.nameTextView;
        nameTextView.setText(contract.getName());
    }

    @Override
    public int getItemCount() {
        return mContacts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView nameTextView;
        public ViewHolder(View itemView){
            super(itemView);

            nameTextView = (TextView) itemView.findViewById(R.id.tv_name);
        }
    }

}
