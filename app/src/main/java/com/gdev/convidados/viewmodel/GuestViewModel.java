package com.gdev.convidados.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.gdev.convidados.model.Feedback;
import com.gdev.convidados.model.GuestModel;
import com.gdev.convidados.repository.GuestRespository;

public class GuestViewModel extends AndroidViewModel {

    private final GuestRespository mRespository;

    private final MutableLiveData<GuestModel> mGuestModel = new MutableLiveData<>();
    public LiveData<GuestModel> guestModel = this.mGuestModel;

    private final MutableLiveData<Feedback> mFeedback = new MutableLiveData<>();
    public LiveData<Feedback> feedback = this.mFeedback;

    public GuestViewModel(@NonNull Application application) {
        super(application);
        this.mRespository = GuestRespository.getInstance(application.getApplicationContext());
    }

    public void saveOrUpdate(GuestModel guestModel) {
        String message = "";
        boolean sucess;
        if(guestModel.getName().equals("")){
            message = "Nome Obrigatório!";
            sucess = false;
        }else if (guestModel.getId() == 0) {
            sucess = this.mRespository.insert(guestModel);
            message = sucess ? "Convidado salvo com sucesso!" : "Erro ao salvar convidado!";
        }else{
            sucess = this.mRespository.update(guestModel);
            message = sucess ? "Convidado atualizado com sucesso!" : "Erro ao atualizar convidado!";
        }

        Feedback feedback = new Feedback(message,sucess);
        this.mFeedback.setValue(feedback);
    }

    public void get(int id) {
        this.mGuestModel.setValue(this.mRespository.find(new GuestModel(id)));
    }
}
