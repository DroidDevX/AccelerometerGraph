package com.example.accelerometergraph;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

/**
 * A custom fragment that is used to graph x,y,z values obtained
 * from the device's accelerometer in real time.
 * This class uses a 3rd party library known as MPCharts to perform
 * rendering of values.
 * */


public class MPAcceleromterGraph extends AccelerometerGraph{
    private static final String TAG = "MPAcceleromterGraph";
    private int counter=0;
    LineChart chart;
    int XAXIS_MAX_SIZE =20;
    ArrayList<Entry> xValues;
    ArrayList<Entry> yValues;
    ArrayList<Entry> zValues;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        xValues = new ArrayList<>();
        yValues = new ArrayList<>();
        zValues = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.mpchart_accelerometer,container,false);
        chart = rootView.findViewById(R.id.acceleromterChart);
        initLineChart(chart);

        return rootView;
    }

    private void initLineChart(LineChart chart){
        chart.setDragEnabled(true);
        chart.setScaleEnabled(false);
        chart.getAxisLeft().setDrawGridLines(false);
        chart.getAxisRight().setDrawGridLines(false);
        chart.getXAxis().setDrawLabels(true);
        chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        chart.setDrawGridBackground(false);
        chart.fitScreen();
    }


    @Override
    public void drawData(Cords cords) {
        Log.e(TAG,"drawData()");
        if(xValues ==null || yValues==null ||zValues==null)
            return;


        trimListIfMAX(xValues,XAXIS_MAX_SIZE);
        trimListIfMAX(yValues,XAXIS_MAX_SIZE);
        trimListIfMAX(zValues,XAXIS_MAX_SIZE);


            xValues.add(new Entry(counter,cords.getX()));
            yValues.add(new Entry(counter,cords.getY()));
            zValues.add(new Entry(counter,cords.getZ()));
            counter++;


            //Each entry goes into a data set
            LineDataSet xDataSet = new LineDataSet(xValues,"X");
            LineDataSet yDataSet = new LineDataSet(yValues,"Y");
            LineDataSet zDataSet = new LineDataSet(zValues,"Z");

            //set colors
            xDataSet.setColor(Color.RED);
            yDataSet.setColor(Color.GREEN);
            zDataSet.setColor(Color.BLUE);

            //Add datasets into the line data
            LineData data = new LineData(xDataSet,yDataSet,zDataSet);



            //Add line data to chart
            chart.fitScreen();
            chart.clear();
            chart.setData(data);
            chart.notifyDataSetChanged();
            chart.invalidate();
    }

    /**
     * Removes first item of the list if the list at max size
     * */
    void trimListIfMAX(ArrayList<Entry> list,int MAX_SIZE){
        if(list ==null)
            return;
        if(list.size()==0)
            return;
        if(list.size()==MAX_SIZE)
            list.remove(0);
    }
}
