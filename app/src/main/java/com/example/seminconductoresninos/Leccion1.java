package com.example.seminconductoresninos;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class Leccion1 extends AppCompatActivity {

    private String currentTopicId;
    private ImageButton favoriteButton;
    private boolean isFavorite = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leccion1);

        // Obtener el topicId del Intent
        android.content.Intent intent = getIntent();
        String topicId = intent.getStringExtra("TOPIC_ID");
        currentTopicId = topicId != null ? topicId : "unknown";

        setupToolbarActions();
        setupBottomButtons();
        loadConceptContent();
        setupFavoriteButton();
    }

    private void setupToolbarActions() {
        ImageButton closeButton = findViewById(R.id.closeButton);
        if (closeButton != null) {
            closeButton.setOnClickListener(v -> finish());
        }

        favoriteButton = findViewById(R.id.favoriteButton);

        ImageButton menuButton = findViewById(R.id.menuButton);
        if (menuButton != null) {
            menuButton.setOnClickListener(v -> showToast("Mostrar opciones del concepto"));
        }
    }
    private void setupFavoriteButton() {
        favoriteButton = findViewById(R.id.favoriteButton);
        if (favoriteButton != null) {
            // Cargar estado actual de favorito
            android.content.SharedPreferences favPrefs = getSharedPreferences("favorites", MODE_PRIVATE);
            isFavorite = favPrefs.getBoolean(currentTopicId, false);

            // Actualizar icono según el estado
            updateFavoriteIcon();

            // Configurar click listener
            favoriteButton.setOnClickListener(v -> toggleFavorite());
        }
    }

    private void toggleFavorite() {
        android.content.SharedPreferences favPrefs = getSharedPreferences("favorites", MODE_PRIVATE);
        android.content.SharedPreferences.Editor editor = favPrefs.edit();

        isFavorite = !isFavorite;
        editor.putBoolean(currentTopicId, isFavorite);
        editor.apply();

        updateFavoriteIcon();

        String message = isFavorite ? "Añadido a favoritos" : "Eliminado de favoritos";
        showToast(message);
    }

    private void updateFavoriteIcon() {
        if (favoriteButton != null) {
            if (isFavorite) {
                favoriteButton.setImageResource(R.drawable.ic_favorite); // Icono lleno
            } else {
                favoriteButton.setImageResource(R.drawable.ic_favorite_border); // Icono vacío
            }
        }
    }
    private void setupBottomButtons() {
        Button activityButton = findViewById(R.id.activityButton);
        Button finishButton = findViewById(R.id.finishButton);

        // Botón "ACTIVIDAD" - Marcar como completado
        if (activityButton != null) {
            activityButton.setOnClickListener(v -> {
                markTopicAsCompleted();
                showToast("¡Actividad completada!");
            });
        }

        // Botón "CERRAR" - Solo cerrar
        if (finishButton != null) {
            finishButton.setOnClickListener(v -> finish());
        }
    }

    private void markTopicAsCompleted() {
        // Usar SharedPreferences directamente para marcar como completado
        android.content.SharedPreferences prefs = getSharedPreferences("topic_progress", MODE_PRIVATE);
        android.content.SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(currentTopicId, 2); // 2 = COMPLETED
        editor.apply();

        showToast("¡Tema completado!");
    }

    private void loadConceptContent() {
        android.content.Intent intent = getIntent();
        String conceptTitle = intent.getStringExtra("CONCEPT_TITLE");

        if (conceptTitle == null) {
            conceptTitle = "CONCEPTO DESCONOCIDO";
        }

        TextView titleBar = findViewById(R.id.conceptTitle);
        TextView conceptBodyText = findViewById(R.id.conceptBodyText);
        TextView floatingTitleText = findViewById(R.id.burbuja);
        ImageView conceptImage = findViewById(R.id.conceptImage);

        titleBar.setText(conceptTitle);

        if (floatingTitleText != null) {
            floatingTitleText.setText(conceptTitle);
        }

        if (conceptImage == null) {
            Log.e("Leccion1", "ImageView conceptImage no encontrado en el layout.");
        }

        if (conceptTitle.equals("¿Qué son los circuitos?")) {
            conceptBodyText.setText(R.string.circuito_lorem_ipsum);
            if (conceptImage != null) {
                conceptImage.setImageResource(R.drawable.circuito_electronico);
            }
        } else if (conceptTitle.equals("¿Puedes identificar un componente de un circuito?")) {
            conceptBodyText.setText(R.string.actividad_identificacion_lorem);
            if (conceptImage != null) {
                conceptImage.setImageResource(R.drawable.act_circuito);
            }
        } else if (conceptTitle.equals("¿Qué es la energía?")) {
            conceptBodyText.setText(R.string.energia_lorem_ipsum);
            if (conceptImage != null) {
                conceptImage.setImageResource(R.drawable.img_energia);
            }
        } else {
            conceptBodyText.setText("Error: Contenido para " + conceptTitle + " no disponible.");
            if (conceptImage != null) {
                conceptImage.setImageResource(R.drawable.logo);
            }
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}