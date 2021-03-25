package com.gdev.convidados.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gdev.convidados.R;
import com.gdev.convidados.constants.GuestConstants;
import com.gdev.convidados.model.Feedback;
import com.gdev.convidados.view.adapter.GuestAdapter;
import com.gdev.convidados.view.listener.OnListClick;
import com.gdev.convidados.viewmodel.AllGuestsViewModel;

public class AllGuestsFragment extends Fragment {

    private AllGuestsViewModel mViewModel;
    private ViewHolder mViewHolder = new ViewHolder();
    private GuestAdapter mAdapter = new GuestAdapter();
    private int mFilter = 0;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        this.mViewModel = new ViewModelProvider(this).get(AllGuestsViewModel.class);

        View root = inflater.inflate(R.layout.fragment_all_guests, container, false);

        this.mViewHolder.recyclerGuests = root.findViewById(R.id.recycler_list);
        this.mViewHolder.recyclerGuests.setLayoutManager(new LinearLayoutManager(getContext()));
        this.mViewHolder.recyclerGuests.setAdapter(this.mAdapter);

        OnListClick guestListener = new OnListClick() {
            @Override
            public void onClick(int id) {
                Intent i = new Intent(getContext(), GuestActivity.class);
                i.putExtra(GuestConstants.INTENT_ID, id);
                startActivity(i);
            }

            @Override
            public void onLongClick(int id) {
                mViewModel.delete(id);
                mViewModel.getList(mFilter);
            }
        };
        this.mAdapter.attachListener(guestListener);

        if(getArguments() != null){
            this.mFilter = getArguments().getInt(GuestConstants.FRAGMENT_FILTER);
        }
        this.observers();

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        this.mViewModel.getList(this.mFilter);
    }

    private void observers(){
        this.mViewModel.guestList.observe(getViewLifecycleOwner(), guestModels -> {
            this.mAdapter.attachList(guestModels);
        });

        this.mViewModel.feedback.observe(getViewLifecycleOwner(), feedback -> {
            Toast.makeText(getContext(),feedback.getMessage(),Toast.LENGTH_SHORT).show();
        });
    }

    private static class ViewHolder{
        RecyclerView recyclerGuests;
    }
}