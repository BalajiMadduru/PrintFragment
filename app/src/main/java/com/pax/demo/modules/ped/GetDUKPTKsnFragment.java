package com.pax.demo.modules.ped;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pax.demo.R;
import com.pax.demo.base.BasePedFragment;
import com.pax.demo.util.Convert;

public class GetDUKPTKsnFragment extends BasePedFragment {

    private TextView writeview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_text, container, false);
        writeview = (TextView) view.findViewById(R.id.fragment_textview);
        byte[] verString = PedTester.getInstance(pedType).getDUKPTKsn();
        if (verString != null) {
            writeview.setText(getString(R.string.ped_ksn_get) + Convert.getInstance().bcdToStr(verString));
        } else {
            writeview.setText(getString(R.string.ped_ksn_null));
        }
        return view;
    }
}
