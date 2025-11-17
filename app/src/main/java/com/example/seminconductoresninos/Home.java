package com.example.seminconductoresninos;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import androidx.viewpager2.widget.ViewPager2;
import androidx.appcompat.app.AppCompatActivity;

public class Home extends AppCompatActivity {
    TextView textView;
    private ViewPager2 viewPager;
    private ScreenSlidePagerAdapter pagerAdapter;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prefs = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
        boolean hasSeenIntro = prefs.getBoolean("hasSeenIntro", false);

        if (hasSeenIntro) {
            startMainActivity();
            return;
        }
        // Si no ha visto la introducción, mostrar las pantallas
        setContentView(R.layout.activity_home);

        textView = findViewById(R.id.textView);
        viewPager = findViewById(R.id.viewPager);
        pagerAdapter = new ScreenSlidePagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setUserInputEnabled(false);
    }
    public void goToNextPage() {
        int currentItem = viewPager.getCurrentItem();
        if (currentItem < pagerAdapter.getItemCount() - 1) {
            viewPager.setCurrentItem(currentItem + 1, true);
        } else {
            // Cuando llega a la última pantalla, marcar como visto y ir a MainActivity
            markIntroAsSeen();
            startMainActivity();
        }
    }
    public void markIntroAsSeen() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("hasSeenIntro", true);
        editor.apply();
    }
    public void startMainActivity() {
        Intent intent = new Intent(this, HomeMain.class);
        startActivity(intent);
        finish();
    }
}