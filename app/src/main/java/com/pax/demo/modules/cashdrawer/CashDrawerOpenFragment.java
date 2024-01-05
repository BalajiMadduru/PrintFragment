package com.pax.demo.modules.cashdrawer;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pax.demo.R;
import com.pax.demo.base.BaseFragment;

public class CashDrawerOpenFragment extends BaseFragment {
    private TextView textView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_text, container, false);
        textView = (TextView) view.findViewById(R.id.fragment_textview);
        textView.setTextSize(14);
        textView.setMovementMethod(ScrollingMovementMethod.getInstance());

        int ret = CashDrawerTest.getInstance().open();
        if (ret == 0) {
            textView.setText("open success");
        } else {
            textView.setText("open failed");
        }
        return view;
    }
}
