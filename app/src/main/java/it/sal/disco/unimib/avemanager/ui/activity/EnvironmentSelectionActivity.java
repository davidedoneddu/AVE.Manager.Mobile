package it.sal.disco.unimib.avemanager.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import android.os.Bundle;
import android.widget.FrameLayout;

import dagger.hilt.android.AndroidEntryPoint;
import it.sal.disco.unimib.avemanager.R;

@AndroidEntryPoint
public class EnvironmentSelectionActivity extends AppCompatActivity {

    private FrameLayout progressOverlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_environment_selection);
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