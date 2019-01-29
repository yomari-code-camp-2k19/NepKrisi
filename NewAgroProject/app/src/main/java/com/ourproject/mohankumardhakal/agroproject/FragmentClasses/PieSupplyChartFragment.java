package com.ourproject.mohankumardhakal.agroproject.FragmentClasses;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.ourproject.mohankumardhakal.agroproject.R;

import java.util.ArrayList;
import java.util.List;

public class PieSupplyChartFragment extends Fragment {
    PieChart pieChart;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.graph_layout, null);
        pieChart = view.findViewById(R.id.pieChart);
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 10, 5, 5);
        pieChart.setDragDecelerationFrictionCoef(0.99f);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setCenterText(generateCenterSpannableText());

        //radius of the transparent circle above hole
        pieChart.setTransparentCircleRadius(61f);
        List<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(18f, "Syangja"));
        entries.add(new PieEntry(16f, "Kaski"));
        entries.add(new PieEntry(24f, "Kathmandu"));
        entries.add(new PieEntry(12f, "Humla"));
        entries.add(new PieEntry(10f, "Janakpur"));
        entries.add(new PieEntry(12f, "Butwal"));
        entries.add(new PieEntry(5f, "Dolakha"));
        entries.add(new PieEntry(3f, "Manag"));
        PieDataSet set = new PieDataSet(entries, "High Supply");
        // entry label styling
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setEntryLabelTextSize(12f);
        //space between slices
        set.setSliceSpace(3f);
        set.setValueTextColor(Color.BLACK);
        //index valus for the values index for chart
        Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);
        //shift of the clicked part of piechart
        set.setSelectionShift(10f);
        set.setColors(ColorTemplate.JOYFUL_COLORS);
        PieData data = new PieData(set);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(15f);
        data.setValueTextColor(Color.BLACK);
        pieChart.setData(data);
        pieChart.invalidate();
        return view;
        }
    private SpannableString generateCenterSpannableText() {

        SpannableString s = new SpannableString("Supply Chart");
        s.setSpan(new RelativeSizeSpan(1.7f), 0, 12, 0);
        return s;
    }

}
