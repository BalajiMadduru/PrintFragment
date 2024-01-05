package com.pax.demo.modules.system;

import java.util.Arrays;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.pax.demo.R;
import com.pax.demo.base.BaseFragment;
import com.pax.demo.util.BackListAdapter;

public class SystemFragment extends BaseFragment implements OnItemClickListener {

    private LinearLayout screenLayout;
    private GridView consoleGridView;
    private BackListAdapter adapter = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);

        screenLayout = (LinearLayout) view.findViewById(R.id.fragment_screen);
        consoleGridView = (GridView) view.findViewById(R.id.fragment_gridview);

        adapter = new BackListAdapter(Arrays.asList(getResources().getStringArray(R.array.Sys)), getActivity());
        consoleGridView.setAdapter(adapter);
        consoleGridView.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        adapter.setPos(position);
        adapter.notifyDataSetChanged();
        switch (position) {
            case 0:
                fragmentSelect(new BeepFragment());
                break;
            case 1:
                fragmentSelect(new TermInfoFragment());
                break;
            case 2:
                fragmentSelect(new GetRadomFragment());
                break;
            case 3:
                fragmentSelect(new DevInterfaceVerFragment());
                break;
            case 4:
                fragmentSelect(new NavigationBarFragment());
                break;
            case 5:
                fragmentSelect(new StatusBarFragment());
                break;
            case 6:
                fragmentSelect(new PowerFragment());
                break;
            case 7:
                fragmentSelect(new SettingsNeedPWFragment());
                break;
            case 8:
                fragmentSelect(new SwitchTouchModeFragment());
                break;
            case 9:
                fragmentSelect(new GetAppLogsFragment());
                break;
            case 10:
                fragmentSelect(new ReadTUSNFragment());
                break;
            case 11:
                fragmentSelect(new BaseInfoFragment());
                break;
            case 12:
                fragmentSelect(new SystemLanguageFragment());
                break;
            case 13:
                fragmentSelect(new GetPNFragment());
                break;
            default:
                break;
        }
    }
}
