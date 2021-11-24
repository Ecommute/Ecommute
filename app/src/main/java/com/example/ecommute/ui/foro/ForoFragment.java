package com.example.ecommute.ui.foro;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.ecommute.PopUpClass;
import com.example.ecommute.databinding.FragmentForoBinding;

public class ForoFragment extends Fragment {

    private ForoViewModel foroViewModel;
    private FragmentForoBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        foroViewModel =
                new ViewModelProvider(this).get(ForoViewModel.class);

        binding = FragmentForoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        /*foroViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/

        /*Button detalles = binding.detalles;
        detalles.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                PopUpClass popUpClass = new PopUpClass();
                popUpClass.showPopupWindow(v);
            }
        });*/

        /*textView.setText(Html.fromHtml("<iframe width=\"20\" height=\"20\" frameborder=\"0\" style=\"border:0\" " +
                "src=https://www.google.com/maps/embed/v1/directions?key=AIzaSyApUk0xJoZuc46YAjVQEhF1ul67ObY80Sk&origin=Barcelona&destination=Madrid&mode=driving&region=es " +
                "allowfullscreen></iframe>"));*/
        textView.setText("Route Map");


        return root;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}