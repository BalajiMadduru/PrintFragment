package com.pax.demo.modules.ped;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pax.demo.R;
import com.pax.demo.base.BasePedFragment;

public class GenRsaKeyFragment extends BasePedFragment {

    private TextView resultView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_text, container, false);
        resultView = (TextView) view.findViewById(R.id.fragment_textview);
        boolean flag = PedTester.getInstance(pedType).genRsaKey();
        if (flag) {
            resultView.setText("GenRSAKey successful.");
        } else {
            resultView.setText("GenRSAKey failed.");
        }
        return view;
    }
}
