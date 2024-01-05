package com.pax.demo.modules.idreader;

import android.os.Bundle;
import android.os.Handler;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pax.dal.entity.IDReadResult;
import com.pax.demo.R;
import com.pax.demo.base.BaseFragment;

public class ReadFragment extends BaseFragment {
    private TextView textView;

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:
                    if (msg.obj instanceof String) {
                        textView.setText(msg.obj.toString());
                    } else if (msg.obj instanceof IDReadResult) {
                        IDReadResult result = (IDReadResult) msg.obj;
                        String res = "Address:" + result.getAddress() + "\nAuthority:" + result.getAuthority()
                                + "\nBirth:" + result.getBirth() + "\nCardNo:" + result.getCardNo() + "\nDn:"
                                + result.getDn() + "\nEthnicity:" + result.getEthnicity() + "\nName:"
                                + result.getName() + "\nPeriod:" + result.getPeriod() + "\nSex:" + result.getSex()
                                + "\nAvatarByteCount:" + result.getAvatar().getByteCount() + "\nOffImageByteCount:"
                                + result.getOffImage().getByteCount() + "\nOnImageByteCount:"
                                + result.getOnImage().getByteCount();

                        textView.setText(res);
                    }

                    break;

                default:
                    break;
            }
        };
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_text, container, false);
        textView = (TextView) view.findViewById(R.id.fragment_textview);
        textView.setTextSize(14);
        textView.setMovementMethod(ScrollingMovementMethod.getInstance());

        IDReaderTest.getInstance().startRead(handler);
        return view;
    }
}
