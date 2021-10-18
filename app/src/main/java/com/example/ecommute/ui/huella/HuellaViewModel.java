package com.example.ecommute.ui.huella;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HuellaViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HuellaViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Pestaña de la huella");
    }

    public LiveData<String> getText() {
        return mText;
    }
}