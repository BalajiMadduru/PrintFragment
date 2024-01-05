package com.pax.demo.modules.ped;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pax.demo.R;
import com.pax.demo.base.BasePedFragment;

public class GetVersionFragment extends BasePedFragment {

    private TextView writeview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_text, container, false);
        writeview = (TextView) view.findViewById(R.id.fragment_textview);
        String verString = PedTester.getInstance(pedType).getVersion();
        if (verString != null) {
            writeview.setText(getString(R.string.ped_version) + verString);
        } else {
            writeview.setText(getString(R.string.ped_version_null));
        }
        return view;
    }
}
