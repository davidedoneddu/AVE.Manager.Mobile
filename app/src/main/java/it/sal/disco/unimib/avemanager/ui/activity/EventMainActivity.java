package it.sal.disco.unimib.avemanager.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import it.sal.disco.unimib.avemanager.R;
import it.sal.disco.unimib.avemanager.ui.fragment.environmentselection.EventSelectionFragment;
import it.sal.disco.unimib.avemanager.ui.fragment.mainactivity.CheckInFragment;
import it.sal.disco.unimib.avemanager.ui.fragment.mainactivity.EventSettingsFragment;
import it.sal.disco.unimib.avemanager.ui.fragment.mainactivity.HelpFragment;
import it.sal.disco.unimib.avemanager.ui.fragment.mainactivity.HomePageFragment;
import it.sal.disco.unimib.avemanager.ui.fragment.mainactivity.ManageInvitatiFragment;

public class EventMainActivity extends AppCompatActivity {

    private static final int CAMERA_PERMISSION_REQUEST_CODE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        FloatingActionButton fabScanQr = findViewById(R.id.fabScanQr);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                openFragment(new HomePageFragment());
                return true;
            } else if (id == R.id.nav_invitati) {
                openFragment(new ManageInvitatiFragment());
                return true;
            } else if (id == R.id.nav_event_settings) {
                openFragment(new EventSettingsFragment());
                return true;
            } else if (id == R.id.nav_help) {
                openFragment(new HelpFragment());
                return true;
            }else return true;
        });

        fabScanQr.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED) {
                startQrScanner();
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
            }
        });

        if (savedInstanceState == null) {
            openFragment(new HomePageFragment());
        }
    }

    private void startQrScanner() {
        Intent intent = new Intent(this, CheckInActivity.class);
        startActivity(intent);
    }

    private void openFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startQrScanner();
            } else {
                Toast.makeText(this, "Permesso fotocamera negato", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
