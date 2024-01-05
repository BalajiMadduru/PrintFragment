package com.pax.demo.modules.ped;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.pax.demo.R;
import com.pax.demo.base.BasePedFragment;
import com.pax.demo.util.Convert;

public class GetKCVFragment extends BasePedFragment {

    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private TextView resultText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_ped_writekey, container, false);
        button1 = (Button) view.findViewById(R.id.fragment_ped_writeKey_bt1);
        button1.setVisibility(View.GONE);
        button2 = (Button) view.findViewById(R.id.fragment_ped_writeKey_bt2);
        button2.setText("getTPK  KCV");
        button3 = (Button) view.findViewById(R.id.fragment_ped_writeKey_bt3);
        button3.setText("getTAK  KCV");
        button4 = (Button) view.findViewById(R.id.fragment_ped_writeKey_bt4);
        button4.setText("getTDK  KCV");
        resultText = (TextView) view.findViewById(R.id.fragment_ped_writeKey_result_text);
        button2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                byte[] tpk = PedTester.getInstance(pedType).getKCV_TPK();
                if (tpk != null) {
                    resultText.setText(getString(R.string.ped_kcvvalue_tpk1)
                            + Convert.getInstance().bcdToStr(tpk));
                } else {
                    resultText.setText(getString(R.string.ped_kcvvalue_tpk2));
                }
            }
        });
        button3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                byte[] tak = PedTester.getInstance(pedType).getKCV_TAK();
                if (tak != null) {
                    resultText.setText(getString(R.string.ped_kcvvalue_tak1)
                            + Convert.getInstance().bcdToStr(tak));
                } else {
                    resultText.setText(getString(R.string.ped_kcvvalue_tak2));
                }
            }
        });
        button4.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                byte[] tdk = PedTester.getInstance(pedType).getKCV_TDK();
                if (tdk != null) {
                    resultText.setText(getString(R.string.ped_kcvvalue_tdk1)
                            + Convert.getInstance().bcdToStr(tdk));
                } else {
                    resultText.setText(getString(R.string.ped_kcvvalue_tdk2));
                }
            }
        });
        return view;
    }
}
