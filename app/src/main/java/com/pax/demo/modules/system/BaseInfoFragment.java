package com.pax.demo.modules.system;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pax.dal.entity.BaseInfo;
import com.pax.demo.R;
import com.pax.demo.base.BaseFragment;

public class BaseInfoFragment extends BaseFragment {

    private TextView textView;
    private SysTester sysTester;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_text, container, false);
        textView = (TextView)view.findViewById(R.id.fragment_textview);
        sysTester = SysTester.getInstance();
        String baseInfo = "BaseInfo:\n";
        if(sysTester.isOnBase()){
            BaseInfo info = sysTester.getBaseInfo();
            if(info != null){
                baseInfo+=("\nMAC: "+info.getMac());
                baseInfo+=("\nPN: "+info.getPn());
                baseInfo+=("\nSN: "+info.getSn());
            }
        }else{
            baseInfo = "not on base!";
        }
        textView.setText(baseInfo);
        return view;
    }
}
