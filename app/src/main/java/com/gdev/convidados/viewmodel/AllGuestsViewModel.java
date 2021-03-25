package com.gdev.convidados.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gdev.convidados.constants.GuestConstants;
import com.gdev.convidados.model.Feedback;
import com.gdev.convidados.model.GuestModel;
import com.gdev.convidados.repository.GuestRespository;

import java.util.List;

public class AllGuestsViewModel extends AndroidViewModel {

    private GuestRespository mRespository;

    private MutableLiveData<List<GuestModel>> mGuestList = new MutableLiveData<>();
    public LiveData<List<GuestModel>> guestList = this.mGuestList;

    private MutableLiveData<Feedback> mFeedback = new MutableLiveData<>();
    public LiveData<Feedback> feedback = this.mFeedback;

    public AllGuestsViewModel(@NonNull Application application) {
        super(application);
        this.mRespository = GuestRespository.getInstance(application.getApplicationContext());
    }

    public void getList(int filter){
        if(filter == GuestConstants.NOT_CONFIRMED) {
            this.mGuestList.setValue(this.mRespository.getAll());
        }else if(filter == GuestConstants.PRESENT){
            this.mGuestList.setValue(this.mRespository.getPresents());
        }else if(filter == GuestConstants.ABSENT){
            this.mGuestList.setValue(this.mRespository.getAbsents());
        }
    }

    public void delete(int id){
        if(this.mRespository.delete(new GuestModel(id))){
            mFeedback.setValue(new Feedback("Convidado excluído com sucesso!", true));
        }else{
            mFeedback.setValue(new Feedback("Erro inesperado ao excluír convidado!", false));
        }
    }

}