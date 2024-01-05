package com.pax.demo.base;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.pax.demo.R;
import com.pax.demo.modules.cashdrawer.CashDrawerFragment;
import com.pax.demo.modules.comm.CommFragment;
import com.pax.demo.modules.deviceinfo.DeviceInfoFragment;
import com.pax.demo.modules.icc.IccFragment;
import com.pax.demo.modules.idreader.IDReaderFragment;
import com.pax.demo.modules.keyboard.KeyBoardFragment;
import com.pax.demo.modules.mag.MagFragment;
import com.pax.demo.modules.ped.PedSelectFragment;
import com.pax.demo.modules.picc.PiccSelectFragment;
import com.pax.demo.modules.printer.PrinterFragment;
import com.pax.demo.modules.scancodec.CodecFragment;
import com.pax.demo.modules.scanner.ScannerSelectFragment;
import com.pax.demo.modules.signpad.SignPadFragment;
import com.pax.demo.modules.system.SystemFragment;
import com.pax.demo.util.BackListAdapter;
import com.pax.demo.util.FloatView;
import com.pax.demo.modules.wifiprobe.WifiProbeFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DALTestActivity extends AppCompatActivity {

    private ListView listView;

    private List<String> nameList = null;
//    public static IDAL idal = null;
    public FloatView floatView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_dal_test);

//        long start = System.currentTimeMillis();
//        try {
//            idal = NeptuneLiteUser.getInstance().getDal(getApplicationContext());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        if (null == idal) {
//            Toast.makeText(DALTestActivity.this, "error occurred,DAL is null.", Toast.LENGTH_LONG).show();
//        }

        floatView = FloatView.getInstance(DALTestActivity.this);
        floatView.createFloatView(20, 20);

        listView = (ListView) findViewById(R.id.test_listview);
        nameList = new ArrayList<String>();
        String[] catalogueArray = getResources().getStringArray(R.array.catalogue);
        nameList = Arrays.asList(catalogueArray);

        // ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, nameList);
        final BackListAdapter adapter = new BackListAdapter(nameList, this);
        listView.setAdapter(adapter);

        // default select
        // fragmentSelect(new MagFragment());

        listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                adapter.setPos(arg2);
                adapter.notifyDataSetChanged();

                switch (arg2) {
                    case 0:
                        fragmentSelect(new DeviceInfoFragment());
                        break;
                    case 1:
                        fragmentSelect(new MagFragment());
                        break;
                    case 2:
                        fragmentSelect(new IccFragment());
                        break;
                    case 3:
                        fragmentSelect(new PiccSelectFragment());
                        break;
                    case 4:
                        fragmentSelect(new PrinterFragment());
                        break;
                    case 5:
                        fragmentSelect(new PedSelectFragment());
                        break;
                    case 6:
                        fragmentSelect(new SystemFragment());
                        break;
                    case 7:
                        fragmentSelect(new KeyBoardFragment());
                        break;
                    case 8:
                        fragmentSelect(new ScannerSelectFragment());
                        break;
                    case 9:
                        fragmentSelect(new CommFragment());
                        break;
                    case 10:
                        fragmentSelect(new SignPadFragment());
                        break;
                    case 11:
                        fragmentSelect(new IDReaderFragment());
                        break;
                    case 12:
                        fragmentSelect(new CashDrawerFragment());
                        break;
                    case 13:
                        fragmentSelect(new CodecFragment());
                        break;
                    case 14:
                        fragmentSelect(new WifiProbeFragment());
                        break;
                    case 15:
                        fragmentSelect(new AboutFragment());
                        break;
                    default:
                        break;
                }
            }

        });
    }

    @Override
    protected void onResume() {
        setScreen();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        floatView.removeFloatView();
        floatView.release();
        super.onDestroy();
    }

    private void fragmentSelect(Fragment fragment) {
        FragmentManager fManager = getSupportFragmentManager();
        FragmentTransaction transaction = fManager.beginTransaction();
        transaction.replace(R.id.parent_layout, fragment);
        transaction.commit();
    }

    private void setScreen() {
        int width = getWindowManager().getDefaultDisplay().getWidth();
        int height = getWindowManager().getDefaultDisplay().getHeight();
        if (width > height) {
            // do nothing
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }
}
