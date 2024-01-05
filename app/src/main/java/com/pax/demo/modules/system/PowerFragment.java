package com.pax.demo.modules.system;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.pax.demo.R;
import com.pax.demo.base.BaseFragment;

public class PowerFragment extends BaseFragment implements OnClickListener {

    private Button enablePowerKeyBt, shutdownBt, rebootBt;
    private boolean enableB = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sys_power, container, false);
        enablePowerKeyBt = (Button) view.findViewById(R.id.fragment_sys_power_enable);
        shutdownBt = (Button) view.findViewById(R.id.fragment_sys_power_shutdown);
        rebootBt = (Button) view.findViewById(R.id.fragment_sys_power_reboot);

        enablePowerKeyBt.setOnClickListener(this);
        shutdownBt.setOnClickListener(this);
        rebootBt.setOnClickListener(this);

        enableB = SysTester.getInstance().isPowerKeyEnabled();
        enablePowerKeyBt.setText(enableB == true ? "disablePowerKey" : "enablePowerKey");
        return view;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.fragment_sys_power_enable:
                SysTester.getInstance().enablePowerKey(!enableB);
                enableB = !enableB;
                enablePowerKeyBt.setText(enableB == true ? "disablePowerKey" : "enablePowerKey");
                break;

            case R.id.fragment_sys_power_shutdown:
                SysTester.getInstance().shutdown();
                break;
            case R.id.fragment_sys_power_reboot:
                SysTester.getInstance().reboot();
                break;
            default:
                break;
        }
    }

}
