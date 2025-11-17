package com.example.seminconductoresninos;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.content.FileProvider;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PerfilActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int TAKE_PHOTO_REQUEST = 2;
    private ImageView profileImage;
    private String currentPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        profileImage = findViewById(R.id.profileImage);
        setupNavigation();
        loadUserData();
        setupSwitches();
        setupProfileImage();
    }

    private void setupProfileImage() {
        // Cargar imagen guardada si existe
        loadSavedProfileImage();

        // Hacer clickable el avatar y el texto
        profileImage.setOnClickListener(v -> showImagePickerDialog());

        // También el texto "Cambiar foto"
        TextView changePhotoText = findViewById(R.id.changePhotoText);
        if (changePhotoText != null) {
            changePhotoText.setOnClickListener(v -> showImagePickerDialog());
        }
    }

    private void showImagePickerDialog() {
        final CharSequence[] options = {"Tomar foto", "Elegir de galería", "Cancelar"};

        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("Elige una opción");
        builder.setItems(options, (dialog, item) -> {
            if (options[item].equals("Tomar foto")) {
                takePhoto();
            } else if (options[item].equals("Elegir de galería")) {
                chooseFromGallery();
            } else if (options[item].equals("Cancelar")) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void takePhoto() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                Toast.makeText(this, "Error creando archivo", Toast.LENGTH_SHORT).show();
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.seminconductoresninos.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, TAKE_PHOTO_REQUEST);
            }
        }
    }

    private void chooseFromGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Selecciona una imagen"), PICK_IMAGE_REQUEST);
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(null);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == PICK_IMAGE_REQUEST && data != null && data.getData() != null) {
                // Imagen seleccionada de galería
                Uri selectedImageUri = data.getData();
                profileImage.setImageURI(selectedImageUri);
                saveProfileImageUri(selectedImageUri.toString());

            } else if (requestCode == TAKE_PHOTO_REQUEST) {
                // Foto tomada con cámara
                File file = new File(currentPhotoPath);
                if (file.exists()) {
                    Uri photoUri = Uri.fromFile(file);
                    profileImage.setImageURI(photoUri);
                    saveProfileImageUri(photoUri.toString());
                }
            }
        }
    }

    private void saveProfileImageUri(String imageUri) {
        SharedPreferences prefs = getSharedPreferences("UserData", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("profileImageUri", imageUri);
        editor.apply();
        Toast.makeText(this, "Foto de perfil actualizada", Toast.LENGTH_SHORT).show();
    }

    private void loadSavedProfileImage() {
        SharedPreferences prefs = getSharedPreferences("UserData", MODE_PRIVATE);
        String imageUriString = prefs.getString("profileImageUri", null);

        if (imageUriString != null) {
            Uri imageUri = Uri.parse(imageUriString);
            try {
                profileImage.setImageURI(imageUri);
            } catch (Exception e) {
                // Si hay error, cargar imagen por defecto
                profileImage.setImageResource(R.drawable.ic_default_avatar);
            }
        }
    }

    // ... (el resto de los métodos permanece igual)
    private void setupNavigation() {
        LinearLayout navTemas = findViewById(R.id.nav_temas);
        LinearLayout navPerfil = findViewById(R.id.nav_perfil);
        LinearLayout navFavoritos = findViewById(R.id.nav_favoritos);

        navTemas.setOnClickListener(v -> {
            finish();
        });

        navFavoritos.setOnClickListener(v -> {
            Toast.makeText(this, "Ir a Favoritos", Toast.LENGTH_SHORT).show();
        });
    }

    private void loadUserData() {
        SharedPreferences prefs = getSharedPreferences("UserData", MODE_PRIVATE);
        String nombre = prefs.getString("nombre", "Usuario");

        TextView userName = findViewById(R.id.userName);
        userName.setText(nombre);
    }

    private void setupSwitches() {
        SwitchCompat soundSwitch = findViewById(R.id.soundSwitch);
        SwitchCompat notificationsSwitch = findViewById(R.id.notificationsSwitch);
        SwitchCompat kidModeSwitch = findViewById(R.id.kidModeSwitch);

        // Cargar preferencias guardadas
        SharedPreferences prefs = getSharedPreferences("AppSettings", MODE_PRIVATE);
        soundSwitch.setChecked(prefs.getBoolean("sound", true));
        notificationsSwitch.setChecked(prefs.getBoolean("notifications", true));
        kidModeSwitch.setChecked(prefs.getBoolean("kidMode", true));

        // Guardar cambios
        soundSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            saveSetting("sound", isChecked);
        });

        notificationsSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            saveSetting("notifications", isChecked);
        });

        kidModeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            saveSetting("kidMode", isChecked);
        });
    }

    private void saveSetting(String key, boolean value) {
        SharedPreferences.Editor editor = getSharedPreferences("AppSettings", MODE_PRIVATE).edit();
        editor.putBoolean(key, value);
        editor.apply();
    }
    }
