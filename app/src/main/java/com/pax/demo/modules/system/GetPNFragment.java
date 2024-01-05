package com.pax.demo.modules.system;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pax.demo.R;
import com.pax.demo.base.BaseFragment;

public class GetPNFragment extends BaseFragment {

    private TextView textView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_text, container, false);
        textView = (TextView) view.findViewById(R.id.fragment_textview);
        textView.setTextSize(16);
        textView.setText("PN:"+SysTester.getInstance().getPN());
        return view;
    }
}
