package com.pax.demo.modules.comm;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.pax.dal.entity.EChannelType;
import com.pax.dal.entity.ERoute;
import com.pax.demo.R;
import com.pax.demo.base.BaseFragment;

public class SetRouteFragment extends BaseFragment implements OnClickListener {

    private String ip = "61.135.169.125";
    
    private Button enableMultiPathBt,enableChannelBt,disableMultiPathBt,disableChannelBt;
    private RadioGroup radioGroup;
    private TextView resultTv;

    private boolean resb = false;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comm_setroute, container, false);

        enableMultiPathBt = (Button) view.findViewById(R.id.fragment_comm_setroute_enableMultiPath_Bt);
        enableChannelBt = (Button)view.findViewById(R.id.fragment_comm_setroute_enableChannel_Bt);
        disableMultiPathBt = (Button) view.findViewById(R.id.fragment_comm_setroute_disableMultiPath_Bt);
        disableChannelBt = (Button)view.findViewById(R.id.fragment_comm_setroute_disableChannel_Bt);
        
        radioGroup = (RadioGroup) view.findViewById(R.id.fragment_comm_setroute_radiogroup);
        resultTv = (TextView) view.findViewById(R.id.fragment_comm_setroute_test_result);

        enableMultiPathBt.setOnClickListener(this);
        enableChannelBt.setOnClickListener(this);
        disableMultiPathBt.setOnClickListener(this);
        disableChannelBt.setOnClickListener(this);
        radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.fragment_comm_setroute_mobile_rb:
                        resb = CommTester.getInstance().setRoute(ip, ERoute.MOBILE);
                        break;
                    case R.id.fragment_comm_setroute_wifi_rb:
                        resb = CommTester.getInstance().setRoute(ip, ERoute.WIFI);
                        break;
                    case R.id.fragment_comm_setroute_ethernet_rb:
                        resb = CommTester.getInstance().setRoute(ip, ERoute.ETHERNET);
                        break;

                    default:
                        break;
                }

                if (resb) {
                    resultTv.setText("setRoute result:successful");
                } else {
                    resultTv.setText("setRoute result:failed");
                }
            }
        });

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_comm_setroute_enableMultiPath_Bt:
                resb = CommTester.getInstance().enableMultiPath();
                if (resb) {
                    resultTv.setText("enableMultiPath result:successful");
                } else {
                    resultTv.setText("enableMultiPath result:failed");
                }
                break;

            case R.id.fragment_comm_setroute_enableChannel_Bt:
                ChannelTester.getInstance().enableNetWork(EChannelType.WIFI);
                ChannelTester.getInstance().enableNetWork(EChannelType.MOBILE);
                ChannelTester.getInstance().enableNetWork(EChannelType.LAN);
                
                break;
            case R.id.fragment_comm_setroute_disableMultiPath_Bt:
                resb = CommTester.getInstance().disableMultiPath();
                if (resb) {
                    resultTv.setText("disableMultiPath result:successful");
                } else {
                    resultTv.setText("disableMultiPath result:failed");
                }
                break;

            case R.id.fragment_comm_setroute_disableChannel_Bt:
                ChannelTester.getInstance().disableNetWork(EChannelType.WIFI);
                ChannelTester.getInstance().disableNetWork(EChannelType.MOBILE);
                ChannelTester.getInstance().disableNetWork(EChannelType.LAN);
                
                break;
            default:
                break;
        }
    }
}
