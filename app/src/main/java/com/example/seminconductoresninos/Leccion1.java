package com.example.seminconductoresninos;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Leccion1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Enlaza la actividad con el nuevo layout XML
        setContentView(R.layout.activity_leccion1);
        setupToolbarActions();
    }

    private void setupToolbarActions() {
        // 1. Botón de Cerrar (X)
        ImageButton closeButton = findViewById(R.id.closeButton);
        if (closeButton != null) {
            closeButton.setOnClickListener(v -> finish()); // Cierra la actividad y regresa a HomeMain
        }

        // 2. Botón de Búsqueda (Lupa)
        ImageButton searchButton = findViewById(R.id.searchButton);
        if (searchButton != null) {
            searchButton.setOnClickListener(v -> showToast("Funcionalidad de búsqueda no implementada"));
        }

        // 3. Botón de Menú (3 puntos)
        ImageButton menuButton = findViewById(R.id.menuButton);
        if (menuButton != null) {
            menuButton.setOnClickListener(v -> showToast("Opciones de menú no implementadas"));
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}