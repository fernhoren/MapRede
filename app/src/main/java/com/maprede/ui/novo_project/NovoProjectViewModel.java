package com.maprede.ui.novo_project;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NovoProjectViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public NovoProjectViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Novo_projeto");
    }

    public LiveData<String> getText() {
        return mText;
    }
}