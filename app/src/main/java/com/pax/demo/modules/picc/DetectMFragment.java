package com.pax.demo.modules.picc;

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

import com.pax.dal.entity.EM1KeyType;
import com.pax.dal.entity.EPiccType;
import com.pax.demo.R;
import com.pax.demo.base.BaseFragment;
import com.pax.demo.util.Convert;
import com.pax.demo.util.Convert.EPaddingPosition;

import java.util.Locale;

public class DetectMFragment extends BaseFragment implements OnClickListener {

    private TextView textView;
    public DetectMThread detectMThread;
    private EPiccType piccType;
    private Button keyTypeBt, startBt;
    private EditText blockNumEt, passwordEt;

    private EM1KeyType m1KeyType = EM1KeyType.TYPE_A;

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
        View view = inflater.inflate(R.layout.fragment_picc_detect_m, container, false);
        textView = (TextView) view.findViewById(R.id.fragment_picc_detect_m_textview);
        textView.setMovementMethod(ScrollingMovementMethod.getInstance());
        piccType = EPiccType.valueOf(getArguments().getString("piccType"));

        keyTypeBt = (Button) view.findViewById(R.id.fragment_picc_detect_m_keytype);
        blockNumEt = (EditText) view.findViewById(R.id.fragment_picc_detect_m_blocknum);
        passwordEt = (EditText) view.findViewById(R.id.fragment_picc_detect_m_password);
        startBt = (Button) view.findViewById(R.id.fragment_picc_detect_m_start_bt);

        keyTypeBt.setOnClickListener(this);
        startBt.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.fragment_picc_detect_m_keytype:
                if (keyTypeBt.getText().equals("A")) {
                    keyTypeBt.setText("B");
                    m1KeyType = EM1KeyType.TYPE_B;
                } else {
                    keyTypeBt.setText("A");
                    m1KeyType = EM1KeyType.TYPE_A;
                }
                break;
            case R.id.fragment_picc_detect_m_start_bt:
                if (detectMThread != null) {
                    detectMThread.interrupt();
                    detectMThread = null;
                }
                    PiccTester.getInstance(piccType).open();
                    detectMThread = new DetectMThread();
                    detectMThread.start();
                break;
            default:
                break;
        }
    }

    @Override
    public void onDestroy() {
        PiccTester.getInstance(piccType).close();
        if (detectMThread != null) {
            detectMThread.interrupt();
            detectMThread = null;
        }
        super.onDestroy();
    }

    class DetectMThread extends Thread {
        @Override
        public void run() {
            super.run();
            // textView.post(new Runnable() {
            // @Override
            // public void run() {
            // textView.setText("searching M1 card...");
            // }
            // });
            
            //while (!Thread.interrupted()) {
                String blockStr = blockNumEt.getText().toString();
                int blockNum = Integer.parseInt(blockStr.equals("") ? blockNumEt.getHint().toString() : blockStr);
                String password = passwordEt.getText().toString().equals("") ? passwordEt.getHint().toString()
                        : passwordEt.getText().toString();
                 PiccTester.getInstance(piccType).detectM(handler, m1KeyType, blockNum,
                        Convert.getInstance().strToBcd(password.toUpperCase(Locale.ENGLISH), EPaddingPosition.PADDING_RIGHT));
//                if (i == 1) {
//                    break;
//                }
//                try {
//                    sleep(1000);
//                } catch (InterruptedException e) {
//                    // e.printStackTrace();
//                    break;
//                }

            //}
            
        }
    }

}
