package com.ourproject.mohankumardhakal.agroproject.FragmentClasses;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.ourproject.mohankumardhakal.agroproject.R;

import java.util.List;

public class PieChartFragment extends Fragment {
    PieChart pieChart;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.graph_layout, null);
        pieChart = view.findViewById(R.id.pieChart);
        List<>
        return view;
    }
}
