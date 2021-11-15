package com.example.ecommute.ui.puntos;

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

import com.example.ecommute.databinding.FragmentPuntosBinding;

public class PuntosFragment extends Fragment {

    private PuntosViewModel puntosViewModel;
    private FragmentPuntosBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        puntosViewModel =
                new ViewModelProvider(this).get(PuntosViewModel.class);

        binding = FragmentPuntosBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        /*final TextView textView = binding.textPuntos;
        puntosViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}