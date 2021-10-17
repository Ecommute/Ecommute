package com.example.ecommute.ui.puntos;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PuntosViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public PuntosViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Esta es la pestaña de los puntos");
    }

    public LiveData<String> getText() {
        return mText;
    }
}