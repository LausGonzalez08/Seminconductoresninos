package com.example.seminconductoresninos;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class RatingFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rating, container, false);

        Button startButton = view.findViewById(R.id.startButton);
        Button rateButton = view.findViewById(R.id.rateButton);
        Button socialButton = view.findViewById(R.id.socialButton);

        if (startButton != null) {
            startButton.setOnClickListener(v -> {
                if (getActivity() instanceof Home) {
                    ((Home) getActivity()).markIntroAsSeen();
                    ((Home) getActivity()).startMainActivity();
                }
            });
        }

        if (rateButton != null) {
            rateButton.setOnClickListener(v -> {
                // Abrir Play Store para calificar
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("market://details?id=" + requireContext().getPackageName()));
                startActivity(intent);
            });
        }

        if (socialButton != null) {
            socialButton.setOnClickListener(v -> {
                // Abrir redes sociales
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://twitter.com/tu_usuario"));
                startActivity(intent);
            });
        }

        return view;
    }
}