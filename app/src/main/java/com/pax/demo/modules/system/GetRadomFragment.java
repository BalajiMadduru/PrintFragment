package com.pax.demo.modules.system;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.pax.demo.R;
import com.pax.demo.base.BaseFragment;

public class GetRadomFragment extends BaseFragment {

    private TextView textView;
    private EditText editText;
    private Button button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sys_getradom, container, false);
        textView = (TextView) view.findViewById(R.id.fragment_getradom_text);
        editText = (EditText) view.findViewById(R.id.fragment_getradom_edit);
        button = (Button) view.findViewById(R.id.fragment_getradom_bt);

        textView.setMovementMethod(ScrollingMovementMethod.getInstance());// 使textview可以滚动
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String editString = editText.getText().toString();
                if (editString.equals("")) {
                    editText.setText("10");
                }
                textView.setText(getString(R.string.random_number)
                        + SysTester.getInstance().getRadom(Integer.parseInt(editText.getText().toString())));
            }
        });

        return view;
    }
}
