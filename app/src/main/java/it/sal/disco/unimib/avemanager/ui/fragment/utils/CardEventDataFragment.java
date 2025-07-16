package it.sal.disco.unimib.avemanager.ui.fragment.utils;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.progressindicator.CircularProgressIndicator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import it.sal.disco.unimib.avemanager.R;

public class CardEventDataFragment extends Fragment {

    private CircularProgressIndicator donutChart;
    private TextView centerText;
    private TextView quorumStatusText;

    private int votesPresent;
    private int votesTotal;
    private boolean isQuorumOk;
    private String quorumDate;

    public static CardEventDataFragment newInstance(int votesPresent, int votesTotal, boolean isQuorumOk, String quorumDate) {
        Bundle args = new Bundle();
        args.putInt("votesPresent", votesPresent);
        args.putInt("votesTotal", votesTotal);
        args.putBoolean("isQuorumOk", isQuorumOk);
        args.putString("quorumDate", quorumDate);
        CardEventDataFragment fragment = new CardEventDataFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            votesPresent = getArguments().getInt("votesPresent");
            votesTotal = getArguments().getInt("votesTotal");
            isQuorumOk = getArguments().getBoolean("isQuorumOk");
            quorumDate = getArguments().getString("quorumDate");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_card_event_data, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        donutChart = view.findViewById(R.id.donutChart);
        centerText = view.findViewById(R.id.centerText);
        quorumStatusText = view.findViewById(R.id.quorumStatusText);

        setupChart();
        setupQuorumStatus();

        View container = view.findViewById(R.id.donutChartContainer);
        container.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                container.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                int width = container.getWidth();
                int height = container.getHeight();
                int size = (int) (Math.min(width, height) * 1f);

                donutChart.setIndicatorSize(size);
            }
        });
    }

    private void setupChart() {
        if (votesTotal > 0) {
            int percent = (votesPresent * 100) / votesTotal;
            donutChart.setProgress(percent);
            centerText.setText(percent + "%");
        } else {
            donutChart.setProgress(0);
            centerText.setText("0%");
        }
    }

    private void setupQuorumStatus() {
        if (isQuorumOk && !TextUtils.isEmpty(quorumDate)) {
            try {
                LocalDateTime dateTime = LocalDateTime.parse(quorumDate, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                String formattedDate = dateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

                quorumStatusText.setText("Quorum raggiunto alle ore " + formattedDate);
            } catch (DateTimeParseException e) {
                quorumStatusText.setText("Quorum raggiunto alle ore " + quorumDate);
            }

        } else {
            quorumStatusText.setText("Quorum non ancora raggiunto");
        }
    }
}
