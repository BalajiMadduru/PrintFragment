package com.pax.demo.modules.signpad;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.pax.dal.entity.SignPadResp;
import com.pax.demo.R;
import com.pax.demo.base.BaseFragment;

public class SignPadStrFragment extends BaseFragment implements OnClickListener {

    private Button btnSignStart;
    private Button btnDiaplay;
    private Button btnCancel;
    private ImageView ivSign;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_pad, container, false);
        btnSignStart = (Button) view.findViewById(R.id.btnSignStart);
        btnSignStart.setOnClickListener(this);
        btnDiaplay = (Button) view.findViewById(R.id.btnDisplay);
        btnDiaplay.setOnClickListener(this);
        btnCancel = (Button) view.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(this);
        btnCancel.setEnabled(false);
        ivSign = (ImageView) view.findViewById(R.id.ivSign);

        return view;
    }

    public Bitmap bytes2Bimap(byte[] b) {
        if (b.length != 0) {
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        } else {
            return null;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSignStart:
                btnCancel.setEnabled(true);
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        final SignPadResp resp = SignPadTester.getInstance().signStart("3031323334353637");
                        getActivity().runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                if (resp != null && resp.getSignBmp() != null) {
                                    ivSign.setImageBitmap(bytes2Bimap(resp.getSignBmp()));
                                }
                                btnCancel.setEnabled(false);
                            }
                        });
                    }
                }).start();
                break;
            case R.id.btnDisplay:
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        SignPadTester.getInstance().displayWord((short) 0, (short) 0, (byte) 0x06, (byte) 0x00,
                                (byte) 0x01, (short) 100);
                    }
                }).start();
                break;

            case R.id.btnCancel:
                SignPadTester.getInstance().cancle();
                break;

            default:
                break;
        }

    }
}
