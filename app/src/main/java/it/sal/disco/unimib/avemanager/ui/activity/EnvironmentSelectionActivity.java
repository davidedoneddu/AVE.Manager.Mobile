package it.sal.disco.unimib.avemanager.ui.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import dagger.hilt.android.AndroidEntryPoint;
import it.sal.disco.unimib.avemanager.R;

@AndroidEntryPoint
public class EnvironmentSelectionActivity extends AppCompatActivity {

    private FrameLayout progressOverlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_environment_selection);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            );
            window.setStatusBarColor(Color.TRANSPARENT);
        }

        View fragmentContainer = findViewById(R.id.nav_host_fragment_environment_selection);
        ViewCompat.setOnApplyWindowInsetsListener(fragmentContainer, (view, insets) -> {
            Insets statusBarInsets = insets.getInsets(WindowInsetsCompat.Type.statusBars());
            view.setPadding(
                    view.getPaddingLeft(),
                    statusBarInsets.top,
                    view.getPaddingRight(),
                    view.getPaddingBottom()
            );
            return insets;
        });
        progressOverlay = findViewById(R.id.loadingOverlay);
    }

    public void showLoader() {
        if (progressOverlay != null) {
            progressOverlay.setVisibility(View.VISIBLE);
        }
    }

    public void hideLoader() {
        if (progressOverlay != null) {
            progressOverlay.setVisibility(View.GONE);
        }
    }
}