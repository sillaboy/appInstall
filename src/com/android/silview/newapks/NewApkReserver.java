package com.android.silview.newapks;

import java.io.File;
import java.io.IOException;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Path;
import android.hardware.usb.UsbAccessory;
import android.net.Uri;
import android.util.Log;

import com.android.silview.newapks.MyGlobal;
import com.android.silview.newapks.util.PackageUtils;

public class NewApkReserver extends BroadcastReceiver {	
	
	public static final String ACTION = "android.intent.action.Install_New_Apk";
	public static final String USB_UDISK_STRING = "/mnt/udisk";
	public static final String USB_INFO = "/mnt/udisk/criwell.apk";

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Log.d(MyGlobal.TAG, intent.toString());
		final Uri uri = intent.getData();
		if (intent.getAction().equals(ACTION)) {
			Log.d(MyGlobal.TAG, "from sd..");
			String pathString = intent.getStringExtra("PATH");
			if (pathString != null) {
				Install install = new Install();
				install.setType(Install.TYPE_WITH_PM_INSTALL);
				install.setInstallPath(pathString);
				install.start();
			}
		} else {
			 Log.d(MyGlobal.TAG, "from service...");
			 if (uri == null) {
				 Log.d(MyGlobal.TAG, "url null return!");
				 return;
			 }
             if (uri.getScheme().equals("file")) {
            	 String path = uri.getPath();            	 
	             if(USB_UDISK_STRING.equals(path)) {
	            	//isNew(context);
	            	Install install = new Install();
	 				install.setType(Install.TYPE_WITH_PM_INSTALL);
	 				install.setInstallPath(USB_INFO);
	 				install.start();
	             }
             }
        }  
	}
	
	/**
	 * update app according to version code;
	 * @return
	 */
	private boolean isNew(Context mContext){
		int currentCode, waitCode;
		currentCode = PackageUtils.getAppVersionCodeByName(mContext, MyGlobal.ROBOT_NAME);
		waitCode = PackageUtils.getAppVersionCodeByPath(mContext, USB_INFO);
		Log.d(MyGlobal.TAG, "current code is " + currentCode + " to update code is " + waitCode);
		return currentCode < waitCode ? true : false;
	}
}
