package it.sal.disco.unimib.avemanager.ui.fragment.utils;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.android.material.progressindicator.CircularProgressIndicator;

import java.util.ArrayList;

import it.sal.disco.unimib.avemanager.R;

public class CardEventDataFragment extends Fragment {

    private CircularProgressIndicator donutChart;
    private TextView centerText;
    private TextView votesPresentText, votesTotalText, peoplePresentText, peopleTotalText;

    private int votesPresent;
    private int votesTotal;
    private int peoplePresent;
    private int peopleTotal;

    public static CardEventDataFragment newInstance(int votesPresent, int votesTotal, int peoplePresent, int peopleTotal) {
        Bundle args = new Bundle();
        args.putInt("votesPresent", votesPresent);
        args.putInt("votesTotal", votesTotal);
        args.putInt("peoplePresent", peoplePresent);
        args.putInt("peopleTotal", peopleTotal);
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
            peoplePresent = getArguments().getInt("peoplePresent");
            peopleTotal = getArguments().getInt("peopleTotal");
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
        votesPresentText = view.findViewById(R.id.votesPresentText);
        votesTotalText = view.findViewById(R.id.votesTotalText);
        peoplePresentText = view.findViewById(R.id.peoplePresentText);
        peopleTotalText = view.findViewById(R.id.peopleTotalText);

        setupChart();
        updateText();
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

    private void updateText() {
        votesPresentText.setText("Voti presenti: " + votesPresent);
        votesTotalText.setText("Voti totali: " + votesTotal);
        peoplePresentText.setText("Persone presenti: " + peoplePresent);
        peopleTotalText.setText("Persone totali: " + peopleTotal);
    }
}