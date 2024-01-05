package com.pax.demo.modules.scanner;

import android.os.Bundle;
import android.os.Handler;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.pax.dal.entity.EScannerType;
import com.pax.demo.R;
import com.pax.demo.base.BaseFragment;

public class ScanFragment extends BaseFragment implements OnClickListener {

    private TextView resultTv;
    private EScannerType scannerType = EScannerType.REAR;
    private Button frontBt, rearBt, leftBt, rightBt;
    private EditText timeOutEt;

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:
                    resultTv.setText(getText(R.string.scanner_result) + msg.obj.toString());
                    break;
                default:
                    break;
            }
        };
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scanner_layout, container, false);

        scannerType = EScannerType.valueOf(getArguments().getString("scannerType"));

        resultTv = (TextView) view.findViewById(R.id.fragment_scanner_result);
        frontBt = (Button) view.findViewById(R.id.fragment_scanner_front);
        rearBt = (Button) view.findViewById(R.id.fragment_scanner_rear);
        leftBt = (Button) view.findViewById(R.id.fragment_scanner_left);
        rightBt = (Button) view.findViewById(R.id.fragment_scanner_right);

        timeOutEt = (EditText) view.findViewById(R.id.fragment_scanner_timeout_et);

        resultTv.setMovementMethod(new ScrollingMovementMethod());
        
        if (scannerType == EScannerType.EXTERNAL) {
            frontBt.setVisibility(View.GONE);
            rearBt.setVisibility(View.GONE);
            leftBt.setText("Start");
            rightBt.setText("Stop");
            leftBt.setOnClickListener(this);
            rightBt.setOnClickListener(this);
        } else {
            frontBt.setOnClickListener(this);
            rearBt.setOnClickListener(this);
            leftBt.setOnClickListener(this);
            rightBt.setOnClickListener(this);
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ScannerTester.getInstance(EScannerType.FRONT).close();
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void clearResult() {
        resultTv.setText(getText(R.string.scanner_result));
    }

    private int getTimeout() {
        String time = (timeOutEt.getText() == null || timeOutEt.getText().toString().equals("")) ? timeOutEt.getHint().toString() : timeOutEt.getText().toString();
        return Integer.parseInt(time);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_scanner_front:
                clearResult();
                ScannerTester.getInstance(EScannerType.FRONT).scan(handler,getTimeout());
                break;
            case R.id.fragment_scanner_rear:
                clearResult();
                ScannerTester.getInstance(EScannerType.REAR).scan(handler,getTimeout());
                break;
            case R.id.fragment_scanner_left:
                clearResult();
                if (scannerType == EScannerType.EXTERNAL) {
                    ScannerTester.getInstance(EScannerType.EXTERNAL).scan(handler,getTimeout());
                } else {
                    ScannerTester.getInstance(EScannerType.LEFT).scan(handler,getTimeout());
                }
                break;
            case R.id.fragment_scanner_right:
                clearResult();
                if (scannerType == EScannerType.EXTERNAL) {
                    ScannerTester.getInstance(EScannerType.EXTERNAL).close();
                } else {
                    ScannerTester.getInstance(EScannerType.RIGHT).scan(handler,getTimeout());
                }
                break;
            default:
                break;
        }
    }
}
