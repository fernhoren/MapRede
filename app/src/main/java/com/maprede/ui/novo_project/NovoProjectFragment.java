package com.maprede.ui.novo_project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.maprede.R;

public class NovoProjectFragment extends Fragment {

    private NovoProjectViewModel novoProjectViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        novoProjectViewModel =
                new ViewModelProvider(this).get(NovoProjectViewModel.class);
        View root = inflater.inflate(R.layout.fragment_novo_project, container, false);
        final TextView textView = root.findViewById(R.id.text_gallery);
        novoProjectViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btn_novo_projeto = view.findViewById(R.id.btn_novo_projeto);
        final NavController navController= Navigation.findNavController(view);

        btn_novo_projeto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.map_user);

            }
        });

    }
}