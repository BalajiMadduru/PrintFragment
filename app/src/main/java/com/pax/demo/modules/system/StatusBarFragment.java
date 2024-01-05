package com.pax.demo.modules.system;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.pax.demo.R;
import com.pax.demo.base.BaseFragment;

public class StatusBarFragment extends BaseFragment implements OnClickListener {

    private Button enableBt, visibleBt, reSetBt;
    private boolean enableB = true, visibleB = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sys_statusbar, container, false);
        enableBt = (Button) view.findViewById(R.id.fragment_sys_statusbar_enable);
        visibleBt = (Button) view.findViewById(R.id.fragment_sys_statusbar_visible);
        reSetBt = (Button) view.findViewById(R.id.fragment_sys_statusbar_reset);

        enableBt.setOnClickListener(this);
        visibleBt.setOnClickListener(this);
        reSetBt.setOnClickListener(this);

        // get the statusBar's status
        enableB = SysTester.getInstance().isStatusBarEnabled();
        visibleB = SysTester.getInstance().isStatusBarVisible();
        enableBt.setText(enableB == true ? "disable" : "enable");
        visibleBt.setText(visibleB == true ? "unvisible" : "visible");

        return view;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.fragment_sys_statusbar_enable:
                SysTester.getInstance().enableStatusBar(!enableB);
                enableB = !enableB;
                enableBt.setText(enableB == true ? "disable" : "enable");
                break;
            case R.id.fragment_sys_statusbar_visible:
                SysTester.getInstance().showStatusBar(!visibleB);
                visibleB = !visibleB;
                visibleBt.setText(visibleB == true ? "unvisible" : "visible");
                break;
            case R.id.fragment_sys_statusbar_reset:
                SysTester.getInstance().resetStatusBar();
                enableB = true;
                visibleBt.setText(visibleB == true ? "unvisible" : "visible");
                break;

            default:
                break;
        }
    }
}
