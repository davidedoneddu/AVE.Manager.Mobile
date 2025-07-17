package it.sal.disco.unimib.avemanager.ui.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import dagger.hilt.android.AndroidEntryPoint;
import it.sal.disco.unimib.avemanager.R;
import it.sal.disco.unimib.avemanager.ui.fragment.mainactivity.CheckInFragment;
import it.sal.disco.unimib.avemanager.ui.fragment.mainactivity.EventSettingsFragment;
import it.sal.disco.unimib.avemanager.ui.fragment.mainactivity.HelpFragment;
import it.sal.disco.unimib.avemanager.ui.fragment.mainactivity.HomePageFragment;
import it.sal.disco.unimib.avemanager.ui.fragment.mainactivity.ManageInvitatiFragment;
import it.sal.disco.unimib.avemanager.ui.viewmodel.LoginViewModel;

@AndroidEntryPoint
public class EventMainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FloatingActionButton fabScanQr;
    private AppBarLayout topBar;
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_main);

        // Trasparenza status bar e navigation bar
        Window window = getWindow();
        window.getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        );
        window.setStatusBarColor(Color.TRANSPARENT);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        // Bottom navigation setup
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        Drawable background = bottomNavigationView.getBackground();
        if (background instanceof ColorDrawable) {
            int color = ((ColorDrawable) background).getColor();
            window.setNavigationBarColor(color);
        } else {
            bottomNavigationView.setBackgroundColor(Color.parseColor("#F0F0F0"));
        }

        // App bar title
        TextView titleText = findViewById(R.id.titleText);
        TextView subtitleText = findViewById(R.id.subtitleText);

        String orgName = getIntent().getStringExtra("org_name");
        String eventName = getIntent().getStringExtra("event_name");

        if (orgName != null) titleText.setText(orgName);
        if (eventName != null) subtitleText.setText(eventName);

        // Logout
        ImageButton logoutBtn = findViewById(R.id.logoutButton);
        logoutBtn.setOnClickListener(v -> new AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Sei sicuro di voler effettuare il logout?")
                .setPositiveButton("Sì", (dialog, which) -> {
                    invalidateSession();
                    Intent intent = new Intent(this, LoginPageActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                })
                .setNegativeButton("Annulla", null)
                .show()
        );

        // FAB scan QR
        fabScanQr = findViewById(R.id.fabScanQr);
        fabScanQr.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                startQrScanner();
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
            }
        });

        // Top app bar
        topBar = findViewById(R.id.appBarLayout);

        // Bottom navigation listener
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

        // Fragment back stack listener
        getSupportFragmentManager().addOnBackStackChangedListener(() -> {
            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);

            if (currentFragment == null) {
                closeFullScreen();
                deselectAllBottomItems();
                return;
            }

            if (currentFragment instanceof HomePageFragment) {
                closeFullScreen();
                selectBottomItem(R.id.nav_home);
            } else if (currentFragment instanceof ManageInvitatiFragment) {
                closeFullScreen();
                selectBottomItem(R.id.nav_invitati);
            } else if (currentFragment instanceof EventSettingsFragment) {
                closeFullScreen();
                selectBottomItem(R.id.nav_event_settings);
            } else if (currentFragment instanceof HelpFragment) {
                closeFullScreen();
                selectBottomItem(R.id.nav_help);
            } else if (currentFragment instanceof CheckInFragment) {
                openFullScreen();
            } else {
                closeFullScreen();
                deselectAllBottomItems();
            }
        });

        if (savedInstanceState == null) {
            openFragment(new HomePageFragment());
        }
    }

    private void startQrScanner() {
        openFragment(new CheckInFragment());
    }

    private void openFragment(Fragment fragment) {
        if (fragment instanceof CheckInFragment) {
            openFullScreen();
        } else {
            closeFullScreen();
        }
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .addToBackStack(null)
                .commit();
    }

    public void openFullScreen() {
        bottomNavigationView.setVisibility(View.GONE);
        fabScanQr.setVisibility(View.GONE);
        topBar.setVisibility(View.GONE);

        View fragmentContainer = findViewById(R.id.fragmentContainer);
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) fragmentContainer.getLayoutParams();
        params.setBehavior(null);
        fragmentContainer.setLayoutParams(params);
    }

    public void closeFullScreen() {
        bottomNavigationView.setVisibility(View.VISIBLE);
        fabScanQr.setVisibility(View.VISIBLE);
        topBar.setVisibility(View.VISIBLE);

        View fragmentContainer = findViewById(R.id.fragmentContainer);
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) fragmentContainer.getLayoutParams();
        params.setBehavior(new AppBarLayout.ScrollingViewBehavior());
        fragmentContainer.setLayoutParams(params);
    }

    private void selectBottomItem(int itemId) {
        if (bottomNavigationView.getSelectedItemId() != itemId) {
            bottomNavigationView.setSelectedItemId(itemId);
        }
    }

    private void deselectAllBottomItems() {
        bottomNavigationView.getMenu().setGroupCheckable(0, false, true);
    }

    private void invalidateSession() {
        LoginViewModel viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        viewModel.getLogoutState().observe(this, state -> {
            if (state == LoginViewModel.LogoutState.ERROR) {
                new AlertDialog.Builder(this)
                        .setTitle("Errore")
                        .setMessage("Si è verificato un errore durante il logout. Riprova.")
                        .setPositiveButton("OK", null)
                        .show();
            } else if (state == LoginViewModel.LogoutState.SUCCESS) {
                SharedPreferences prefs = getSharedPreferences("session", Context.MODE_PRIVATE);
                prefs.edit().clear().apply();
            }
        });

        viewModel.logout();
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
