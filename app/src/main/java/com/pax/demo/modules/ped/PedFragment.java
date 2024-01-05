package com.pax.demo.modules.ped;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.pax.dal.entity.EPedType;
import com.pax.demo.R;
import com.pax.demo.base.BasePedFragment;
import com.pax.demo.util.BackListAdapter;

import java.util.Arrays;

public class PedFragment extends BasePedFragment implements OnItemClickListener {
    private GridView consoleGridView;
    private BackListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        consoleGridView = (GridView) view.findViewById(R.id.fragment_gridview);
        if (pedType == EPedType.INTERNAL) {
            adapter = new BackListAdapter(Arrays.asList(getResources().getStringArray(R.array.Ped_IN)), getActivity());
        } else {
            adapter = new BackListAdapter(Arrays.asList(getResources().getStringArray(R.array.Ped_EX)), getActivity());
        }

        consoleGridView.setAdapter(adapter);
        consoleGridView.setOnItemClickListener(this);
        return view;
    }

    @SuppressLint("NewApi")
    @Override
    public void onDestroyView() {
        if (getChildFragmentManager().getBackStackEntryCount() > 0) {
            getChildFragmentManager().popBackStackImmediate();
        }
        super.onDestroyView();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        adapter.setPos(position);
        adapter.notifyDataSetChanged();
        if (pedType == null) {
            return;
        }
        switch (position) {
            case 0:
                fragmentSelect(new WriteKeyFragment(), pedType);
                break;
            case 1:
                fragmentSelect(new WriteTIKFragment(), pedType);
                break;
            case 2:
                fragmentSelect(new GetPinBlockFragment(), pedType);
                break;
            case 3:
                fragmentSelect(new GetMacFragment(), pedType);
                break;
            case 4:
                fragmentSelect(new CalcDesFragment(), pedType);
                break;
            case 5:
                fragmentSelect(new GetDUKPTPinFragment(), pedType);
                break;
            case 6:
                fragmentSelect(new GetDUKPTMacFragment(), pedType);
                break;
            case 7:
                if (pedType == EPedType.EXTERNAL_TYPEA) {
                    fragmentSelect(new GetVersionFragment(), pedType);
                    break;
                }
                fragmentSelect(new GetKCVFragment(), pedType);
                break;
            case 8:
                if (pedType == EPedType.EXTERNAL_TYPEA) {
                    fragmentSelect(new SetFunctionFragment(), pedType);
                    break;
                }
                fragmentSelect(new WriteKeyVarFragment(), pedType);
                break;
            case 9:
                if (pedType == EPedType.EXTERNAL_TYPEA) {
                    fragmentSelect(new SetExModeFragment(), pedType);
                    break;
                }
                fragmentSelect(new GetVersionFragment(), pedType);
                break;
            case 10:
                if (pedType == EPedType.EXTERNAL_TYPEA) {
                    fragmentSelect(new ClearScreenFragment(), pedType);
                    break;
                }
                fragmentSelect(new EraseFragment(), pedType);
                break;
            case 11:
                if (pedType == EPedType.EXTERNAL_TYPEA) {
                    fragmentSelect(new InputStrFragment(), pedType);
                    break;
                }
                fragmentSelect(new SetInTimeFragment(), pedType);
                break;
            case 12:
                if (pedType == EPedType.EXTERNAL_TYPEA) {
                    fragmentSelect(new ShowStrFragment(), pedType);
                    break;
                }
                fragmentSelect(new SetFunctionFragment(), pedType);
                break;
            case 13:
                if (pedType == EPedType.EXTERNAL_TYPEA) {
                    fragmentSelect(new GetSNFragment(), pedType);
                    break;
                }
                fragmentSelect(new GenRsaKeyFragment(), pedType);
                break;
            case 14:
                if (pedType == EPedType.EXTERNAL_TYPEA) {
                    fragmentSelect(new GenSM2KeyPairFragment(), pedType);
                    break;
                }
                fragmentSelect(new WriteRSAkeyFragment(), pedType);
                break;
            case 15:
                if (pedType == EPedType.EXTERNAL_TYPEA) {
                    fragmentSelect(new WriteSM2KeyFragment(), pedType);
                    break;
                }
                fragmentSelect(new ReadRSAKeyFragment(), pedType);
                break;
            case 16:
                if (pedType == EPedType.EXTERNAL_TYPEA) {
                    fragmentSelect(new SM2SignAndVerifyFragment(), pedType);
                    break;
                }
                fragmentSelect(new RSARecoverFragment(), pedType);
                break;
            case 17:
                if (pedType == EPedType.EXTERNAL_TYPEA) {
                    fragmentSelect(new SM2RecoverFragment(), pedType);
                    break;
                }
                fragmentSelect(new CalcDUKPTDesFragment(), pedType);
                break;
            case 18:
                if (pedType == EPedType.EXTERNAL_TYPEA) {
                    fragmentSelect(new SM3Fragment(), pedType);
                    break;
                }
                fragmentSelect(new GetDUKPTKsnFragment(), pedType);
                break;
            case 19:
                if (pedType == EPedType.EXTERNAL_TYPEA) {
                    fragmentSelect(new SM4Fragment(), pedType);
                    break;
                }
                fragmentSelect(new InDuKPTKsnFragment(), pedType);
                break;
            case 20:
                if (pedType == EPedType.EXTERNAL_TYPEA) {
                    fragmentSelect(new GetMacSMFragment(), pedType);
                    break;
                }
                fragmentSelect(new GenSM2KeyPairFragment(), pedType);
                break;
            case 21:
                if (pedType == EPedType.EXTERNAL_TYPEA) {
                    fragmentSelect(new GetPinBlockSM4Fragment(), pedType);
                    break;
                }
                fragmentSelect(new WriteSM2KeyFragment(), pedType);
                break;
            case 22:
                fragmentSelect(new SM2SignAndVerifyFragment(), pedType);
                break;
            case 23:
                fragmentSelect(new SM2RecoverFragment(), pedType);
                break;
            case 24:
                fragmentSelect(new SM3Fragment(), pedType);
                break;
            case 25:
                fragmentSelect(new SM4Fragment(), pedType);
                break;
            case 26:
                fragmentSelect(new GetMacSMFragment(), pedType);
                break;
            case 27:
                fragmentSelect(new GetPinBlockSM4Fragment(), pedType);
                break;
            case 28:
                fragmentSelect(new AesFragment(), pedType);
                break;
            case 29:
                startActivity(new Intent(getActivity(), JsonActivity.class));
                break;
            case 30:
                startActivity(new Intent(getActivity(), ViewActivity.class));
                break;
            default:
                break;
        }
    }
}
