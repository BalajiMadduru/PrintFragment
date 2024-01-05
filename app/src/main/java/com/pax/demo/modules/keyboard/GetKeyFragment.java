package com.pax.demo.modules.keyboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.pax.dal.entity.ETermInfoKey;
import com.pax.demo.R;
import com.pax.demo.base.BaseFragment;
import com.pax.demo.base.DemoApp;
import com.pax.demo.util.BaseTester;

public class GetKeyFragment extends BaseFragment implements OnClickListener {
    private Button getKeyBt, flushBt, muteBt;
    private TextView getKeyTv;
    private boolean isMute = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_keyboard_getkeyandset, container, false);

        getKeyBt = (Button) view.findViewById(R.id.fragment_keyboard_getkey_bt);
        flushBt = (Button) view.findViewById(R.id.fragment_keyboard_flush);
        muteBt = (Button) view.findViewById(R.id.fragment_keyboard_mute);
        getKeyTv = (TextView) view.findViewById(R.id.fragment_keyboard_getkey_tv);
            if (DemoApp.getDal().getSys().getTermInfo().get(ETermInfoKey.MODEL).equals("A920")) {
                new BaseTester().logTrue("getTermInfo");
                getKeyBt.setVisibility(View.GONE);
                flushBt.setVisibility(View.GONE);
                muteBt.setVisibility(View.GONE);
                getKeyTv.setText("Not support");
            }
       

        getKeyBt.setOnClickListener(this);
        flushBt.setOnClickListener(this);
        muteBt.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_keyboard_getkey_bt:
                if (KeyBoardTester.getInstance().isHit()) {
                    getKeyTv.setText(KeyBoardTester.getInstance().getKey().name());
                } else {
                    getKeyTv.setText(getString(R.string.keyboard_hit_nokey));
                }
                break;
            case R.id.fragment_keyboard_flush:
                KeyBoardTester.getInstance().clear();
                break;
            case R.id.fragment_keyboard_mute:
                if (isMute) {
                    muteBt.setText(getString(R.string.keyboard_mute_off));
                    isMute = false;
                } else {
                    muteBt.setText(getString(R.string.keyboard_mute_on));
                    isMute = true;
                }
                KeyBoardTester.getInstance().setMute(isMute);
                break;
            default:
                break;
        }
    }

}
