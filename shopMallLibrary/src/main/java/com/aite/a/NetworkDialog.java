package com.aite.a;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.aiteshangcheng.a.R;

/**
 * 网络检查
 *
 * @author CAOYOU
 */
public class NetworkDialog extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.net_dialog);
        setFinishOnTouchOutside(false);//
        AppManager.getInstance().addActivity(this);
        Button setting = (Button) findViewById(R.id.setting);
        setting.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.settings.WIRELESS_SETTINGS");
                startActivity(intent);

            }
        });
        Button cancel = (Button) findViewById(R.id.bt_cancel);
        cancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                AppManager.getInstance().AppExit(NetworkDialog.this);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitBy2Click();
        }
        return false;
    }

    private void exitBy2Click() {
        AppManager.getInstance().AppExit(NetworkDialog.this);
        System.exit(0);
    }

}