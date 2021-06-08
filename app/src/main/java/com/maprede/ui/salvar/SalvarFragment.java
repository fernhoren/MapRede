package com.maprede.ui.salvar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.maprede.R;

public class SalvarFragment extends Fragment {

    private SalvarViewModel salvarViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        salvarViewModel =
                new ViewModelProvider(this).get(SalvarViewModel.class);
        View root = inflater.inflate(R.layout.fragment_salvar, container, false);
        final TextView textView = root.findViewById(R.id.text_slideshow);
        salvarViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}