package it.sal.disco.unimib.avemanager.ui.fragment.utils;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;

import it.sal.disco.unimib.avemanager.R;
import it.sal.disco.unimib.avemanager.ui.fragment.mainactivity.CheckInFragment;


public class MaterialDialogFragment extends DialogFragment {

    public static MaterialDialogFragment newInstance(boolean success, String title, String message) {
        MaterialDialogFragment fragment = new MaterialDialogFragment();
        Bundle args = new Bundle();
        args.putBoolean("success", success);
        args.putString("title", title);
        args.putString("message", message);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_material_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ImageView imageView = view.findViewById(R.id.image_result);
        TextView titleView = view.findViewById(R.id.text_title);
        TextView messageView = view.findViewById(R.id.text_message);
        MaterialButton buttonOk = view.findViewById(R.id.button_ok);

        Bundle args = getArguments();
        if (args != null) {
            boolean success = args.getBoolean("success");
            String title = args.getString("title");
            String message = args.getString("message");

            titleView.setText(title);
            messageView.setText(message);
            imageView.setImageResource(success ? R.drawable.ic_circle_success : R.drawable.ic_circle_error);
        }

        buttonOk.setOnClickListener(v -> dismiss());
    }

    @Override
    public void onStart() {
        super.onStart();
        // Rendi il background trasparente
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);


        }


    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        // Chiamiamo la funzione di CheckInActivity se è l'host
        Fragment fragment = requireActivity()
                .getSupportFragmentManager()
                .findFragmentById(R.id.fragmentContainer);

        if (fragment instanceof CheckInFragment) {
            ((CheckInFragment) fragment).reactivateCheckIn();
        }

    }
}