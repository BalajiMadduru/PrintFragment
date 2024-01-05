package com.pax.demo.modules.system;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.pax.dal.entity.ETouchMode;
import com.pax.demo.R;
import com.pax.demo.base.BaseFragment;

public class SwitchTouchModeFragment extends BaseFragment {

    private Button touchModeBt;
    private ETouchMode touchMode = ETouchMode.PEN;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_button, container, false);
        touchModeBt = (Button) view.findViewById(R.id.fragment_button);
        touchModeBt.setText(touchMode.name());
        touchModeBt.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (touchMode == ETouchMode.PEN) {
                    touchMode = ETouchMode.FINGER;
                } else {
                    touchMode = ETouchMode.PEN;
                }
                SysTester.getInstance().switchTouchMode(touchMode);
                touchModeBt.setText(touchMode.name());
            }
        });
        return view;
    }
}
