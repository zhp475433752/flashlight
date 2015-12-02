package com.hpzhang.flashlight;

import android.app.Activity;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * 手电筒
 * @author hpzhang
 */
public class MainActivity extends Activity {

    private ImageView btn_switch;
    private boolean state = false;
    private Camera camera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_switch = (ImageView) findViewById(R.id.btn_switch);
        try{
            camera = Camera.open();
            if(camera != null){
                camera.startPreview();
            }
        }catch(Exception e){
            Toast.makeText(this, R.string.check, Toast.LENGTH_LONG).show();
        }

    }


    public void onSwitch(View view) {
        if(camera != null){
            state = !state;
            if(state){
                btn_switch.setImageResource(R.mipmap.on);
                // 摄像头的闪光灯打开
                setParameter(true);
            }else{
                btn_switch.setImageResource(R.mipmap.off);
                // 摄像头的闪光灯关闭
                setParameter(false);
            }
        }


    }

    private void setParameter(boolean flag) {
        if (camera != null) {
            Camera.Parameters parameters = camera.getParameters();
            if (flag) {
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            } else {
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            }
            camera.setParameters(parameters);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(camera != null){
            camera.stopPreview();
            camera.release();
            camera = null;
        }
    }
}
