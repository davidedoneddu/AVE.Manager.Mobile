package it.sal.disco.unimib.avemanager.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import it.sal.disco.unimib.avemanager.R;

public class SplashCustomActivity extends AppCompatActivity {

    private VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);


        window.getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );

        setContentView(R.layout.activity_splash_custom);

        videoView = findViewById(R.id.splashVideoView);
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.splash_animation);
        videoView.setVideoURI(uri);

        videoView.setZOrderOnTop(true);

        videoView.setOnPreparedListener(mp -> {
            videoView.setAlpha(0f);

            // Start appena pronto
            videoView.start();

            // Fade-in immediato (non aspettare 200ms)
            videoView.animate().alpha(1f).setDuration(600).start();
        });

        videoView.setOnCompletionListener(mp -> {
            // Evita black frame finale
            videoView.pause();
            videoView.seekTo(videoView.getDuration());

            // Fade-out rapido e switch
            videoView.animate().alpha(0f).setDuration(100).withEndAction(() -> {
                videoView.setVisibility(View.INVISIBLE);
                startActivity(new Intent(SplashCustomActivity.this, LoginPageActivity.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }).start();
        });
    }
}