package com.example.seminconductoresninos;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class ComponentesActivity extends AppCompatActivity {
    private String currentTopicId = "componentes";
    private SharedPreferences progressPrefs;
    private int score = 0;
    private int currentQuestion = 0;
    // Preguntas y respuestas
    private final String[] questions = {
            "¿Qué componente proporciona energía al circuito?",
            "¿Qué componente ilumina cuando pasa la corriente?",
            "¿Qué componente permite abrir o cerrar el circuito?",
            "¿Qué componente conduce la electricidad?",
            "¿Qué componente limita el paso de la corriente?"
    };
    private final String[][] options = {
            {"Bombilla", "Batería", "Cable", "Interruptor"},
            {"Batería", "Bombilla", "Cable", "Motor"},
            {"Bombilla", "Batería", "Interruptor", "Resistencia"},
            {"Bombilla", "Batería", "Cable", "Interruptor"},
            {"Bombilla", "Batería", "Resistencia", "Cable"}
    };
    private final int[] correctAnswers = {1, 1, 2, 2, 2};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_componentes);

        progressPrefs = getSharedPreferences("topic_progress", MODE_PRIVATE);
        setupToolbar();
        setupQuiz();
        setupFavoriteButton();
    }
    private void setupToolbar() {
        ImageButton backButton = findViewById(R.id.backButton);
        if (backButton != null) {
            backButton.setOnClickListener(v -> finish());
        }
        TextView title = findViewById(R.id.activityTitle);
        if (title != null) {
            title.setText("Identifica Componentes");
        }
    }
    private void setupFavoriteButton() {
        ImageButton favoriteButton = findViewById(R.id.favoriteButton);
        if (favoriteButton != null) {
            SharedPreferences favPrefs = getSharedPreferences("favorites", MODE_PRIVATE);
            boolean isFavorite = favPrefs.getBoolean(currentTopicId, false);
            updateFavoriteIcon(favoriteButton, isFavorite);
            favoriteButton.setOnClickListener(v -> {
                boolean newFavoriteState = !isFavorite;
                SharedPreferences.Editor editor = favPrefs.edit();
                editor.putBoolean(currentTopicId, newFavoriteState);
                editor.apply();
                updateFavoriteIcon(favoriteButton, newFavoriteState);
                String message = newFavoriteState ? "Añadido a favoritos" : "Eliminado de favoritos";
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            });
        }
    }
    private void updateFavoriteIcon(ImageButton button, boolean isFavorite) {
        if (isFavorite) {
            button.setImageResource(R.drawable.ic_favorite);
        } else {
            button.setImageResource(R.drawable.ic_favorite_border);
        }
    }
    private void setupQuiz() {
        showQuestion();
        // Configurar botones de opciones
        Button option1 = findViewById(R.id.option1);
        Button option2 = findViewById(R.id.option2);
        Button option3 = findViewById(R.id.option3);
        Button option4 = findViewById(R.id.option4);
        Button nextButton = findViewById(R.id.nextButton);
        Button restartButton = findViewById(R.id.restartButton);
        // Listeners para las opciones
        if (option1 != null) {
            option1.setOnClickListener(v -> checkAnswer(0));
        }
        if (option2 != null) {
            option2.setOnClickListener(v -> checkAnswer(1));
        }
        if (option3 != null) {
            option3.setOnClickListener(v -> checkAnswer(2));
        }
        if (option4 != null) {
            option4.setOnClickListener(v -> checkAnswer(3));
        }
        // Botón Siguiente
        if (nextButton != null) {
            nextButton.setOnClickListener(v -> nextQuestion());
        }
        // Botón Reiniciar
        if (restartButton != null) {
            restartButton.setOnClickListener(v -> restartQuiz());
        }
    }
    private void showQuestion() {
        if (currentQuestion < questions.length) {
            TextView questionText = findViewById(R.id.questionText);
            Button option1 = findViewById(R.id.option1);
            Button option2 = findViewById(R.id.option2);
            Button option3 = findViewById(R.id.option3);
            Button option4 = findViewById(R.id.option4);
            TextView progressText = findViewById(R.id.progressText);
            if (questionText != null) {
                questionText.setText(questions[currentQuestion]);
            }
            if (option1 != null) option1.setText(options[currentQuestion][0]);
            if (option2 != null) option2.setText(options[currentQuestion][1]);
            if (option3 != null) option3.setText(options[currentQuestion][2]);
            if (option4 != null) option4.setText(options[currentQuestion][3]);
            if (progressText != null) {
                progressText.setText("Pregunta " + (currentQuestion + 1) + " de " + questions.length);
            }
            // Resetear colores de botones
            resetOptionButtons();
            // Mostrar/ocultar botones según sea necesario
            Button nextButton = findViewById(R.id.nextButton);
            if (nextButton != null) {
                nextButton.setEnabled(false);
                nextButton.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.gray));
            }
        } else {
            showResults();
        }
    }
    private void checkAnswer(int selectedOption) {
        boolean isCorrect = (selectedOption == correctAnswers[currentQuestion]);
        // Cambiar color del botón según si es correcto o incorrecto
        Button selectedButton = getOptionButton(selectedOption);
        Button correctButton = getOptionButton(correctAnswers[currentQuestion]);
        if (selectedButton != null) {
            if (isCorrect) {
                selectedButton.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.green_completed));
                score++;
                Toast.makeText(this, "¡Correcto! 🎉", Toast.LENGTH_SHORT).show();
            } else {
                selectedButton.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.red_error));
                if (correctButton != null) {
                    correctButton.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.green_completed));
                }
                Toast.makeText(this, "Incorrecto 😔", Toast.LENGTH_SHORT).show();
            }
        }
        // Deshabilitar todos los botones de opción
        disableOptionButtons();
        // Habilitar botón Siguiente
        Button nextButton = findViewById(R.id.nextButton);
        if (nextButton != null) {
            nextButton.setEnabled(true);
            nextButton.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.blue_primary));
        }
    }
    private Button getOptionButton(int index) {
        switch (index) {
            case 0: return findViewById(R.id.option1);
            case 1: return findViewById(R.id.option2);
            case 2: return findViewById(R.id.option3);
            case 3: return findViewById(R.id.option4);
            default: return null;
        }
    }
    private void resetOptionButtons() {
        int[] optionIds = {R.id.option1, R.id.option2, R.id.option3, R.id.option4};
        for (int id : optionIds) {
            Button button = findViewById(id);
            if (button != null) {
                button.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.light_gray));
                button.setEnabled(true);
            }
        }
    }
    private void disableOptionButtons() {
        int[] optionIds = {R.id.option1, R.id.option2, R.id.option3, R.id.option4};
        for (int id : optionIds) {
            Button button = findViewById(id);
            if (button != null) {
                button.setEnabled(false);
            }
        }
    }
    private void nextQuestion() {
        currentQuestion++;
        showQuestion();
    }
    private void restartQuiz() {
        currentQuestion = 0;
        score = 0;
        showQuestion();
        TextView resultText = findViewById(R.id.resultText);
        if (resultText != null) {
            resultText.setVisibility(View.GONE);
        }
        Button restartButton = findViewById(R.id.restartButton);
        if (restartButton != null) {
            restartButton.setVisibility(View.GONE);
        }
        Button completeButton = findViewById(R.id.completeButton);
        if (completeButton != null) {
            completeButton.setVisibility(View.GONE);
        }
    }
    private void showResults() {
        TextView questionText = findViewById(R.id.questionText);
        LinearLayout optionsLayout = findViewById(R.id.optionsLayout);
        TextView progressText = findViewById(R.id.progressText);
        TextView resultText = findViewById(R.id.resultText);
        Button nextButton = findViewById(R.id.nextButton);
        Button restartButton = findViewById(R.id.restartButton);
        Button completeButton = findViewById(R.id.completeButton);
        if (questionText != null) {
            questionText.setText("¡Quiz Completado!");
        }
        if (optionsLayout != null) {
            optionsLayout.setVisibility(View.GONE);
        }
        if (progressText != null) {
            progressText.setText("Resultado Final");
        }
        if (resultText != null) {
            resultText.setVisibility(View.VISIBLE);
            String resultMessage = "Obtuviste " + score + " de " + questions.length + " respuestas correctas\n\n";
            if (score == questions.length) {
                resultMessage += "¡Excelente! \nEres un experto en componentes de circuitos";
                markTopicAsCompleted();
            } else if (score >= questions.length / 2) {
                resultMessage += "¡Buen trabajo! \nVas por buen camino";
                markTopicAsInProgress();
            } else {
                resultMessage += "Sigue practicando \nPuedes intentarlo de nuevo";
            }
            resultText.setText(resultMessage);
        }
        if (nextButton != null) {
            nextButton.setVisibility(View.GONE);
        }
        if (restartButton != null) {
            restartButton.setVisibility(View.VISIBLE);
        }
        if (completeButton != null) {
            completeButton.setVisibility(View.VISIBLE);
            completeButton.setOnClickListener(v -> {
                Toast.makeText(this, "¡Actividad completada! 🎉", Toast.LENGTH_SHORT).show();
                finish();
            });
        }
    }
    private void markTopicAsInProgress() {
        SharedPreferences.Editor editor = progressPrefs.edit();
        editor.putInt(currentTopicId, 1); // IN_PROGRESS
        editor.apply();
    }
    private void markTopicAsCompleted() {
        SharedPreferences.Editor editor = progressPrefs.edit();
        editor.putInt(currentTopicId, 2); // COMPLETED
        editor.apply();
    }
}