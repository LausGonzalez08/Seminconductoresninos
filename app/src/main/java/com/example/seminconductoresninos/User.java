package com.example.seminconductoresninos;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class User extends AppCompatActivity {
    EditText editTextNombre;
    Spinner spinnerEdad;
    Button btnIniciar;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user);
        // Verifica datos
        SharedPreferences prefs = getSharedPreferences("UserData", Context.MODE_PRIVATE);
        String nombreGuardado = prefs.getString("nombre", null);
        if (nombreGuardado != null) {
            // ¿Ya se lleno antes?
            Intent intent = new Intent(this, Home.class);
            startActivity(intent);
            finish(); // cerrar esta pantalla
            return;
        }
        // Si llega aquí, es porque no hay datos guardados
        editTextNombre = findViewById(R.id.editTextNombre);
        spinnerEdad = findViewById(R.id.spinnerEdad);
        btnIniciar = findViewById(R.id.btnIniciar);
        // Llenar spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                new String[]{"Seleccionar", "10", "11", "12", "13", "14", "15", "16", "17", "18"}
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEdad.setAdapter(adapter);
        // Botón iniciar
        btnIniciar.setOnClickListener(v -> {
            String nombre = editTextNombre.getText().toString().trim();
            String edad = spinnerEdad.getSelectedItem().toString();

            if (nombre.isEmpty() || edad.equals("Seleccionar")) {
                Toast.makeText(this, "Por favor ingresa tu nombre y edad", Toast.LENGTH_SHORT).show();
                return;
            }
            // Guardar datos en SharedPreferences
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("nombre", nombre);
            editor.putString("edad", edad);
            editor.apply();
            // Ir a siguiente pantalla
            Intent intent = new Intent(this, Home.class);
            startActivity(intent);
            finish();
        });
    }
}