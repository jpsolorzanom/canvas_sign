package com.example.canvasign.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.canvasign.CanvaFirma;
import com.example.canvasign.R;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private CanvaFirma firma;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        firma = new CanvaFirma(getContext());

        View root = inflater.inflate(R.layout.fragment_home, container, false);
//        final TextView textView = root.findViewById(R.id.text_home);
//        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        FrameLayout frameFirma = (FrameLayout) root.findViewById(R.id.firmaCanvas);
        frameFirma.addView(firma);
        return root;
    }


}