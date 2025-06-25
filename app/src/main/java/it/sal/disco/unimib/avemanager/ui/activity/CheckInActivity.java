package it.sal.disco.unimib.avemanager.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.OptIn;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ExperimentalGetImage;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.mlkit.vision.barcode.BarcodeScanner;
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.common.InputImage;

import java.util.concurrent.ExecutionException;

import dagger.hilt.android.AndroidEntryPoint;
import it.sal.disco.unimib.avemanager.R;
import it.sal.disco.unimib.avemanager.ui.fragment.utils.MaterialDialogFragment;
import it.sal.disco.unimib.avemanager.ui.model.CheckInResult;
import it.sal.disco.unimib.avemanager.ui.viewmodel.CheckInViewModel;
import it.sal.disco.unimib.avemanager.util.DataCallback;

@AndroidEntryPoint
public class CheckInActivity extends AppCompatActivity {

    private PreviewView previewView;
    private CheckInViewModel viewModel;
    private boolean isProcessing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in);
        previewView = findViewById(R.id.previewView);

        FloatingActionButton backButton = findViewById(R.id.backButton);

        backButton.setEnabled(false);
        backButton.setOnClickListener(v -> {
            finish();
        });
        backButton.setEnabled(true);

        viewModel = new ViewModelProvider(this).get(CheckInViewModel.class);

        viewModel.getCheckInResult().observe(this, result -> {
            if (result != null) {
                MaterialDialogFragment dialog = MaterialDialogFragment.newInstance(
                        true,
                        "Check-in riuscito",
                        "Utente: " + result.getNomeUtente()
                );
                dialog.show(getSupportFragmentManager(), "CheckInSuccess");
            }
        });
        viewModel.getErrorMessage().observe(this, message -> {
            if (message != null) {
                MaterialDialogFragment dialog = MaterialDialogFragment.newInstance(false, "Errore", message);
                dialog.show(getSupportFragmentManager(), "CheckInError");
            }
        });
        setupCamera();
    }

    private void setupCamera() {
        BarcodeScanner scanner = BarcodeScanning.getClient();
        ListenableFuture<ProcessCameraProvider> future = ProcessCameraProvider.getInstance(this);
        future.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = future.get();
                Preview preview = new Preview.Builder().build();
                preview.setSurfaceProvider(previewView.getSurfaceProvider());

                ImageAnalysis analysis = new ImageAnalysis.Builder().build();
                analysis.setAnalyzer(ContextCompat.getMainExecutor(this), image -> processImage(scanner, image));

                cameraProvider.bindToLifecycle(this, CameraSelector.DEFAULT_BACK_CAMERA, preview, analysis);
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }, ContextCompat.getMainExecutor(this));
    }

    @OptIn(markerClass = ExperimentalGetImage.class)
    private void processImage(BarcodeScanner scanner, ImageProxy image) {
        if (image.getImage() == null || isProcessing) {
            image.close();
            return;
        }

        // Blocca subito la nuova elaborazione (prima di scanner.process)
        isProcessing = true;

        InputImage input = InputImage.fromMediaImage(image.getImage(), image.getImageInfo().getRotationDegrees());

        scanner.process(input)
                .addOnSuccessListener(barcodes -> {
                    boolean foundCode = false;
                    for (Barcode barcode : barcodes) {
                        String code = barcode.getRawValue();
                        if (code != null && !foundCode) {
                            foundCode = true;
                            viewModel.checkInWithQr(code);
                            break;
                        }
                    }

                    if (!foundCode) {
                        // Se non trovi codice valido sblocca
                        isProcessing = false;
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("CheckIn", "Errore lettura QR", e);
                    // Sblocca anche in caso di errore
                    isProcessing = false;
                })
                .addOnCompleteListener(task -> image.close());
    }
    public void reactivateCheckIn() {
        isProcessing = false;
    }
}