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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leccion1); 

        setupToolbarActions();
        setupBottomButtons(); //Configura los botones de la parte inferior
        loadConceptContent(); // llamada para cargar el contenido
    }

    private void setupToolbarActions() {
        // Botón de Cerrar
        ImageButton closeButton = findViewById(R.id.closeButton);
        if (closeButton != null) {
            closeButton.setOnClickListener(v -> finish());
        }

        // 2. Botón de Búsqueda
        ImageButton searchButton = findViewById(R.id.searchButton);
        if (searchButton != null) {
            searchButton.setOnClickListener(v -> showToast("Abrir búsqueda en el concepto"));
        }

        // Botón de Menú
        ImageButton menuButton = findViewById(R.id.menuButton);
        if (menuButton != null) {
            menuButton.setOnClickListener(v -> showToast("Mostrar opciones del concepto"));
        }
    }

    private void setupBottomButtons() {
        // Inicializar botones
        Button activityButton = findViewById(R.id.activityButton);
        Button finishButton = findViewById(R.id.finishButton);

        // Botón "ACTIVIDAD"
        if (activityButton != null) {
            activityButton.setOnClickListener(v -> showToast("¡A iniciar la actividad!"));
        }

        // Botón "CERRAR"
        if (finishButton != null) {
            //  Implementación del cierre/regreso
            finishButton.setOnClickListener(v -> finish());
        }
    }


    private void loadConceptContent() {
        // Obtener el Intent y el título
        android.content.Intent intent = getIntent();
        String conceptTitle = intent.getStringExtra("CONCEPT_TITLE");

        if (conceptTitle == null) {
            conceptTitle = "CONCEPTO DESCONOCIDO";
        }

        //Obtener referencias de las vistas
        TextView titleBar = findViewById(R.id.conceptTitle);
        TextView conceptBodyText = findViewById(R.id.conceptBodyText);
        TextView floatingTitleText = findViewById(R.id.burbuja);

        // El ImageView de la ilustración
        ImageView conceptImage = findViewById(R.id.conceptImage);

        //Lógica de Carga Condicional

        // Actualiza el título en la barra superior
        titleBar.setText(conceptTitle);

        // Actualiza el título en la burbuja flotante
        if (floatingTitleText != null) {
            floatingTitleText.setText(conceptTitle);
        }

        if (conceptImage == null) {
            // Precaución: si el ImageView no se encuentra, no intentamos actualizarlo.
            Log.e("Leccion1", "ImageView conceptImage no encontrado en el layout.");
        }

        if (conceptTitle.equals("¿Qué son los circuitos?")) {
            conceptBodyText.setText(R.string.circuito_lorem_ipsum);
            // CAMBIO DE IMAGEN: Para el tema de circuitos
            if (conceptImage != null) {
                conceptImage.setImageResource(R.drawable.circuito_electronico);
            }

        } else if (conceptTitle.equals("¿Puedes identificar un componente de un circuito?")) {
            conceptBodyText.setText(R.string.actividad_identificacion_lorem);
            // CAMBIO DE IMAGEN: Para el tema de actividad
            if (conceptImage != null) {
                conceptImage.setImageResource(R.drawable.act_circuito);
            }

        } else if (conceptTitle.equals("¿Qué es la energía?")) {
            conceptBodyText.setText(R.string.energia_lorem_ipsum);
            // CAMBIO DE IMAGEN: Para el tema de energía
            if (conceptImage != null) {
                conceptImage.setImageResource(R.drawable.img_energia);
            }

        } else {
            conceptBodyText.setText("Error: Contenido para " + conceptTitle + " no disponible.");
            // Si no se reconoce el título se pone un placeholder
            if (conceptImage != null) {
                conceptImage.setImageResource(R.drawable.logo); // Necesitarías crear esta imagen
            }
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}