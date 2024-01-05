package com.pax.demo.modules.ped;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pax.demo.R;
import com.pax.demo.base.BasePedFragment;

public class WriteTIKFragment extends BasePedFragment {

    private TextView writeview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_text, container, false);
        writeview = (TextView) view.findViewById(R.id.fragment_textview);
        boolean verString = PedTester.getInstance(pedType).writeTIK();
        if (verString) {
            writeview.setText(getString(R.string.ped_tik_success));
        } else {
            writeview.setText(getString(R.string.ped_tik_fail));
        }
        return view;
    }
}
