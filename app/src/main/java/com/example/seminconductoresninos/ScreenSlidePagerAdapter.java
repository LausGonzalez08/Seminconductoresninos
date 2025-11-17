package com.example.seminconductoresninos;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ScreenSlidePagerAdapter extends FragmentStateAdapter {
    public ScreenSlidePagerAdapter(FragmentActivity fa) {
        super(fa);
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new WelcomeFragment();
            case 1:
                return new SafetyFragment();
            case 2:
                return new RatingFragment();
            default:
                return new WelcomeFragment();
        }
    }
    @Override
    public int getItemCount() {
        return 3;
    }
}