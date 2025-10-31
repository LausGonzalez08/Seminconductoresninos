package com.example.seminconductoresninos;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class Home extends AppCompatActivity {
    Spinner Edad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        // 🔽 Aquí sí puedes vincular la vista
        Edad = findViewById(R.id.spinnerEdad);

        // Configurar el adaptador del spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                new String[]{"Seleccionar", "10", "11", "12", "13", "14", "15", "16", "17", "18"}
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Edad.setAdapter(adapter);
    }
}
