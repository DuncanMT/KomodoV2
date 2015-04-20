package com.duncan.komodomyversion;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by alihassan on 16/04/2015.
 */
public class HomeViewAdapter extends RecyclerView.Adapter<HomeViewAdapter.ContactViewHolder>  {

    private List<CableInfo> cableList;
    Context ctx;
    public HomeViewAdapter(List<CableInfo> cableList) {
        this.cableList = cableList;
    }
    @Override
    public int getItemCount() {
        return cableList.size();
    }


    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.card_layout, viewGroup, false);
        return new ContactViewHolder(itemView);

    }


    @Override
    public void onBindViewHolder(ContactViewHolder contactViewHolder, int i) {
        CableInfo ci = cableList.get(i);
        final int position = i;
        contactViewHolder.itemView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.v("TOUCH: ", ""+position);
                return false;
            }
        });
        contactViewHolder.vName.setText(ci.title);
        if (Integer.parseInt(ci.quantity)  == 0 ){
            contactViewHolder.vSurname.setText("Out of stock");
        } else {
            contactViewHolder.vSurname.setText(ci.quantity + " left");
        }
        //  contactViewHolder.vEmail.setText(ci.time);
        contactViewHolder.vTitle.setText("£"+ci.price);

    }

    public void onClick(View v){
        Log.v("Clock", "Click");
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

        protected TextView vName;
        protected TextView vSurname;
        protected TextView vEmail;
        protected TextView vTitle;

        public ContactViewHolder(View v) {
            super(v);
            vName =  (TextView) v.findViewById(R.id.title);
            vSurname = (TextView)  v.findViewById(R.id.type);
            //    vEmail = (TextView)  v.findViewById(R.id.txtEmail);
            vTitle = (TextView) v.findViewById(R.id.price);
            v.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            // menu.setHeaderTitle("Select The Action");
            // menu.add(0, v.getId(), 0, "Call");//groupId, itemId, order, title
            // menu.add(0, v.getId(), 0, "SMS");
            Log.d("TAG", "CONTEXT ADAPTER");
        }
    }
}