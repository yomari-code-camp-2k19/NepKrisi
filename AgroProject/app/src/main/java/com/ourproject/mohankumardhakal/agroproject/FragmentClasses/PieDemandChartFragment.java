package com.ourproject.mohankumardhakal.agroproject.FragmentClasses;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ourproject.mohankumardhakal.agroproject.HelperClasses.PostsAttributes;
import com.ourproject.mohankumardhakal.agroproject.HelperClasses.SpinnerAdapter;
import com.ourproject.mohankumardhakal.agroproject.R;

import java.util.ArrayList;
import java.util.List;

public class PieDemandChartFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    PieChart pieChart;
    FirebaseDatabase database;
    DatabaseReference dbRef;
    ArrayList<PostsAttributes> list;
    Spinner spinner;
    SpinnerAdapter spinnerAdapter;
    ArrayList<String> product_titlelist;
    int minIndex;
    View view;
    ArrayList<String> palceNames;
    ArrayList<Integer> productCounts;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.graph_layout_1, null);
        product_titlelist = new ArrayList<>();
        spinner = view.findViewById(R.id.myspinner);
        spinner.setOnItemSelectedListener(this);
        productCounts = new ArrayList<>();
        palceNames = new ArrayList<>();
        list = new ArrayList<>();
        productTitleCollection();
        return view;
    }

    public void plotChart() {
        pieChart = view.findViewById(R.id.pieChart);
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 10, 5, 5);
        pieChart.setDragDecelerationFrictionCoef(0.99f);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setCenterText(generateCenterSpannableText());
        pieChart.setTransparentCircleRadius(61f);
        List<PieEntry> entries = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            if (this.productCounts.get(i) != null) {
                entries.add(new PieEntry(this.productCounts.get(i), this.palceNames.get(i)));
            }
        }

        PieDataSet set = new PieDataSet(entries, "High Demand");
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
        set.setColors(ColorTemplate.MATERIAL_COLORS);
        PieData data = new PieData(set);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(15f);
        data.setValueTextColor(Color.BLACK);
        pieChart.setData(data);
        pieChart.invalidate();

    }

    private SpannableString generateCenterSpannableText() {
        SpannableString s = new SpannableString("Demand Chart");
        s.setSpan(new RelativeSizeSpan(1.7f), 0, 12, 0);
        return s;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String name = String.valueOf(spinner.getSelectedItem());
        Toast.makeText(getActivity(), "" + name, Toast.LENGTH_SHORT).show();
        countItemNumbers(name);

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    //collects the title of product
    public void productTitleCollection() {
        database = FirebaseDatabase.getInstance("https://agroproject-b9829.firebaseio.com/");
        dbRef = database.getInstance().getReference("Customer Posts");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds1 : dataSnapshot.getChildren()) {
                    for (DataSnapshot ds2 : ds1.getChildren()) {
                        PostsAttributes postsAttributes = ds2.getValue(PostsAttributes.class);
                        product_titlelist.add(postsAttributes.getPost_title());
                    }
                }

                spinnerAdapter = new SpinnerAdapter(getActivity(), product_titlelist);
                spinner.setAdapter(spinnerAdapter);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void numberAnalysis(ArrayList<Integer> productCounts) {
        int sum = 0;
        for (int a : productCounts) {
            sum += a;
        }
        for (int i = 0; i < productCounts.size(); i++) {
            this.productCounts.set(i, (int) calculatePercentage(productCounts.get(i), sum));
            Log.i("productCounts", String.valueOf(this.productCounts.get(i)));
        }
        plotChart();

    }

    public float calculatePercentage(int count, int total) {
        float temp;
        temp = (count * 100) / total;
        return temp;
    }

    public void countItemNumbers(final String name) {
        database = FirebaseDatabase.getInstance("https://agroproject-b9829.firebaseio.com/");
        dbRef = database.getInstance().getReference("Customer Posts");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String ItemToDraw = name;
                ArrayList<Integer> countlist = new ArrayList<>();
                ArrayList<String> locationslist = new ArrayList<>();
                for (DataSnapshot ds1 : dataSnapshot.getChildren()) {
                    for (DataSnapshot ds2 : ds1.getChildren()) {
                        PostsAttributes postsAttributes = ds2.getValue(PostsAttributes.class);
                        String location = postsAttributes.getPost_location();
                        String title = postsAttributes.getPost_title();
                        //calculatePercentage(location, title);
                        boolean flag = false;
                        for (int i = 0; i < locationslist.size(); i++) {
                            if (locationslist.get(i).equals(location)) {
                                int temp = countlist.get(i);
                                countlist.set(i, ++temp);
                                flag = !flag;
                                break;
                            }
                        }
                        if (flag == false) {
                            locationslist.add(location);
                            countlist.add(1);
                        }
                        list.add(postsAttributes);
                    }
                }
                minIndex = 0;
                int[] indexArray = new int[5];
                int[] tempvalues = new int[5];
                for (int i = 0; i < locationslist.size(); i++) {
                    if (countlist.get(i) >= tempvalues[minIndex]) {
                        indexArray[minIndex] = i;
                        tempvalues[minIndex] = countlist.get(i);
                        int min = tempvalues[0];
                        minIndex = 0;
                        for (int j = 0; j < 5; j++) {
                            if (tempvalues[j] < min) {
                                min = tempvalues[j];
                                minIndex = j;
                            }
                        }

                    }
                }
                for (int i = 0; i < 5; i++) {
                    productCounts.add(tempvalues[i]);
                    palceNames.add(locationslist.get(indexArray[i]));
                }
                numberAnalysis(productCounts);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
