package com.gdev.convidados.view.viewholder;

import android.content.DialogInterface;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.gdev.convidados.R;
import com.gdev.convidados.model.GuestModel;
import com.gdev.convidados.view.listener.OnListClick;

public class GuestViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
    private final TextView mTextName;
    private OnListClick mClick;
    private GuestModel mGuestModel;

    public GuestViewHolder(@NonNull View itemView) {
        super(itemView);
        itemView.setOnLongClickListener(this);
        itemView.setOnClickListener(this);
        this.mTextName = itemView.findViewById(R.id.text_name);
    }

    @Override
    public void onClick(View v) {
        this.mClick.onClick(this.mGuestModel.getId());
    }

    @Override
    public boolean onLongClick(View v) {
        new AlertDialog.Builder(v.getContext())
                .setTitle("Exclusão de convidado")
                .setMessage("Deseja realmente excluir este convidado?")
                .setPositiveButton("SIM", (dialog, which) -> mClick.onLongClick(mGuestModel.getId()))
                .setNegativeButton("NÃO", null)
                .show();
        return false;
    }

    public void bind(GuestModel guestModel, OnListClick clickListener) {
        this.mGuestModel = guestModel;
        this.mClick = clickListener;
        this.mTextName.setText(guestModel.getName());
    }
}
