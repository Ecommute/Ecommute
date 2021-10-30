package com.example.ecommute.ui.foro;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ForoViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ForoViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Esta es la pestaña del foro");
    }

    public LiveData<String> getText() {
        return mText;
    }
}