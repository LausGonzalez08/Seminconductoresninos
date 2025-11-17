package com.example.seminconductoresninos;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class Favoritos extends AppCompatActivity {
    private LinearLayout favoritesContainer;
    private SharedPreferences progressPrefs;
    private SharedPreferences favoritesPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoritos);

        progressPrefs = getSharedPreferences("topic_progress", MODE_PRIVATE);
        favoritesPrefs = getSharedPreferences("favorites", MODE_PRIVATE);

        initializeViews();
        setupNavigation();
        loadFavorites();
    }

    private void initializeViews() {
        favoritesContainer = findViewById(R.id.favoritesContainer);
    }

    private void setupNavigation() {
        LinearLayout navTemas = findViewById(R.id.nav_temas);
        LinearLayout navPerfil = findViewById(R.id.nav_perfil);
        LinearLayout navFavoritos = findViewById(R.id.nav_favoritos);

        if (navTemas != null) {
            navTemas.setOnClickListener(v -> {
                finish(); // Regresar a HomeMain
            });
        }

        if (navPerfil != null) {
            navPerfil.setOnClickListener(v -> {
                Intent intent = new Intent(Favoritos.this, PerfilActivity.class);
                startActivity(intent);
            });
        }

        if (navFavoritos != null) {
            navFavoritos.setOnClickListener(v -> {
                showToast("Ya estás en Favoritos");
            });
        }
    }

    private void loadFavorites() {
        favoritesContainer.removeAllViews();

        // Verificar cada tema si es favorito
        String[] topicIds = {"circuitos", "componentes", "energia"};
        String[] titles = {
                "¿Qué son los circuitos?",
                "¿Puedes identificar un componente de un circuito?",
                "¿Qué es la energía?"
        };
        String[] categories = {
                "Conceptos básicos",
                "Actividad",
                "Concepto Básico"
        };
        String[] durations = {"15 min", "10 min", "15 min"};

        boolean hasFavorites = false;

        for (int i = 0; i < topicIds.length; i++) {
            if (favoritesPrefs.getBoolean(topicIds[i], false)) {
                hasFavorites = true;
                addFavoriteCard(topicIds[i], categories[i], durations[i], titles[i],
                        "Este tema está en tus favoritos");
            }
        }

        if (!hasFavorites) {
            // Mostrar mensaje cuando no hay favoritos
            TextView noFavoritesText = new TextView(this);
            noFavoritesText.setText("No tienes temas favoritos aún");
            noFavoritesText.setTextSize(16);
            noFavoritesText.setGravity(View.TEXT_ALIGNMENT_CENTER);
            noFavoritesText.setPadding(0, 100, 0, 0);
            favoritesContainer.addView(noFavoritesText);
        }
    }

    private void addFavoriteCard(String topicId, String category, String duration, String title, String description) {
        View topicCard = getLayoutInflater().inflate(R.layout.item_topic_card, null);

        TextView categoryText = topicCard.findViewById(R.id.categoryText);
        TextView durationText = topicCard.findViewById(R.id.durationText);
        TextView titleText = topicCard.findViewById(R.id.titleText);
        TextView descriptionText = topicCard.findViewById(R.id.descriptionText);
        Button actionButton = topicCard.findViewById(R.id.actionButton);
        View statusIndicator = topicCard.findViewById(R.id.statusIndicator);

        // Configurar datos
        categoryText.setText(category);
        durationText.setText(duration);
        titleText.setText(title);
        descriptionText.setText(description);

        // Obtener estado del tema para el botón
        int progressState = progressPrefs.getInt(topicId, 0);
        String buttonText = "EMPEZAR";
        int colorRes = R.color.orange_locked;

        if (progressState == 1) {
            buttonText = "CONTINUAR";
            colorRes = R.color.blue_in_progress;
        } else if (progressState == 2) {
            buttonText = "COMPLETADO";
            colorRes = R.color.green_completed;
        }

        actionButton.setText(buttonText);
        statusIndicator.setBackgroundColor(ContextCompat.getColor(this, colorRes));

        // Configurar acción del botón
        actionButton.setOnClickListener(v -> {
            openTopicActivity(topicId, title);
        });

        // Configurar clic en toda la tarjeta
        topicCard.setOnClickListener(v -> {
            openTopicActivity(topicId, title);
        });

        favoritesContainer.addView(topicCard);
    }

    private void openTopicActivity(String topicId, String title) {
        Intent intent = new Intent(this, Leccion1.class);
        intent.putExtra("CONCEPT_TITLE", title);
        intent.putExtra("TOPIC_ID", topicId);
        startActivity(intent);
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refrescar cuando se regrese de una lección
        loadFavorites();
    }
}

