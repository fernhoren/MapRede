package com.maprede.ui.salvar;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SalvarViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SalvarViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Salvar Projeto");
    }

    public LiveData<String> getText() {
        return mText;
    }
}