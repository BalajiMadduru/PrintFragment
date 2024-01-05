package com.pax.demo.modules.comm;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pax.dal.entity.EUartPort;
import com.pax.demo.R;
import com.pax.demo.base.BaseFragment;
import com.pax.demo.base.DemoApp;
import com.pax.demo.util.BaseTester;

import java.util.List;

public class UartPortListFragment extends BaseFragment {

    private TextView textView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_text, container, false);
        textView = (TextView) view.findViewById(R.id.fragment_textview);

        List<EUartPort> uartPortList = DemoApp.getDal().getCommManager().getUartPortList();
        new BaseTester().logTrue("getUartPortList");
        String UPStr = "available port:\n";
        for (EUartPort port : uartPortList) {
            UPStr += (port.name() + "\n");
        }
        textView.setText(UPStr);

        return view;
    }

}
