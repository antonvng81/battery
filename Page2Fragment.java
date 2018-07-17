package com.example.joananton.battery;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Page2Fragment extends Fragment {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Data members
    //

    private TextView mTextView1;
    private TextView mTextView2;
    private TextView mTextView3;
    private ChartView mChartView1;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Methods
    //

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.page_2, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mTextView1 = (TextView) view.findViewById(R.id.Page2_TextView1);
        mTextView2 = (TextView) view.findViewById(R.id.Page2_TextView2);
        mTextView3 = (TextView) view.findViewById(R.id.Page2_TextView3);
        mChartView1 = (ChartView) view.findViewById(R.id.Page2_ChartView1);
    }


    public void onDataReceived(Intent intent) {

        if (intent.getAction().equals(BatteryService.ACTION_GET_BATTERY_INFO_ARRAY)) {

            String[] dias_semana = {"Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado", "Domingo"};

            int[] fecha_inicio = BatteryService.getBatteryInfoArray().getTimeStart();
            int[] fecha_fin = BatteryService.getBatteryInfoArray().getTimeEnd();

            String str = "Batería al" +
                    Integer.toString(BatteryService.getBatteryInfo().getLevel()) + "%";
            mTextView1.setText(str);

            str = "Fecha inicio:\n" + dias_semana[fecha_inicio[3]] + " "
                    + Integer.toString(fecha_inicio[2]) + ":"
                    + Integer.toString(fecha_inicio[1]) + ":"
                    + Integer.toString(fecha_inicio[0]);
            mTextView2.setText(str);

            str = "Fecha fin:\n" + dias_semana[fecha_fin[3]] + " "
                    + Integer.toString(fecha_fin[2]) + ":"
                    + Integer.toString(fecha_fin[1]) + ":"
                    + Integer.toString(fecha_fin[0]) ;
            mTextView3.setText(str);


            mChartView1.setChartInfo(new BatteryChartInfo(BatteryService.getBatteryInfoArray()));

            if (!mChartView1.isShown())
                mChartView1.setVisibility(View.VISIBLE);

            mChartView1.invalidate();

        } else if (intent.getAction().equals(BatteryService.ACTION_EXIT_SERVICE)) {
            mChartView1.setChartInfo(null);
            mChartView1.setVisibility(View.INVISIBLE);
        }
    }

    public void onActivityClose() {
        mChartView1.setChartInfo(null);
        mChartView1.setVisibility(View.INVISIBLE);
    }

}
