package com.pax.demo.modules.ped;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pax.dal.entity.EPedType;
import com.pax.demo.R;
import com.pax.demo.base.BasePedFragment;

public class ReadRSAKeyFragment extends BasePedFragment {

    private TextView textView;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_text, container, false);
        textView = (TextView) view.findViewById(R.id.fragment_textview);
        textView.setMovementMethod(ScrollingMovementMethod.getInstance());
        textView.setText(PedTester.getInstance(EPedType.INTERNAL).readRSAKey());
        return view;
    }
}
