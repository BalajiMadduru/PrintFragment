package com.pax.demo.modules.wifiprobe;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.pax.demo.R;
import com.pax.demo.base.BaseFragment;

import java.lang.ref.WeakReference;
import java.util.List;

public class WifiProbeFragment extends BaseFragment implements OnClickListener {

    private Button startBt, getStatusBt, stopBt, getResultBt;
    private TextView statusTv, itemTv, resultTv;
    private WifiProbeTester probeTester;
    private WifiProbeHandler handler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wifiprobe_layout, container, false);
        initView(view);
        probeTester = WifiProbeTester.getInstance();
        handler = new WifiProbeHandler(this);
        return view;
    }

    private void initView(View view) {
        startBt = (Button) view.findViewById(R.id.fragment_wifiprobe_start_bt);
        getStatusBt = (Button) view.findViewById(R.id.fragment_wifiprobe_getstatus_bt);
        stopBt = (Button) view.findViewById(R.id.fragment_wifiprobe_stop_bt);
        getResultBt = (Button) view.findViewById(R.id.fragment_wifiprobe_getresult_bt);
        statusTv = (TextView) view.findViewById(R.id.fragment_wifiprobe_status_tv);
        itemTv = (TextView) view.findViewById(R.id.fragment_wifiprobe_item_tv);
        resultTv = (TextView) view.findViewById(R.id.fragment_wifiprobe_result_tv);

        resultTv.setMovementMethod(ScrollingMovementMethod.getInstance());

        startBt.setOnClickListener(this);
        getStatusBt.setOnClickListener(this);
        stopBt.setOnClickListener(this);
        getResultBt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_wifiprobe_start_bt:
                probeTester.start(handler);
                break;
            case R.id.fragment_wifiprobe_getstatus_bt:
                int status = probeTester.getStatus();
                String statusStr = "";
                // 0:probe finished 1:probing -1:not start
                if (status == 0) {
                    statusStr = "probe finished";
                } else if (status == 1) {
                    statusStr = "probing";
                } else if (status == -1) {
                    statusStr = "not start";
                }
                statusTv.setText(statusStr);
                break;
            case R.id.fragment_wifiprobe_stop_bt:
                probeTester.stop();
                break;
            case R.id.fragment_wifiprobe_getresult_bt:
                List<String> results = probeTester.getResults();
                if (results != null) {
                    String resString = "NUM                     MAC\n\n";
                    for (String item : results) {
                        resString += ((results.indexOf(item) + 1) + "     " + item + "\n");
                    }
                    resultTv.setText(resString);
                } else {
                    resultTv.setText("null");
                }
                break;
            default:
                break;
        }
    }

    private static class WifiProbeHandler extends Handler {
        private WeakReference<WifiProbeFragment> reference;

        public WifiProbeHandler(WifiProbeFragment fragment) {
            reference = new WeakReference<WifiProbeFragment>(fragment);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            WifiProbeFragment fragment = reference.get();
            if (fragment == null) {
                return;
            }
            switch (msg.what) {
                case 0:
                    String item = (String) msg.obj;
                    if (item != null) {
                        fragment.itemTv.setText(item);
                    }
                    break;

                default:
                    break;
            }
        }
    }
}
