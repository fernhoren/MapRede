package com.maprede.ui.Instructions;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class InstructionsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public InstructionsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Instruções");
    }

    public LiveData<String> getText() {
        return mText;
    }
}