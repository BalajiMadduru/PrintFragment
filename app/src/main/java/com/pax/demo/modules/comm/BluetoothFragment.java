package com.pax.demo.modules.comm;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.pax.dal.entity.EChannelType;
import com.pax.demo.R;
import com.pax.demo.base.BaseFragment;

public class BluetoothFragment extends BaseFragment implements OnClickListener {

    private Button enableBt, disableBt;
    private TextView resultText;
    private String resultString;
    static CheckNetStatusThread checkNetStatusThread;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    resultText.setText(msg.obj.toString());
                    break;

                default:
                    break;
            }
        };
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comm_bluetooth, container, false);
        enableBt = (Button) view.findViewById(R.id.fragment_comm_bluetooth_enable_bt);
        disableBt = (Button) view.findViewById(R.id.fragment_comm_bluetooth_disable_bt);
        resultText = (TextView) view.findViewById(R.id.fragment_comm_bluetooth_result_text);

        enableBt.setOnClickListener(this);
        disableBt.setOnClickListener(this);
        if (checkNetStatusThread == null) {
            checkNetStatusThread = new CheckNetStatusThread();
            checkNetStatusThread.start();
        }
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_comm_bluetooth_enable_bt:
                new Thread(new Runnable() {
                    public void run() {
                        ChannelTester.getInstance().enableNetWork(EChannelType.BT);
                    }
                }).start();

                break;
            case R.id.fragment_comm_bluetooth_disable_bt:
                new Thread(new Runnable() {
                    public void run() {
                        ChannelTester.getInstance().disableNetWork(EChannelType.BT);
                    }
                }).start();

                break;
            default:
                break;
        }
    }

    @Override
    public void onDestroy() {
        if (checkNetStatusThread != null) {
            checkNetStatusThread.interrupt();
            checkNetStatusThread = null;
        }
        super.onDestroy();
    }

    class CheckNetStatusThread extends Thread {

        @Override
        public void run() {
            super.run();
            while (!Thread.interrupted()) {
                resultString = ChannelTester.getInstance().getNetWorkStatus(EChannelType.BT);
                Message message = Message.obtain();
                message.what = 0;
                message.obj = resultString;
                handler.sendMessage(message);
                SystemClock.sleep(500);
            }
        }
    }

}
