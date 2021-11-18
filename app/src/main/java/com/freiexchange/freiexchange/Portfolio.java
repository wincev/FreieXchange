package com.freiexchange.freiexchange;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.Utils;
import org.json.JSONException;

import java.util.ArrayList;

public class Portfolio extends AppCompatActivity {

    LineChart chartView;
    SharedPreferences pairs;
    String FREIPREFS = "freiex";
    String ESTBALLIST = "estBalList";

    public static Intent makeIntent(Context contClass) {
        return new Intent(contClass, Portfolio.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.portfolio);

        chartView = findViewById(R.id.chart);
        chartView.setTouchEnabled(true);
        chartView.setPinchZoom(true);

        fillchart();
    }

    public String getSharedPrefs(String pair) {
        pairs = getSharedPreferences(FREIPREFS, Context.MODE_PRIVATE);
        String pairsString = pairs.getString(pair, "");
        return pairsString;
    }



    private void fillchart() {
        ArrayList<Entry> values = new ArrayList<>();
        ArrayList<MainItem> tickerArray = new ArrayList<>();

        try {
            tickerArray = MainItem.mainItemsFromJson(getSharedPrefs(ESTBALLIST));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < tickerArray.size(); i++) {
            Entry NewEntry = new Entry(Float.parseFloat(tickerArray.get(i).getTickerName()), Float.parseFloat(tickerArray.get(i).getTickerPrice()));
            values.add(NewEntry); }

        values.add(new Entry(1, 50));
        values.add(new Entry(2, 100));

        LineDataSet set1;
        if (chartView.getData() != null &&
                chartView.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) chartView.getData().getDataSetByIndex(0);
            set1.setValues(values);
            chartView.getData().notifyDataChanged();
            chartView.notifyDataSetChanged();
        } else {
            set1 = new LineDataSet(values, "Sample Data");
            set1.setDrawIcons(false);
            set1.enableDashedLine(10f, 5f, 0f);
            set1.enableDashedHighlightLine(10f, 5f, 0f);
            set1.setColor(Color.DKGRAY);
            set1.setCircleColor(Color.DKGRAY);
            set1.setLineWidth(1f);
            set1.setCircleRadius(3f);
            set1.setDrawCircleHole(false);
            set1.setValueTextSize(9f);
            set1.setDrawFilled(true);
            set1.setFormLineWidth(1f);
            set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            set1.setFormSize(15.f);
            if (Utils.getSDKInt() >= 18) {
                Drawable drawable = ContextCompat.getDrawable(this, R.drawable.backgound_gadient);
                set1.setFillDrawable(drawable);
            } else {
                set1.setFillColor(Color.DKGRAY);
            }
            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);
            LineData data = new LineData(dataSets);
            chartView.setData(data);
        }
    }
}
