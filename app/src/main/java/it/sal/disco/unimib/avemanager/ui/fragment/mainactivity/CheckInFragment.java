package it.sal.disco.unimib.avemanager.ui.fragment.mainactivity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.OptIn;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ExperimentalGetImage;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.transition.MaterialSharedAxis;
import com.google.mlkit.vision.barcode.BarcodeScanner;
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.common.InputImage;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutionException;

import dagger.hilt.android.AndroidEntryPoint;
import it.sal.disco.unimib.avemanager.R;
import it.sal.disco.unimib.avemanager.ui.fragment.utils.MaterialDialogFragment;
import it.sal.disco.unimib.avemanager.ui.viewmodel.CheckInViewModel;

@AndroidEntryPoint
public class CheckInFragment extends Fragment {

    private PreviewView previewView;
    private CheckInViewModel viewModel;
    private boolean isProcessing = false;
    private FloatingActionButton backButton;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Transizione asse X (orizzontale)
        setEnterTransition(new MaterialSharedAxis(MaterialSharedAxis.X, true));  // entra da destra
        setReturnTransition(new MaterialSharedAxis(MaterialSharedAxis.X, false)); // torna a sinistra

        setExitTransition(new MaterialSharedAxis(MaterialSharedAxis.X, true));    // esce a sinistra quando si va avanti
        setReenterTransition(new MaterialSharedAxis(MaterialSharedAxis.X, false)); // rientra da destra
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_check_in, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        previewView = view.findViewById(R.id.previewView);
        backButton = view.findViewById(R.id.backButton);

        backButton.setEnabled(false);
        backButton.setOnClickListener(v -> {
            // Torna indietro nel fragment stack o fai finish dell'activity se serve
            if (getActivity() != null) {
                getActivity().finish();
            }
        });
        backButton.setEnabled(true);

        viewModel = new ViewModelProvider(this).get(CheckInViewModel.class);

        viewModel.getCheckInResult().observe(getViewLifecycleOwner(), result -> {
            if (result != null) {
                MaterialDialogFragment dialog = MaterialDialogFragment.newInstance(
                        true,
                        "Check-in riuscito",
                        "Utente: " + result.getNomeUtente()
                );
                dialog.show(getParentFragmentManager(), "CheckInSuccess");
            }
        });

        viewModel.getErrorMessage().observe(getViewLifecycleOwner(), message -> {
            if (message != null) {
                MaterialDialogFragment dialog = MaterialDialogFragment.newInstance(false, "Errore", message);
                dialog.show(getParentFragmentManager(), "CheckInError");
            }
        });

        setupCamera();
    }

    private void setupCamera() {
        BarcodeScanner scanner = BarcodeScanning.getClient();
        ListenableFuture<ProcessCameraProvider> future = ProcessCameraProvider.getInstance(requireContext());
        future.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = future.get();
                Preview preview = new Preview.Builder().build();
                preview.setSurfaceProvider(previewView.getSurfaceProvider());

                ImageAnalysis analysis = new ImageAnalysis.Builder().build();
                analysis.setAnalyzer(ContextCompat.getMainExecutor(requireContext()), image -> processImage(scanner, image));

                cameraProvider.unbindAll();
                cameraProvider.bindToLifecycle(this, CameraSelector.DEFAULT_BACK_CAMERA, preview, analysis);

            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }, ContextCompat.getMainExecutor(requireContext()));
    }

    @OptIn(markerClass = ExperimentalGetImage.class)
    private void processImage(BarcodeScanner scanner, ImageProxy image) {
        if (image.getImage() == null || isProcessing) {
            image.close();
            return;
        }

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
                    isProcessing = false;
                })
                .addOnCompleteListener(task -> image.close());
    }

    public void reactivateCheckIn() {
        isProcessing = false;
    }
}