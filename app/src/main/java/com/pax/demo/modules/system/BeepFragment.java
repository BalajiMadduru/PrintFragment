package com.pax.demo.modules.system;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.pax.dal.entity.EBeepMode;
import com.pax.demo.R;
import com.pax.demo.base.BaseFragment;

public class BeepFragment extends BaseFragment {

    private Button beepBt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_button, container, false);

        beepBt = (Button) view.findViewById(R.id.fragment_button);
        beepBt.setText("Beep");
        beepBt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                SysTester.getInstance().beep(EBeepMode.FREQUENCE_LEVEL_6, 100);
            }
        });
        return view;
    }
}
