package com.pax.demo.modules.ped;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import android.util.Log;

import com.pax.dal.IPed;
import com.pax.dal.entity.DUKPTResult;
import com.pax.dal.entity.EAesCheckMode;
import com.pax.dal.entity.ECheckMode;
import com.pax.dal.entity.ECryptOperate;
import com.pax.dal.entity.ECryptOpt;
import com.pax.dal.entity.EDUKPTDesMode;
import com.pax.dal.entity.EDUKPTMacMode;
import com.pax.dal.entity.EDUKPTPinMode;
import com.pax.dal.entity.EFuncKeyMode;
import com.pax.dal.entity.EPedDesMode;
import com.pax.dal.entity.EPedKeyType;
import com.pax.dal.entity.EPedMacMode;
import com.pax.dal.entity.EPedType;
import com.pax.dal.entity.EPinBlockMode;
import com.pax.dal.entity.EUartPort;
import com.pax.dal.entity.RSAKeyInfo;
import com.pax.dal.entity.RSARecoverInfo;
import com.pax.dal.entity.SM2KeyPair;
import com.pax.dal.exceptions.PedDevException;
import com.pax.demo.base.DemoApp;
import com.pax.demo.util.BaseTester;
import com.pax.demo.util.Convert;

public class PedTester extends BaseTester {
    private static PedTester pedTester;
    private static EPedType pedType;
    public static int PEDMODE = 0; //全局标志位
    private static int pedMode = 0; //本地变量
    private IPed ped;
    private byte[] byte_test = new byte[] { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 };
    private KeyPair kp = null;
    public static byte[] modulus1 = null;
    public static boolean isGenRsaKey = false;

    private PedTester(EPedType type) {
        Log.i("Test", type.name());
        pedType = type;
        pedMode = PEDMODE;
       if(pedMode == 0){
           ped = DemoApp.getDal().getPed(type);
       }else{
           ped = DemoApp.getDal().getPedKeyIsolation(type);
       }
        if(type == EPedType.EXTERNAL_TYPEA && !DemoApp.getDal().getCommManager().getUartPortList().contains(EUartPort.PINPAD)){
            ped.setPort(EUartPort.COM1);
        }
        kp = getKeyPair(512);
    }

    public static PedTester getInstance(EPedType type) {
        if (pedTester == null || pedType != type || pedMode != PEDMODE) {
            pedTester = new PedTester(type);
        }
        return pedTester;
    }

