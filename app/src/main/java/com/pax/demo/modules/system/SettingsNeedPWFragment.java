package com.pax.demo.modules.system;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.pax.demo.R;
import com.pax.demo.base.BaseFragment;

public class SettingsNeedPWFragment extends BaseFragment {

    private Button needPWBt;
    private boolean needPWB = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_button, container, false);
        needPWBt = (Button) view.findViewById(R.id.fragment_button);

        needPWB = SysTester.getInstance().isPowerKeyEnabled();
        needPWBt.setText(needPWB == true ? "not need" : "need");
        needPWBt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                SysTester.getInstance().setSettingsNeedPassword(!needPWB);
                needPWB = !needPWB;
                needPWBt.setText(needPWB == true ? "not need" : "need");
            }
        });

        return view;
    }
}
