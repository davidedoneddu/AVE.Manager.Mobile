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

import java.util.concurrent.atomic.AtomicReference;

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

    public BottomNavigationView bottomNavigationView;
    private FloatingActionButton fabScanQr;
    private AppBarLayout topBar;
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 1001;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_main);



        Window window = getWindow();
        window.getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        );
        window.setStatusBarColor(Color.TRANSPARENT);
        View decor = getWindow().getDecorView();
        decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        AtomicReference<BottomNavigationView> bottomNavigationView = new AtomicReference<>(findViewById(R.id.bottom_navigation));
        Drawable background = bottomNavigationView.get().getBackground();

        if (background instanceof ColorDrawable) {
            int color = ((ColorDrawable) background).getColor();
            window.setNavigationBarColor(color);
        }else{
            bottomNavigationView.get().setBackgroundColor(Color.parseColor("#F0F0F0"));
        }



        //gestione APP BAR con titolo e sottotitolo e click impostazioni
        TextView titleText = findViewById(R.id.titleText);
        TextView subtitleText = findViewById(R.id.subtitleText);

        // Recupera i dati passati dall'intent
        String orgName = getIntent().getStringExtra("org_name");
        String eventName = getIntent().getStringExtra("event_name");


        // Imposta i TextView direttamente
        if (orgName != null) {
            titleText.setText(orgName);
        }

        if (eventName != null) {
            subtitleText.setText(eventName);
        }
        ImageButton logoutBtn = findViewById(R.id.logoutButton);
        logoutBtn.setOnClickListener(v -> new AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Sei sicuro di voler effettuare il logout?")
                .setPositiveButton("Sì", (dialog, which) -> {

                    invalidateSession(); // metodo per invalidare la sessione utente

                    // Torna alla LoginActivity svuotando lo stack
                    Intent intent = new Intent(this, LoginPageActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                })
                .setNegativeButton("Annulla", null)
                .show());

        //gestione bottom navigation bar con apertura lettore QR
        bottomNavigationView.set(findViewById(R.id.bottom_navigation));
        fabScanQr = findViewById(R.id.fabScanQr);
        topBar = findViewById(R.id.appBarLayout);

        bottomNavigationView.get().setOnItemSelectedListener(item -> {
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

        getSupportFragmentManager().addOnBackStackChangedListener(() -> {

            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
            bottomNavigationView.set(findViewById(R.id.bottom_navigation));
            if (currentFragment == null) {

                // Nessun fragment attualmente visibile
                closeFullScreen();
                bottomNavigationView.get().getMenu().setGroupCheckable(0, false, true); // deseleziona tutto
                return;
            }

            if (currentFragment instanceof HomePageFragment) {
                closeFullScreen();
                if (bottomNavigationView.get().getSelectedItemId() != R.id.nav_home) {
                    bottomNavigationView.get().setSelectedItemId(R.id.nav_home);
                }
            } else if (currentFragment instanceof ManageInvitatiFragment) {
                closeFullScreen();
                if (bottomNavigationView.get().getSelectedItemId() != R.id.nav_invitati) {
                    bottomNavigationView.get().setSelectedItemId(R.id.nav_invitati);
                }
            } else if (currentFragment instanceof EventSettingsFragment) {
                closeFullScreen();
                if (bottomNavigationView.get().getSelectedItemId() != R.id.nav_event_settings) {
                    bottomNavigationView.get().setSelectedItemId(R.id.nav_event_settings);
                }
            } else if (currentFragment instanceof HelpFragment) {
                closeFullScreen();
                if (bottomNavigationView.get().getSelectedItemId() != R.id.nav_help) {
                    bottomNavigationView.get().setSelectedItemId(R.id.nav_help);
                }
            } else if (currentFragment instanceof CheckInFragment) {
                openFullScreen();
            } else {
                // Nel caso ci siano altri fragment o full screen nascosto
                closeFullScreen();
                bottomNavigationView.get().getMenu().setGroupCheckable(0, false, true); // opzionale per deselezionare tutti
            }
        });

        if (savedInstanceState == null) {
            openFragment(new HomePageFragment());
        }
    }

    private void startQrScanner() {
        Fragment fragment = new CheckInFragment();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .addToBackStack(null)
                .commit();

        findViewById(R.id.bottom_navigation).setVisibility(View.GONE);
        findViewById(R.id.fabScanQr).setVisibility(View.GONE);
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

    private void openFragment(Fragment fragment) {
        if (fragment instanceof CheckInFragment ) {
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
        params.setBehavior(null);  // Rimuove il comportamento di scorrimento
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

    private void invalidateSession() {
        // Esempio: cancella SharedPreferences
        LoginViewModel viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        viewModel.getLogoutState().observe(this, state -> {
            if (state == LoginViewModel.LogoutState.ERROR) {
                new AlertDialog.Builder(this)
                        .setTitle("Errore")
                        .setMessage("Si è verificato un errore durante il logout. Riprova.")
                        .setPositiveButton("OK", null)
                        .show();
            }else if(state == LoginViewModel.LogoutState.SUCCESS){
                SharedPreferences prefs = getSharedPreferences("session", Context.MODE_PRIVATE);
                prefs.edit().clear().apply();
            }
        });

        viewModel.logout();
    }
}
