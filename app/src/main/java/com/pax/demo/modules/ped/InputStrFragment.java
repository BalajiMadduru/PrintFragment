package com.pax.demo.modules.ped;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pax.demo.R;
import com.pax.demo.base.BasePedFragment;

public class InputStrFragment extends BasePedFragment {

    private TextView writeview;
    private Button button;
    private EditText editText;
    private TextView textView;
    private EditText editText2;
    private Handler handler = new Handler();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_ped_base_layout, container, false);
        LinearLayout layout = (LinearLayout) view.findViewById(R.id.fragment_ped_base_linerlayout);
        writeview = (TextView) view.findViewById(R.id.fragment_ped_base_result);
        textView = (TextView) view.findViewById(R.id.fragment_ped_base_title);
        textView.setText(getString(R.string.ped_input_allowable));
        button = (Button) view.findViewById(R.id.fragment_ped_base_action);
        editText = (EditText) view.findViewById(R.id.fragment_ped_base_edit_1);
        editText.setText("2");
        editText2 = (EditText) view.findViewById(R.id.fragment_ped_base_edit_2);
        editText2.setText("6");
        Button button2 = new Button(getActivity());
        button.setText(getString(R.string.ped_instead1));
        button2.setText(getString(R.string.ped_instead2));
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Thread thread1 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String num = editText.getText().toString().trim();
                        String num2 = editText2.getText().toString().trim();
                        final String str = PedTester.getInstance(pedType).inputStr_1(Integer.parseInt(num),
                                Integer.parseInt(num2));
                        if (str != null) {
                            handler.post(new Runnable() {
                                public void run() {
                                    writeview.setText(getString(R.string.ped_return_string) + str);
                                }
                            });
                        } else {
                            handler.post(new Runnable() {
                                public void run() {
                                    writeview.setText(getString(R.string.ped_return_null));
                                }
                            });

                        }
                    }
                });
                thread1.start();

            }
        });
        button2.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Thread thread2 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String num = editText.getText().toString().trim();
                        String num2 = editText2.getText().toString().trim();
                        final String str = PedTester.getInstance(pedType).inputStr_2(Integer.parseInt(num),
                                Integer.parseInt(num2));
                        if (str != null) {
                            handler.post(new Runnable() {
                                public void run() {
                                    writeview.setText(getString(R.string.ped_return_string) + str);
                                }
                            });
                        } else {
                            handler.post(new Runnable() {
                                public void run() {
                                    writeview.setText(getString(R.string.ped_return_null));
                                }
                            });

                        }
                    }
                });
                thread2.start();
            }
        });
        layout.addView(button2);
        return view;
    }
}
