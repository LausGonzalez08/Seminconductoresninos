package com.example.seminconductoresninos;
import android.os.Bundle;
import android.widget.Toast;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
// Importación duplicada eliminada: import androidx.appcompat.app.AppCompatActivity;

public class HomeMain extends AppCompatActivity {
    private LinearLayout topicsContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homemain);
        initializeViews();
        setupTopics();
        setupNavigation();
    }
    private void initializeViews() {
        topicsContainer = findViewById(R.id.topicsContainer);
    }

    private void setupNavigation() {
        // Navegación inferior
        LinearLayout navTemas = findViewById(R.id.nav_temas);
        LinearLayout navPerfil = findViewById(R.id.nav_perfil);
        LinearLayout navFavoritos = findViewById(R.id.nav_favoritos);

        if (navTemas != null) {
            navTemas.setOnClickListener(v -> showToast("Ya estás en Temas"));
        }

        if (navPerfil != null) {
            navPerfil.setOnClickListener(v -> showToast("Ir a Mi Perfil"));
        }

        if (navFavoritos != null) {
            navFavoritos.setOnClickListener(v -> showToast("Ir a Favoritos"));
        }
    }

    private void setupTopics() {
        // Limpiar contenedor
        topicsContainer.removeAllViews();

        // Tema 1: Conceptos básicos (Completado)
        addTopicCard(
                "Conceptos básicos",
                "15 min - Terminado",
                "¿Qué son los circuitos?",
                "¿Has escuchado hablar sobre los circuitos eléctricos? ¿Sabes cómo funciona un circuito? ¿Con qué se come un circuito? En este apartado aprenderemos qué es un circuito electrónico y también averiguaremos cómo identificarlos y identificar cada componente.",
                "COMPLETADO",
                R.color.green_completed
        );

        // Tema 2: Actividad (En proceso)
        addTopicCard(
                "Actividad",
                "10 min - En proceso",
                "¿Puedes identificar un componente de un circuito?",
                "¿Sabes qué es un componente de un circuito? Ayúdame a identificarlo...",
                "CONTINUAR",
                R.color.blue_in_progress
        );

        // Tema 3: Concepto Básico (Por desbloquear)
        addTopicCard(
                "Concepto Básico",
                "15 min - Por desbloquear",
                "¿Qué es la energía?",
                "Cuidado, debes de ser cuidadoso la energía no siempre va a ser tu amigo, es necesario que sepas qué es, para qué sirve y cómo protegerte...",
                "EMPEZAR",
                R.color.orange_locked
        );
    }

    private void addTopicCard(String category, String duration, String title, String description, String buttonText, int statusColorRes) {
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
        actionButton.setText(buttonText);

        // Color del estado
        int statusColor = ContextCompat.getColor(this, statusColorRes);
        statusIndicator.setBackgroundColor(statusColor);

        // Configurar acción del botón
        actionButton.setOnClickListener(v -> {
            handleTopicAction(category, buttonText);
        });

        // Configurar clic en toda la tarjeta
        topicCard.setOnClickListener(v -> {
            // Se utiliza un Intent para pasar de HomeMain a la nueva actividad
            // Nota: El nombre de la actividad debe ser ConceptDetailActivity o el que uses (Leccion1.class)
            android.content.Intent intent = new android.content.Intent(this, Leccion1.class);

            // Pasar el título para cargar el contenido dinámicamente
            intent.putExtra("CONCEPT_TITLE", title);

            startActivity(intent);
        });

        // Agregar al contenedor (Única vez)
        topicsContainer.addView(topicCard);
    } // <--- ¡Cierre ÚNICO y CORRECTO del método!

    private void handleTopicAction(String category, String action) {
        switch (action) {
            case "COMPLETADO":
                showToast("Ver resumen de: " + category);
                break;
            case "CONTINUAR":
                showToast("Continuar con: " + category);
                break;
            case "EMPEZAR":
                showToast("Comenzar: " + category);
                break;
        }
    }

    private void showToast(String message) {
        android.widget.Toast.makeText(this, message, android.widget.Toast.LENGTH_SHORT).show();
    }
} // <--- Cierre de la clase HomeMain