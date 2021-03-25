package com.gdev.convidados.model;

public class Feedback {
    private String message;
    private boolean sucess;

    public Feedback(String message, boolean sucess) {
        this.message = message;
        this.sucess = sucess;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSucess() {
        return sucess;
    }

    public void setSucess(boolean sucess) {
        this.sucess = sucess;
    }
}
