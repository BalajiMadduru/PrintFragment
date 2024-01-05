package com.pax.demo.modules.scancodec;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PreviewCallback;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.pax.dal.entity.DecodeResult;
import com.pax.demo.R;
import com.pax.demo.base.BaseFragment;

import java.io.IOException;


import static android.hardware.Camera.Parameters.FLASH_MODE_OFF;
import static android.hardware.Camera.Parameters.FLASH_MODE_ON;
import static android.hardware.Camera.Parameters.FLASH_MODE_TORCH;

public class CodecFragment extends BaseFragment implements PreviewCallback, OnClickListener, SurfaceHolder.Callback {

    private Camera camera;
    private SurfaceView surfaceView;
    private Button startBt, switchCamera, switchFlash;
    private TextView textView;

    private int CameraID, FlashTorchID = 0;
    private String FlashTorch = FLASH_MODE_OFF;

    private boolean isOpen = false;
    private static final int REQUEST_CODE = 1;

    private byte[] data = null;
    private SurfaceHolder surfaceHolder;
    private int WIDTH = 640, HEIGHT = 480;
    private static final int MAX_FRAME_COUNT = 20;
    private int frameCount = 0;
    private byte[] preivewBuf;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_scancodec, container, false);
        surfaceView = (SurfaceView) view.findViewById(R.id.fragment_scancodec_surfaceview);
        startBt = (Button) view.findViewById(R.id.fragment_scancodec_start_bt);
        switchCamera = (Button) view.findViewById(R.id.fragment_scancodec_switchCamera_bt);
        switchFlash = (Button) view.findViewById(R.id.fragment_scancodec_switchFlash_bt);
        textView = (TextView) view.findViewById(R.id.fragment_scancodec_res_text);

        switchFlash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchFlashTorch();
            }
        });
        switchCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               switchCamera();
            }
        });


        startBt.setOnClickListener(this);
        return view;
    }

    // private Camera.AutoFocusCallback mAutoFocusCallback = new Camera.AutoFocusCallback() {
    // public void onAutoFocus(boolean success, Camera camera) {
    // Log.d("AutoFocusManager", "onAutoFocus call...success=" + success);
    // }
    // };
    //
    // private void startAutoFocus() {
    // if (camera != null) {
    // camera.autoFocus(mAutoFocusCallback);
    // }
    // }

    @Override
    public void onPreviewFrame(byte[] data, Camera camera) {
        // frameCount++;
        // if (frameCount > MAX_FRAME_COUNT) {
        // frameCount = 0;
        // startAutoFocus();
        // }

        if (data != null) {
            Log.i("Test", "dataLEN:" + data.length);
            // new DecodeThread().start();
            long startTime = System.currentTimeMillis();
            DecodeResult decodeResult = ScanCodecTester.getInstance().decode(data);
            long timeCost = System.currentTimeMillis() - startTime;
            String res = "timeCost:"
                    + timeCost
                    + " result:"
                    + ((decodeResult == null || decodeResult.getContent() == null) ? "null" : decodeResult.getContent());
            Log.i("Test", res);

            textView.setText(res);
            camera.addCallbackBuffer(data);
        }
    }

    private void enableFormat() {
        for (int i = 1; i <= 24; i++) {
            ScanCodecTester.getInstance().enableFormat(i);
        }
    }

    @Override
    public void onClick(View v) {
        if (!isOpen) {
            if (!initCamera()) {
                return;
            }

            ScanCodecTester.getInstance().init(getActivity(), WIDTH, HEIGHT);
            enableFormat();
            surfaceHolder = surfaceView.getHolder();
            surfaceHolder.addCallback(this);

            camera.addCallbackBuffer(data);
            camera.setPreviewCallbackWithBuffer(CodecFragment.this);

            try {
                camera.setPreviewDisplay(surfaceHolder);
            } catch (IOException e) {
                e.printStackTrace();
            }
            startPreview();

            startBt.setText("close");
            isOpen = !isOpen;
        } else {
            releaseRes();
            startBt.setText("open");
            isOpen = !isOpen;
        }
    }

    public boolean initCamera() {
        int res = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA);
        if (res == PackageManager.PERMISSION_DENIED) {
            Log.i("Test", "PERMISSION_DENIED");
            Toast.makeText(getActivity(), "PERMISSION_DENIED", Toast.LENGTH_LONG).show();
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, REQUEST_CODE);
            return false;
        } else {
            Log.i("Test", "PERMISSION_GRANTED");
        }

        camera = Camera.open(CameraID);
        // camera.setDisplayOrientation(90);
        Parameters parameters = camera.getParameters();
        Log.i("Test", parameters == null ? "null" : "not null");

        parameters.setPreviewSize(WIDTH, HEIGHT);
        parameters.setPictureSize(WIDTH, HEIGHT);
        parameters.setExposureCompensation(-3);
        parameters.setZoom(parameters.getZoom() + 15);
        parameters.setFlashMode(FlashTorch);
        // parameters.setFocusMode(Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
        // parameters.setAutoWhiteBalanceLock(true);

        camera.setParameters(parameters);
        try {
            camera.cancelAutoFocus();
            camera.autoFocus(new AutoFocusCallback() {
                @Override
                public void onAutoFocus(boolean success, Camera camera) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        preivewBuf = new byte[10 * WIDTH * HEIGHT];

        // For formats besides YV12, the size of the buffer is determined by multiplying the preview image width,
        // height, and bytes per pixel. The width and height can be read from Camera.Parameters.getPreviewSize(). Bytes
        // per pixel can be computed from android.graphics.ImageFormat.getBitsPerPixel(int) / 8, using the image format
        // from Camera.Parameters.getPreviewFormat().
        float bytesPerPixel = ImageFormat.getBitsPerPixel(parameters.getPreviewFormat()) / (float) 8;
        data = new byte[(int) (bytesPerPixel * WIDTH * HEIGHT)];

        Log.i("Test", "previewFormat:" + parameters.getPreviewFormat() + " bytesPerPixel:" + bytesPerPixel
                + " prewidth:" + parameters.getPreviewSize().width + " preheight:" + parameters.getPreviewSize().height);

        return true;
    }

//    @TargetApi(23) @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if(requestCode == REQUEST_CODE){
//            if(grantResults != null && grantResults[0] == PackageManager.PERMISSION_GRANTED){
//               
//            }
//        }
//    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.i("Test", "format:" + format + "width:" + width + "height:" + height);
        synchronized (this) {
            try {
                camera.setPreviewDisplay(surfaceHolder);
            } catch (IOException e) {
                e.printStackTrace();
            }

            camera.setPreviewCallbackWithBuffer(this);
            camera.addCallbackBuffer(preivewBuf);
            camera.startPreview();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    private void startPreview() {
        if (camera != null) {
            camera.setPreviewCallbackWithBuffer(this);
            camera.addCallbackBuffer(preivewBuf);
            camera.startPreview();
        }
    }

    private void stopPreview() {
        if (camera != null) {
            camera.setPreviewCallbackWithBuffer(null);
            camera.stopPreview();
        }
    }

    private void releaseRes() {
        ScanCodecTester.getInstance().release();
        stopPreview();
        camera.release();
        camera = null;
    }

    @Override
    public void onStop() {
        super.onStop();
        stopPreview();
    }

    @Override
    public void onDestroy() {
        if (isOpen) {
            releaseRes();
        }
        super.onDestroy();
    }

    public void  refreshView(){
        releaseRes();
        initCamera();

        ScanCodecTester.getInstance().init(getActivity(), WIDTH, HEIGHT);
        enableFormat();
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);
        camera.addCallbackBuffer(data);
        camera.setPreviewCallbackWithBuffer(CodecFragment.this);
        try {
            camera.setPreviewDisplay(surfaceHolder);
        } catch (IOException e) {
            e.printStackTrace();
        }

        startPreview();

    }

    public void switchCamera(){
        if (CameraID == 1){
            CameraID = 0;
        }else{
            CameraID = 1;
        }
        refreshView();

    }


    public void switchFlashTorch(){
        FlashTorchID = (FlashTorchID + 1) % 3;
        switch (FlashTorchID) {
            case 0 :
                FlashTorch = FLASH_MODE_OFF;
                break;
            case 1  :
                FlashTorch = FLASH_MODE_ON;
                break;
            case 2 :
                FlashTorch = FLASH_MODE_TORCH;
                break;
        }
        refreshView();

    }


}
