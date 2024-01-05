package com.pax.demo.modules.deviceinfo;

import com.pax.dal.IDeviceInfo;
import com.pax.dal.IDeviceInfo.ESupported;
import com.pax.demo.base.DemoApp;
import com.pax.demo.util.BaseTester;

import java.util.Map;

public class DeviceInfoTester extends BaseTester{

    private IDeviceInfo deviceInfo;
    private static DeviceInfoTester tester;
    
    private DeviceInfoTester(){
        deviceInfo = DemoApp.getDal().getDeviceInfo();
    }
    
    public static DeviceInfoTester getInstance(){
        if(tester == null){
            tester = new DeviceInfoTester();
        }
        return tester;
    }
    
    public String getModuleSupported(){
        Map<Integer, ESupported> moduleSupported = deviceInfo.getModuleSupported();
        logTrue("getModuleSupported");
        String supportStr = "ModuleSupported:\n";
        supportStr += ("MODULE_BT:"+moduleSupported.get(IDeviceInfo.MODULE_BT).name()+"\n");
        supportStr += ("MODULE_CASH_BOX:"+moduleSupported.get(IDeviceInfo.MODULE_CASH_BOX).name()+"\n");
        supportStr += ("MODULE_CUSTOMER_DISPLAY:"+moduleSupported.get(IDeviceInfo.MODULE_CUSTOMER_DISPLAY).name()+"\n");
        supportStr += ("MODULE_ETHERNET:"+moduleSupported.get(IDeviceInfo.MODULE_ETHERNET).name()+"\n");
        supportStr += ("MODULE_FINGERPRINT_READER:"+moduleSupported.get(IDeviceInfo.MODULE_FINGERPRINT_READER).name()+"\n");
        supportStr += ("MODULE_G_SENSOR:"+moduleSupported.get(IDeviceInfo.MODULE_G_SENSOR).name()+"\n");
        supportStr += ("MODULE_HDMI:"+moduleSupported.get(IDeviceInfo.MODULE_HDMI).name()+"\n");
        supportStr += ("MODULE_ICC:"+moduleSupported.get(IDeviceInfo.MODULE_ICC).name()+"\n");
        supportStr += ("MODULE_ID_CARD_READER:"+moduleSupported.get(IDeviceInfo.MODULE_ID_CARD_READER).name()+"\n");
        supportStr += ("MODULE_KEYBOARD:"+moduleSupported.get(IDeviceInfo.MODULE_KEYBOARD).name()+"\n");
        supportStr += ("MODULE_MAG:"+moduleSupported.get(IDeviceInfo.MODULE_MAG).name()+"\n");
        supportStr += ("MODULE_PED:"+moduleSupported.get(IDeviceInfo.MODULE_PED).name()+"\n");
        supportStr += ("MODULE_PICC:"+moduleSupported.get(IDeviceInfo.MODULE_PICC).name()+"\n");
        supportStr += ("MODULE_PRINTER:"+moduleSupported.get(IDeviceInfo.MODULE_PRINTER).name()+"\n");
        supportStr += ("MODULE_SM:"+moduleSupported.get(IDeviceInfo.MODULE_SM).name()+"\n");
        supportStr += ("MODULE_MODEM:"+moduleSupported.get(IDeviceInfo.MODULE_MODEM).name()+"\n");
//        supportStr += ("MODULE_SCANNER_HW:"+moduleSupported.get(IDeviceInfo.MODULE_SCANNER_HW).name()+"\n");
        return supportStr;
    }
    
    public String getUsageCount(){
        String usageCount = "usageCount:\n";
        usageCount += ("MODULE_MAG:"+deviceInfo.getUsageCount(IDeviceInfo.MODULE_MAG)+"\n");
        usageCount += ("MODULE_ICC:"+deviceInfo.getUsageCount(IDeviceInfo.MODULE_ICC)+"\n");
        usageCount += ("MODULE_PICC:"+deviceInfo.getUsageCount(IDeviceInfo.MODULE_PICC)+"\n");
        logTrue("getUsageCount");
        return usageCount ;
    }
    
    public String getFailCount(){
        String failCount = "failCount:\n";
        failCount += ("MODULE_MAG:"+deviceInfo.getFailCount(IDeviceInfo.MODULE_MAG)+"\n");
        failCount += ("MODULE_ICC:"+deviceInfo.getFailCount(IDeviceInfo.MODULE_ICC)+"\n");
        failCount += ("MODULE_PICC:"+deviceInfo.getFailCount(IDeviceInfo.MODULE_PICC)+"\n");
        logTrue("getFailCount");
        return failCount ;
    }
}
