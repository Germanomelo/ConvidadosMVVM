package com.gdev.convidados.view.adapter;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gdev.convidados.R;
import com.gdev.convidados.model.GuestModel;
import com.gdev.convidados.view.listener.OnListClick;
import com.gdev.convidados.view.viewholder.GuestViewHolder;
import com.gdev.convidados.viewmodel.GuestViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class GuestAdapter extends RecyclerView.Adapter<GuestViewHolder> {

    private List<GuestModel> mList = new ArrayList<>();
    private OnListClick mOnListClick;

    public GuestAdapter() {
    }

    @NonNull
    @Override
    public GuestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view =inflater.inflate(R.layout.item_guest_row, parent,false);
        return new GuestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GuestViewHolder holder, int position) {
        holder.bind(this.mList.get(position), this.mOnListClick);
    }

    @Override
    public int getItemCount() {
        return this.mList.size();
    }

    public void attachList(List<GuestModel> list){
        this.mList = list;
        notifyDataSetChanged();
    }

    public void attachListener(OnListClick onListClick){
        this.mOnListClick = onListClick;
    }
}
