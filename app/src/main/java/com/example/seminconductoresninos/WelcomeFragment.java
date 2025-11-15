package com.example.seminconductoresninos;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class WelcomeFragment extends Fragment {
    TextView textView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_welcome, container, false);

        // INICIALIZAR el TextView con findViewById
        textView = view.findViewById(R.id.textView4); // Asegúrate que este ID existe en tu XML

        SharedPreferences prefs = getActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE);
        String nombre = prefs.getString("nombre", "Usuario");

        // Mostrar saludo - AHORA textView no será null
        if (textView != null) {
            textView.setText("Bienvenido " + nombre + " 👋");
        }

        Button nextButton = view.findViewById(R.id.nextButton);
        if (nextButton != null) {
            nextButton.setOnClickListener(v -> {
                if (getActivity() instanceof Home) {
                    ((Home) getActivity()).goToNextPage();
                }
            });
        }

        return view;
    }
}