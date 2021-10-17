package com.example.ecommute.ui.ruta;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RutaViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public RutaViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Introduce los datos de tu ruta");
    }

    public LiveData<String> getTitleText() {
        return mText;
    }
}