    private KeyPair getKeyPair(int len) {
        KeyPairGenerator kpg = null;
        try {
            kpg = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        SecureRandom random = new SecureRandom();
        kpg.initialize(len, random);
        KeyPair kp = kpg.generateKeyPair();
        return kp;
    }

    // PED writeKey include TMK,TPK,TAK,TDk
    // ===============================================================================================================

    public boolean writeKey(EPedKeyType srcKeyType, byte srcKeyIndex, EPedKeyType destKeyType, byte destkeyIndex,
            byte[] destKeyValue, ECheckMode checkMode, byte[] checkBuf) {
        try {
            ped.writeKey(srcKeyType, srcKeyIndex, destKeyType, destkeyIndex, destKeyValue, checkMode, checkBuf);
            logTrue("writeKey");
            return true;
        } catch (PedDevException e) {
            e.printStackTrace();
            logErr("writeKey", e.toString());
        }
        return false;
    }

    // ===================================================================================================================

    public boolean writeTIK() {
        byte[] tik16Clr = { (byte) 0x6A, (byte) 0xC2, (byte) 0x92, (byte) 0xFA, (byte) 0xA1, (byte) 0x31, (byte) 0x5B,
                (byte) 0x4D, (byte) 0x85, (byte) 0x8A, (byte) 0xB3, (byte) 0xA3, (byte) 0xD7, (byte) 0xD5, (byte) 0x93,
                (byte) 0x3A };
        byte[] ksn = { (byte) 0xff, (byte) 0xff, (byte) 0x98, (byte) 0x76, (byte) 0x54, (byte) 0x32, (byte) 0x10,
                (byte) 0xE0, (byte) 0x00, (byte) 0x00 };
        try {
            ped.writeTIK((byte) 0x01, (byte) 0x00, tik16Clr, ksn, ECheckMode.KCV_NONE, null);
            logTrue("writeTIK");
            return true;
        } catch (PedDevException e) {
            e.printStackTrace();
            logErr("writeTIK", e.toString());
            return false;
        }
    }

    public byte[] getPinBlock(byte[] dataIn) {
        try {
            byte[] result = ped.getPinBlock((byte) 1, "0,4,6", dataIn, EPinBlockMode.ISO9564_0, 60000);
            logTrue("getPinBlock");
            return result;
        } catch (PedDevException e) {
            e.printStackTrace();
            logErr("getPinBlock", e.toString());
            return null;
        }
    }

    public byte[] getMac(byte[] bytes) {
        try {
            byte[] bytes_m = ped.getMac((byte) 2, bytes, EPedMacMode.MODE_00);
            logTrue("getMac");
            return bytes_m;
        } catch (PedDevException e) {
            e.printStackTrace();
            logErr("getMac", e.toString());
            return null;
        }

    }

    public byte[] calcDes(byte[] bytes) {
        try {
            byte[] bytes_d = ped.calcDes((byte) 3, bytes, EPedDesMode.ENCRYPT);
            logTrue("calcDes");
            return bytes_d;
        } catch (PedDevException e) {
            e.printStackTrace();
            logErr("calcDes", e.toString());
        }
        return null;

    }

    public DUKPTResult getDUKPTPin(byte[] dataIn) {
        try {
            DUKPTResult bytes_ped = ped.getDUKPTPin((byte) 1, "4", dataIn, EDUKPTPinMode.ISO9564_0_INC, 20000);
            logTrue("getDUKPTPin");
            return bytes_ped;
        } catch (PedDevException e) {
            e.printStackTrace();
            logErr("getDUKPTPin", e.toString());
            return null;
        }

    }

    public byte[] getDUKPTMac(byte[] bytes) {
        try {
            DUKPTResult result = ped.getDUPKTMac((byte) 0x01, bytes, EDUKPTMacMode.MODE_00);
            if (result != null) {
                logTrue("getDUKPTMac");
                return result.getResult();
            } else {
                logTrue("getDUKPTMac");
                return null;
            }
        } catch (PedDevException e) {
            e.printStackTrace();
            logErr("getDUKPTMac", e.toString());
            return null;
        }
    }

    // ==============================================================================
    // 获取 TPK TAK TDK 的KCV
    public byte[] getKCV_TPK() {
        try {
            byte[] bytes_tpk = ped.getKCV(EPedKeyType.TPK, (byte) 1, (byte) 0, byte_test);
            logTrue("getKCV_TPK");
            return bytes_tpk;
        } catch (PedDevException e) {
            e.printStackTrace();
            logErr("getKCV_TPK", e.toString());
        }
        return null;
    }

    public byte[] getKCV_TAK() {
        try {
            byte[] bytes_tak = ped.getKCV(EPedKeyType.TAK, (byte) 2, (byte) 0, byte_test);
            logTrue("getKCV_TAK");
            return bytes_tak;
        } catch (PedDevException e) {
            e.printStackTrace();
            logErr("getKCV_TAK", e.toString());
        }
        return null;
    }

    public byte[] getKCV_TDK() {
        try {
            byte[] bytes_tdk = ped.getKCV(EPedKeyType.TDK, (byte) 3, (byte) 0, byte_test);
            logTrue("getKCV_TDK");
            return bytes_tdk;
        } catch (PedDevException e) {
            e.printStackTrace();
            logErr("getKCV_TDK", e.toString());
        }
        return null;
    }

    // =======================================================================================================

    public boolean writeKeyVar(byte[] bs) {
        try {
            ped.writeKeyVar(EPedKeyType.TPK, (byte) 1, (byte) 5, bs, ECheckMode.KCV_NONE, new byte[] {});
            logTrue("writeKeyVar");
            return true;
        } catch (PedDevException e) {
            e.printStackTrace();
            logErr("writeKeyVar", e.toString());
            return false;
        }
    }

    public String getVersion() {
        try {
            String str_verString = ped.getVersion();
            logTrue("getVersion");
            return str_verString;
        } catch (PedDevException e) {
            e.printStackTrace();
            logErr("getVersion", e.toString());
        }
        return null;
    }

    public boolean erase() {
        try {
            boolean flag = ped.erase();
            logTrue("erase");
            return flag;
        } catch (PedDevException e) {
            e.printStackTrace();
            logErr("erase", e.toString());
        }
        return false;
    }

    public boolean setIntervalTime(String num1, String num2) {
        try {
            ped.setIntervalTime(Integer.parseInt(num1), Integer.parseInt(num2));
            logTrue("setIntervalTime");
            return true;
        } catch (PedDevException e) {
            e.printStackTrace();
            logErr("setIntervalTime", e.toString());
            return false;
        }

    }

    public boolean setFunctionKey(EFuncKeyMode k) {
        try {
            ped.setFunctionKey(k);// EFunckeyKeyMode.ClEAR_ALL
            logTrue("setFunctionKey");
            return true;
        } catch (PedDevException e) {
            e.printStackTrace();
            logErr("setFunctionKey", e.toString());
            return false;
        }
    }

    public boolean genRsaKey(){
        try {
            ped.genRSAKey((byte)2, (byte)1, (short)512, (byte)0);
            logTrue("genRSAKey");
            isGenRsaKey = true;
            return true;
        } catch (PedDevException e) {
            e.printStackTrace();
            logErr("genRSAKey", e.toString());
            isGenRsaKey = false;
            return false;
        }
    }
    
    public String readRSAKey(){
        String keyInfo = "RSAKeyInfo:\n";
        try {
            RSAKeyInfo pubKey = ped.readRSAKey((byte) 1);
            logTrue("readRSAKey");
//            RSAKeyInfo pvtKey = ped.readRSAKey((byte) 2);//key type error
            if(pubKey != null){
                keyInfo += "\npubKey:";
                keyInfo += ("\nmodulus: "+Convert.getInstance().bcdToStr(pubKey.getModulus()));
                keyInfo += ("\nexponent: "+Convert.getInstance().bcdToStr(pubKey.getExponent()));
                keyInfo += ("\nkeyInfo: "+Convert.getInstance().bcdToStr(pubKey.getKeyInfo()));
            }
//            if(pvtKey != null){
//                keyInfo += "\npvtKey:";
//                keyInfo += ("\nmodulus: "+Convert.getInstance().bcdToStr(pvtKey.getModulus()));
//                keyInfo += ("\nexponent: "+Convert.getInstance().bcdToStr(pvtKey.getExponent()));
//                keyInfo += ("\nkeyInfo: "+Convert.getInstance().bcdToStr(pvtKey.getKeyInfo()));
//            }
        } catch (PedDevException e) {
            e.printStackTrace();
            logErr("readRSAKey", e.toString());
        }
        
        return keyInfo;
    }
    
    // =====================================================================
    // writeRSAkey include public key and private key
    public boolean writeRSAKeyPublic() {
        try {
            RSAKeyInfo keyInfo = new RSAKeyInfo();

            RSAPublicKey pubKey = (RSAPublicKey) kp.getPublic();
            byte[] exponent = pubKey.getPublicExponent().toByteArray();
            byte[] exponent1 = exponent;
            if(exponent[0]==0){
                exponent1 = new byte[exponent.length-1];
                System.arraycopy(exponent, 1, exponent1, 0, exponent1.length);
            }
            keyInfo.setExponent(exponent1);
            keyInfo.setExponentLen(exponent1.length * 8 );
            logTrue("RSA public exponent:" + Convert.getInstance().bcdToStr(exponent));
            byte[] modulus = pubKey.getModulus().toByteArray();
            modulus1 = modulus;
            if(modulus[0]== 0){
                modulus1 = new byte[modulus.length-1];
               System.arraycopy(modulus, 1, modulus1, 0, modulus1.length);
            }
            keyInfo.setModulus(modulus1);
            keyInfo.setModulusLen(modulus1.length * 8);
            logTrue("RSA public modulus:" + Convert.getInstance().bcdToStr(modulus));
            keyInfo.setKeyInfo(pubKey.getEncoded());
            logTrue("KeyInfo:"+Convert.getInstance().bcdToStr(pubKey.getEncoded()));
            ped.writeRSAKey((byte) 1, keyInfo);
            logTrue("writeRSAKey_public");
            return true;
        } catch (PedDevException e) {
            e.printStackTrace();
            logErr("writeRSAKey_public", e.toString());
            return false;
        }
    }

    public RSARecoverInfo RSARecoverPublic(byte[] bytes) {
        try {
            RSARecoverInfo info = ped.RSARecover((byte) 1, ped.RSARecover((byte)2, bytes).getData());
            logTrue("RSARecover_public");
            return info;
        } catch (PedDevException e) {
            e.printStackTrace();
            logErr("RSARecover_public", e.toString());
            return null;
        }

    }

    public boolean writeRSAKeyPrivate() {
        try {
            RSAKeyInfo keyInfo = new RSAKeyInfo();

            RSAPrivateKey privateKey = (RSAPrivateKey) kp.getPrivate();
            byte[] exponent = privateKey.getPrivateExponent().toByteArray();
            byte[] exponent1 = exponent;
            if(exponent[0]==0){
                exponent1 = new byte[exponent.length-1];
                System.arraycopy(exponent, 1, exponent1, 0, exponent1.length);
            }
            keyInfo.setExponent(exponent1);
            keyInfo.setExponentLen(exponent1.length * 8);
            logTrue("RSA private exponent:" + Convert.getInstance().bcdToStr(exponent));
            byte[] modulus = privateKey.getModulus().toByteArray();
            modulus1 = modulus;
            if(modulus[0]== 0){
                modulus1 = new byte[modulus.length-1];
               System.arraycopy(modulus, 1, modulus1, 0, modulus1.length);
            }
            keyInfo.setModulus(modulus1);
            keyInfo.setModulusLen(modulus1.length * 8);
            logTrue("RSA private modulus:" + Convert.getInstance().bcdToStr(modulus1));
            keyInfo.setKeyInfo(privateKey.getEncoded());
            logTrue("KeyInfo:"+Convert.getInstance().bcdToStr(privateKey.getEncoded()));
            ped.writeRSAKey((byte) 2, keyInfo);
            logTrue("writeRSAKey_private");
            return true;
        } catch (PedDevException e) {
            e.printStackTrace();
            logErr("writeRSAKey_private", e.toString());
            return false;
        }
    }

    public RSARecoverInfo RSARecoverPrivate(byte[] bytes) {
        try {
            RSARecoverInfo info = ped.RSARecover((byte) 2, ped.RSARecover((byte)1, bytes).getData());
            logTrue("RSARecover_private");
            return info;
        } catch (PedDevException e) {
            e.printStackTrace();
            logErr("RSARecover_private", e.toString());
            return null;
        }
    }

    // =================================================================================
    public byte[] calcDUKPTDesMac(byte[] bytes) {
        try {
            DUKPTResult result = ped.calcDUKPTDes((byte) 0x01, (byte) 0x00, null, bytes, EDUKPTDesMode.CBC_ENCRYPTION);
            if (result != null) {
                logTrue("calcDUKPTDes_mac");
                return result.getResult();
            } else {
                logTrue("calcDUKPTDes_mac");
                return null;
            }
        } catch (PedDevException e) {
            e.printStackTrace();
            logErr("calcDUKPTDes_mac", e.toString());
            return null;
        }

    }

    public byte[] calcDUKPTDesDes(byte[] bytes) {
        try {
            DUKPTResult result = ped.calcDUKPTDes((byte) 0x01, (byte) 0x01, null, bytes, EDUKPTDesMode.CBC_ENCRYPTION);
            if (result != null) {
                logTrue("calcDUKPTDes_des");
                return result.getResult();
            } else {
                logTrue("calcDUKPTDes_des");
                return null;
            }
        } catch (PedDevException e) {
            e.printStackTrace();
            logErr("calcDUKPTDes_des", e.toString());
            return null;
        }

    }

    public byte[] getDUKPTKsn() {
        try {
            byte[] bytes_ksn = ped.getDUKPTKsn((byte) 1);
            logTrue("getDUKPTKsn");
            return bytes_ksn;
        } catch (PedDevException e) {
            e.printStackTrace();
            logErr("getDUKPTKsn", e.toString());
            return null;
        }

    }

    public boolean incDUKPTKsn() {
        try {
            ped.incDUKPTKsn((byte) 0x01);
            logTrue("incDUKPTKsn");
            return true;
        } catch (PedDevException e) {
            e.printStackTrace();
            logErr("incDUKPTKsn", e.toString());
            return false;
        }
    }

    public void setExMode(byte b) {
        ped.setExMode(b);
        logTrue("setExMode");
    }

    public boolean clearScreen() {
        try {
            ped.clearScreen();
            logTrue("clearScreen");
            return true;
        } catch (PedDevException e) {
            e.printStackTrace();
            logErr("clearScreen", e.toString());
            return false;
        }
    }

    public String inputStr_1(int num1, int num2) {
        try {
            String str = ped.inputStr((byte) 0x00, (byte) num1, (byte) num2, 10000);
            logTrue("inputStr_1");
            return str;
        } catch (PedDevException e) {
            e.printStackTrace();
            logErr("inputStr_1", e.toString());
            return null;
        }
    }

    public String inputStr_2(int num1, int num2) {
        try {
            String str = ped.inputStr((byte) 0x01, (byte) num1, (byte) num2, 10000);
            logTrue("inputStr_2");
            return str;
        } catch (PedDevException e) {
            e.printStackTrace();
            logErr("inputStr_2", e.toString());
            return null;
        }
    }

    public boolean showStr(String str) {
        try {
            ped.showStr((byte) 0x00, (byte) 0x00, str);
            logTrue("showStr");
            return true;
        } catch (PedDevException e) {
            e.printStackTrace();
            logErr("showStr", e.toString());
            return false;
        }
    }

    public String getSN() {
        String sn = null;
        try {
            sn = ped.getSN();
        } catch (PedDevException e) {
            e.printStackTrace();
        }
        logTrue("getSN");
        return sn == null ? "null" : sn;
    }

    public void showInputBox(boolean flag, String title) {
        try {
            ped.showInputBox(flag, title);
            logTrue("showInputBox");
        } catch (PedDevException e) {
            e.printStackTrace();
            logErr("showInputBox", e.toString());
        }
    }

    public SM2KeyPair genSM2KeyPair(short keyLenBit) {
        try {
            SM2KeyPair keyPair = ped.genSM2KeyPair(keyLenBit);
            logTrue("genSM2KeyPair");
            return keyPair;
        } catch (PedDevException e) {
            e.printStackTrace();
            logErr("genSM2KeyPair", e.toString());
            return null;
        }
    }

    public boolean writeSM2CipherKey(EPedKeyType srcKeyType, byte srcKeyIdx, EPedKeyType dstKeyType, byte dstKeyIdx,
            byte[] keyValue) {
        try {
            ped.writeSM2CipherKey(srcKeyType, srcKeyIdx, dstKeyType, dstKeyIdx, keyValue);
            logTrue("writeSM2CipherKey");
            return true;
        } catch (PedDevException e) {
            e.printStackTrace();
            logErr("writeSM2CipherKey", e.toString());
            return false;
        }

    }

    public boolean writeSM2Key(byte keyIdx, EPedKeyType keyType, byte[] keyValue) {
        try {
            ped.writeSM2Key(keyIdx, keyType, keyValue);
            logTrue("writeSM2Key");
            return true;
        } catch (PedDevException e) {
            e.printStackTrace();
            logErr("writeSM2Key", e.toString());
            return false;
        }
    }

    public byte[] SM2Recover(byte keyIdx, byte[] input, ECryptOperate operation) {
        byte[] res = null;
        try {
            res = ped.SM2Recover(keyIdx, input, operation);
            logTrue("SM2Recover");
            return res;
        } catch (PedDevException e) {
            e.printStackTrace();
            logErr("SM2Recover", e.toString());
        }
        return res;
    }

    public byte[] SM2Sign(byte pubKeyIdx, byte pvtKeyIdx, byte[] uid, byte[] input) {
        byte[] res = null;
        try {
            res = ped.SM2Sign(pubKeyIdx, pvtKeyIdx, uid, input);
            logTrue("SM2Sign");
            return res;
        } catch (PedDevException e) {
            e.printStackTrace();
            logErr("SM2Sign", e.toString());
        }
        return res;
    }

    public boolean SM2Verify(byte pubKeyIdx, byte[] uid, byte[] input, byte[] signature) {
        try {
            ped.SM2Verify(pubKeyIdx, uid, input, signature);
            logTrue("SM2Verify");
            return true;
        } catch (PedDevException e) {
            e.printStackTrace();
            logErr("SM2Verify", e.toString());
            return false;
        }
    }

    public byte[] SM3(byte[] input) {
        byte[] res = null;
        try {
            res = ped.SM3(input, (byte) 0);
            logTrue("SM3");
            return res;
        } catch (PedDevException e) {
            e.printStackTrace();
            logErr("SM3", e.toString());
        }
        return res;
    }

    public byte[] SM4(byte keyIdx, byte[] initVector, byte[] input, ECryptOperate operation, ECryptOpt option) {
        byte[] res = null;
        try {
            res = ped.SM4(keyIdx, initVector, input, operation, option);
            logTrue("SM4");
            return res;
        } catch (PedDevException e) {
            e.printStackTrace();
            logErr("SM4", e.toString());
        }
        return res;
    }

    public byte[] getMacSM(byte keyIdx, byte[] initVector, byte[] input, byte mode) {
        byte[] res = null;
        try {
            res = ped.getMacSM(keyIdx, initVector, input, mode);
            logTrue("getMacSM");
            return res;
        } catch (PedDevException e) {
            e.printStackTrace();
            logErr("getMacSM", e.toString());
        }
        return res;
    }

    public byte[] getPinBlockSM4(byte keyIndex, String expPinLen, byte[] dataIn, EPinBlockMode mode, int timeoutMs) {
        byte[] res = null;
        try {
            res = ped.getPinBlockSM4(keyIndex, expPinLen, dataIn, mode, timeoutMs);
            logTrue("getPinBlockSM4");
            return res;
        } catch (PedDevException e) {
            e.printStackTrace();
            logErr("getPinBlockSM4", e.toString());
        }
        return res;
    }

    public void setKeyboardLayoutLandscape(boolean landscape) {
        try {
            ped.setKeyboardLayoutLandscape(landscape);
            logTrue("setKeyboardLayoutLandscape");
        } catch (PedDevException e) {
            e.printStackTrace();
            logErr("setKeyboardLayoutLandscape", e.toString());
        }
    }
    
    public boolean writeAesKey(EPedKeyType srcKeyType, byte srcKeyIndex, byte destkeyIndex,
            byte[] destKeyValue, EAesCheckMode checkMode, byte[] checkBuf){
        try {
            ped.writeAesKey(srcKeyType, srcKeyIndex, destkeyIndex, destKeyValue, checkMode, checkBuf);
            logTrue("writeAesKey");
            return true;
        } catch (PedDevException e) {
            e.printStackTrace();
            logErr("writeAesKey", e.toString());
            return false;
        }
    }
    
    public byte[] calcAes(byte keyIdx, byte[] initvector, byte[] dataIn,  ECryptOperate operation, ECryptOpt option){
        try {
           byte[] res = ped.calcAes(keyIdx, initvector, dataIn, operation, option);
           logTrue("calcAes");
           return res;
        } catch (PedDevException e) {
            e.printStackTrace();
            logErr("calcAes", e.toString());
            return null;
        }
    }
    
    public void setKeyboardRandom(boolean flag){
        try {
            ped.setKeyboardRandom(flag);
            logTrue("setKeyboardRandom");
        } catch (PedDevException e) {
            e.printStackTrace();
            logErr("setKeyboardRandom", e.toString());
        }
    }
}
