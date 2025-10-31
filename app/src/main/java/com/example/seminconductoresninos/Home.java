package com.example.seminconductoresninos;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Home extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        textView = findViewById(R.id.textView);

        // Recuperar los datos guardados en User.java
        SharedPreferences prefs = getSharedPreferences("UserData", Context.MODE_PRIVATE);
        String nombre = prefs.getString("nombre", "Usuario");

        // Mostrar saludo
        textView.setText("Hola " + nombre + " 👋");
    }
}
