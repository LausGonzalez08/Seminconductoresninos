package com.example.seminconductoresninos;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class HomeMain extends AppCompatActivity {
    private LinearLayout topicsContainer;
    private SharedPreferences progressPrefs;

    // Constantes para los temas
    private static final String TOPIC_CIRCUITOS = "circuitos";
    private static final String TOPIC_COMPONENTES = "componentes";
    private static final String TOPIC_ENERGIA = "energia";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homemain);

        // Inicializar SharedPreferences para el progreso
        progressPrefs = getSharedPreferences("topic_progress", MODE_PRIVATE);
        initializeViews();
        setupTopics();
        setupNavigation();
    }
    protected void onResume() {
        super.onResume();
        // Refrescar los temas cuando la actividad se reanuda
        setupTopics();
    }
    private void initializeViews() {
        topicsContainer = findViewById(R.id.topicsContainer);
    }
    private void setupNavigation() {
        LinearLayout navTemas = findViewById(R.id.nav_temas);
        LinearLayout navPerfil = findViewById(R.id.nav_perfil);
        LinearLayout navFavoritos = findViewById(R.id.nav_favoritos);
        if (navTemas != null) {
            navTemas.setOnClickListener(v -> showToast("Ya estás en Temas"));
        }
        if (navPerfil != null) {
            navPerfil.setOnClickListener(v -> {
                Intent intent = new Intent(HomeMain.this, PerfilActivity.class);
                startActivity(intent);
            });
        }
        if (navFavoritos != null) {
            navFavoritos.setOnClickListener(v -> {
                // Cambiar de showToast a abrir FavoritosActivity
                Intent intent = new Intent(HomeMain.this, Favoritos.class);
                startActivity(intent);
            });
        }
    }
    private void setupTopics() {
        topicsContainer.removeAllViews();
        // Tema 1: Conceptos básicos
        addTopicCard(
                TOPIC_CIRCUITOS,
                "Conceptos básicos",
                "15 min",
                "¿Qué son los circuitos?",
                "¿Has escuchado hablar sobre los circuitos eléctricos? ¿Sabes cómo funciona un circuito? ¿Con qué se come un circuito? En este apartado aprenderemos qué es un circuito electrónico y también averiguaremos cómo identificarlos y identificar cada componente."
        );
        // Tema 2: Actividad
        addTopicCard(
                TOPIC_COMPONENTES,
                "Actividad",
                "10 min",
                "¿Puedes identificar un componente de un circuito?",
                "¿Sabes qué es un componente de un circuito? Ayúdame a identificarlo..."
        );
        // Tema 3: Concepto Básico
        addTopicCard(
                TOPIC_ENERGIA,
                "Concepto Básico",
                "15 min",
                "¿Qué es la energía?",
                "Cuidado, debes de ser cuidadoso la energía no siempre va a ser tu amigo, es necesario que sepas qué es, para qué sirve y cómo protegerte..."
        );
    }
    private void addTopicCard(String topicId, String category, String duration, String title, String description) {
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
        // Obtener estado del tema
        TopicState topicState = getTopicState(topicId);
        // Configurar botón y color según el estado
        switch (topicState) {
            case NOT_STARTED:
                actionButton.setText("EMPEZAR");
                statusIndicator.setBackgroundColor(ContextCompat.getColor(this, R.color.orange_locked));
                break;
            case IN_PROGRESS:
                actionButton.setText("CONTINUAR");
                statusIndicator.setBackgroundColor(ContextCompat.getColor(this, R.color.blue_in_progress));
                break;
            case COMPLETED:
                actionButton.setText("COMPLETADO");
                statusIndicator.setBackgroundColor(ContextCompat.getColor(this, R.color.green_completed));
                break;
        }
        // Configurar acción del botón
        actionButton.setOnClickListener(v -> {
            handleTopicAction(topicId, topicState, title);
        });
        // Configurar clic en toda la tarjeta
        topicCard.setOnClickListener(v -> {
            openTopicActivity(topicId, title);
        });
        topicsContainer.addView(topicCard);
    }
    private TopicState getTopicState(String topicId) {
        int state = progressPrefs.getInt(topicId, 0);
        return TopicState.fromValue(state);
    }
    private void handleTopicAction(String topicId, TopicState currentState, String title) {
        switch (currentState) {
            case NOT_STARTED:
                // Marcar como en progreso y abrir
                markTopicAsInProgress(topicId);
                openTopicActivity(topicId, title);
                break;
            case IN_PROGRESS:
                // Solo abrir (ya está en progreso)
                openTopicActivity(topicId, title);
                break;
            case COMPLETED:
                showToast("Ver resumen de: " + title);
                // Puedes abrir un resumen o volver a abrir la actividad
                openTopicActivity(topicId, title);
                break;
        }
    }
    private void openTopicActivity(String topicId, String title) {
        Intent intent;
        switch (topicId) {
            case TOPIC_CIRCUITOS:
                intent = new Intent(this, Leccion1.class);
                break;
            case TOPIC_COMPONENTES:
                intent = new Intent(this, ComponentesActivity.class);
                break;
            case TOPIC_ENERGIA:
                intent = new Intent(this, Leccion1.class);
                break;
            default:
                intent = new Intent(this, Leccion1.class);
        }
        intent.putExtra("CONCEPT_TITLE", title);
        intent.putExtra("TOPIC_ID", topicId);
        startActivity(intent);
    }
    private void markTopicAsInProgress(String topicId) {
        SharedPreferences.Editor editor = progressPrefs.edit();
        editor.putInt(topicId, TopicState.IN_PROGRESS.getValue());
        editor.apply();
        // Refrescar la vista para mostrar los cambios
        setupTopics();
    }
    public void markTopicAsCompleted(String topicId) {
        SharedPreferences.Editor editor = progressPrefs.edit();
        editor.putInt(topicId, TopicState.COMPLETED.getValue());
        editor.apply();
    }
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    // Enum para los estados del tema
    private enum TopicState {
        NOT_STARTED(0),
        IN_PROGRESS(1),
        COMPLETED(2);
        private final int value;
        TopicState(int value) {
            this.value = value;
        }
        public int getValue() {
            return value;
        }
        public static TopicState fromValue(int value) {
            for (TopicState state : TopicState.values()) {
                if (state.value == value) {
                    return state;
                }
            }
            return NOT_STARTED;
        }
    }
}