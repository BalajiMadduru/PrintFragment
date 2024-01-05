package com.pax.demo.modules.picc;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.pax.dal.entity.EPiccType;
import com.pax.demo.R;
import com.pax.demo.base.BaseFragment;

public class DetectABFragment extends BaseFragment {

    private TextView textView;
    private RadioGroup radioGroup;
    public static DetectABThread detectABThread;
    private EPiccType piccType;
    private PiccTester tester;

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:
                    textView.setText(msg.obj.toString());
                    break;

                default:
                    break;
            }
        };
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_picc_detect, container, false);
        textView = (TextView) view.findViewById(R.id.fragment_picc_detect_textview);
        piccType = EPiccType.valueOf(getArguments().getString("piccType"));

        tester = PiccTester.getInstance(piccType);
        if (detectABThread == null) {
            detectABThread = new DetectABThread();
            tester.open();
            detectABThread.start();
        }
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        tester.setLed((byte) 0x00);// close all lights.
        tester.close();
        if (detectABThread != null) {
            detectABThread.interrupt();
            detectABThread = null;
        }
    }

    class DetectABThread extends Thread {
        @Override
        public void run() {
            super.run();
            textView.post(new Runnable() {
                @Override
                public void run() {
                    textView.setText("searching A card and B card...");
                }
            });
            while (!Thread.interrupted()) {
                int i = tester.detectAorBandCommand(handler);
                if (i == 1) {
                    break;
                }
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    // Log.i("ss", "exit thread" + getId());
                    break;
                }
            }
        }
    }
}
