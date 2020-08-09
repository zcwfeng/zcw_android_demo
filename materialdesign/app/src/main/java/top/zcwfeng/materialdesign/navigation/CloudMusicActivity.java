package top.zcwfeng.materialdesign.navigation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import com.google.android.material.navigation.NavigationView;
import top.zcwfeng.materialdesign.R;
import top.zcwfeng.materialdesign.databinding.ActivityCloudMusicBinding;

public class CloudMusicActivity extends AppCompatActivity {

    private ActivityCloudMusicBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        binding = ActivityCloudMusicBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.llMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.drawerLayout.closeDrawers();
            }
        });

        binding.navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                binding.drawerLayout.closeDrawer(GravityCompat.END);
                return true;
            }
        });
    }
}