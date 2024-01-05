package com.pax.demo.modules.comm;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.pax.dal.entity.EChannelType;
import com.pax.demo.R;
import com.pax.demo.base.BaseFragment;

public class ChannelFragment extends BaseFragment implements OnClickListener, OnItemSelectedListener {

    private Spinner spinner;
    private Button enableBt, disableBt;
    private TextView resultText;
    private EChannelType channelType = EChannelType.WIFI;
    private String resultString;
    // static CheckNetStatusThread checkNetStatusThread;

    private Handler handler = new Handler() {
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

        View view = inflater.inflate(R.layout.fragment_comm_selectnetwork, container, false);
        spinner = (Spinner) view.findViewById(R.id.fragment_comm_selectroute_spinner);
        enableBt = (Button) view.findViewById(R.id.fragment_comm_selectroute_enable_bt);
        disableBt = (Button) view.findViewById(R.id.fragment_comm_selectroute_disable_bt);
        resultText = (TextView) view.findViewById(R.id.fragment_comm_selectroute_result_text);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.selectNetWork));
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(this);
        enableBt.setOnClickListener(this);
        disableBt.setOnClickListener(this);

        // if (checkNetStatusThread == null) {
        // checkNetStatusThread = new CheckNetStatusThread();
        // checkNetStatusThread.start();
        // }
        return view;
    }

    @Override
    public void onDestroy() {
        // if (checkNetStatusThread != null) {
        // checkNetStatusThread.interrupt();
        // checkNetStatusThread = null;
        // }
        super.onDestroy();
    }
    // 影响性能先取消
    // class CheckNetStatusThread extends Thread {
    //
    // @Override
    // public void run() {
    // super.run();
    // while (!Thread.interrupted()) {
    // resultString = ChannelTester.getInstance().getNetWorkStatus(channelType);
    // Message message = Message.obtain();
    // message.what = 0;
    // message.obj = resultString;
    // handler.sendMessage(message);
    // SystemClock.sleep(1000);
    // }
    // }
    // }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                // wifi
                channelType = EChannelType.WIFI;
                break;
            case 1:
                // mobile
                channelType = EChannelType.MOBILE;
                break;
            case 2:
                // ethernet
                channelType = EChannelType.LAN;
                break;

            default:
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // channelType = EChannelType.WIFI;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_comm_selectroute_enable_bt:
                new Thread(new Runnable() {
                    public void run() {
                        int res = ChannelTester.getInstance().enableChannelExclusive(channelType, 20);
                        String enableStr = "";
                        if (res == 0) {
                            enableStr = channelType.toString() + "enable success";
                        } else if (res == -1) {
                            enableStr = "type error";
                        } else if (res == -2) {
                            enableStr = "enable channel error ";
                        } else if (res == -3) {
                            enableStr = "wait for channel ready ";
                        }
                        resultString = ChannelTester.getInstance().getNetWorkStatus(channelType);
                        handler.obtainMessage(0, resultString).sendToTarget();
                        Looper.prepare();
                        Toast.makeText(getActivity(), enableStr, Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }
                }).start();

                // do something
                break;
            case R.id.fragment_comm_selectroute_disable_bt:
                new Thread(new Runnable() {
                    public void run() {
                        ChannelTester.getInstance().disableNetWork(channelType);
                        SystemClock.sleep(500);
                        resultString = ChannelTester.getInstance().getNetWorkStatus(channelType);
                        handler.obtainMessage(0, resultString).sendToTarget();
                    }
                }).start();

                break;

            default:
                break;
        }

    }
}
