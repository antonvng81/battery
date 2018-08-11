package com.example.joananton.battery;

import android.content.Intent;
import android.os.BatteryManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Page1Fragment extends Fragment {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Data members
    //

    private CheckBox mCheckBox1;
    private TextView mTextView1;
    private TextView mTextView2;
    private TextView mTextView3;
    private TextView mTextView4;
    private TextView mTextView5;
    private ProgressBar mProgressBar1;

    private String[] mTextSpanish = {
            "En estado de carga al ",
            "Descargando al ",
            "Temperatura", "Voltaje", "Tecnología",
            "Batería estropeada", "Batería en buen estado", "Batería sobrecalentada", "Sin datos de salud de la batería",
            "Fallo no especificado leyendo el estado de salud de la batería",
            "Servicio detenido"
    };

    private String[] mText;

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // Methods
    //

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.page_1, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mCheckBox1 = (CheckBox) view.findViewById(R.id.Page1_CheckBox1);
        mTextView1 = (TextView) view.findViewById(R.id.Page1_TextView1);
        mTextView2 = (TextView) view.findViewById(R.id.Page1_TextView2);
        mTextView3 = (TextView) view.findViewById(R.id.Page1_TextView3);
        mTextView4 = (TextView) view.findViewById(R.id.Page1_TextView4);
        mTextView5 = (TextView) view.findViewById(R.id.Page1_TextView5);
        mProgressBar1 = (ProgressBar) view.findViewById(R.id.Page1_ProgressBar1);

        mText = mTextSpanish;

        if (!BatteryService.isInstanceCreated()) {
            mCheckBox1.setChecked(false);
            removeInfo();
        } else {
            mCheckBox1.setChecked(true);
            updateInfo();
        }
    }

    public void onDataReceived(Intent intent) {

        if (intent.getAction().equals(BatteryService.ACTION_GET_BATTERY_INFO)) {

            if ((!mCheckBox1.isChecked()) && BatteryService.isInstanceCreated())
                mCheckBox1.setChecked(true);
            updateInfo();

        } else if (intent.getAction().equals(BatteryService.ACTION_EXIT_SERVICE)) {
            mCheckBox1.setChecked(false);
            removeInfo();
        }
    }

    private void removeInfo() {
        mProgressBar1.setVisibility(ProgressBar.INVISIBLE);
        mTextView1.setText("");
        mTextView2.setText("");
        mTextView3.setText("");
        mTextView4.setText("");
        mTextView5.setText("");
    }

    private void updateInfo() {

        String tv1_text;
        String tv2_text;
        String tv3_text;
        String tv4_text;
        String tv5_text;
        int pb_progress;


        BatteryInfo info = BatteryService.getBatteryInfo();

        if (info == null) {
            return;
        }

        if (info.getPlugged() > 0)
            tv1_text = mText[0];
        else
            tv1_text = mText[1];

        if (info.getScale() != 0)
            pb_progress = (info.getLevel() * 100) / info.getScale();
        else
            pb_progress = info.getLevel();

        tv1_text = tv1_text + String.valueOf(pb_progress) + "%";

        tv2_text = mText[2] + " " + Integer.toString(info.getTemperature()) + " K";
        tv3_text = mText[3] + " " + Integer.toString(info.getVoltage()) + " mV";
        tv4_text = mText[4] + " " + info.getTechnology();

        switch (info.getHealth()) {
            case BatteryManager.BATTERY_HEALTH_DEAD:
                tv5_text = mText[5];
                break;
            case BatteryManager.BATTERY_HEALTH_GOOD:
                tv5_text = mText[6];
                break;
            case BatteryManager.BATTERY_HEALTH_OVERHEAT:
                tv5_text = mText[7];
                break;
            case BatteryManager.BATTERY_HEALTH_UNKNOWN:
                tv5_text = mText[8];
                break;
            //case BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE:
            default:
                tv5_text = mText[9];
                break;
        }

        mProgressBar1.setVisibility(ProgressBar.VISIBLE);
        mProgressBar1.setProgress(pb_progress);

        mTextView1.setText(tv1_text);
        mTextView2.setText(tv2_text);
        mTextView3.setText(tv3_text);
        mTextView4.setText(tv4_text);
        mTextView5.setText(tv5_text);
    }

    public void onActivityClose() {
        removeInfo();
    }

    public void Page1_CheckBox1_OnClick(View view) {
        boolean isChecked = ((CheckBox) view).isChecked();
        if (isChecked) {
            if (!BatteryService.isInstanceCreated()) {
                Intent intent = new Intent(getActivity(), BatteryService.class);
                getActivity().startService(intent);
            }
        } else {
            if (BatteryService.isInstanceCreated()) {
                Intent intent = new Intent(getActivity(), BatteryService.class);
                getActivity().stopService(intent);
            }
        }
    }
}
