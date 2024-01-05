package com.pax.demo.modules.ped;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pax.demo.R;
import com.pax.demo.base.BasePedFragment;

public class InDuKPTKsnFragment extends BasePedFragment {

    private TextView resultText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_text, container, false);
        resultText = (TextView) view.findViewById(R.id.fragment_textview);
        boolean verString = PedTester.getInstance(pedType).incDUKPTKsn();
        if (verString) {
            resultText.setText(getString(R.string.ped_indupkt_success));
        } else {
            resultText.setText(getString(R.string.ped_indupkt_error));
        }
        return view;
    }
}
