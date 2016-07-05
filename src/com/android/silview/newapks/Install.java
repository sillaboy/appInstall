package com.android.silview.newapks;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

public class Install extends Thread {
	
	private Context mContext ;
	private int type;
	
	public static final int TYPE_WITH_PM_INSTALL = 0;	
	public static final int TYPE_WITH_PM_UNINSTALL = 1;
	public static final int TYPE_WITH_PACKAGEMANAGER_INSTALL = 2;
	public static final int TYPE_WITH_PACKAGEMANAGER_UNINSTALL = 3;
	
	private String installPath;
	private String packgeName;
	
	/*with pm */
	private String CMD_INSTALL = "pm install -r -f";
	private String CMD_UNINSTALL = "pm uninstall -k";
	
	public Install(){		
	}

	public Install(String installPath, Context mContext) {
		super();
		this.mContext = mContext;
	}

	public Context getmContext() {
		return mContext;
	}

	public void setmContext(Context mContext) {
		this.mContext = mContext;
	}	

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	public String getInstallPath() {
		return installPath;
	}

	public void setInstallPath(String installPath) {
		this.installPath = installPath;
	}

	public String getPackgeName() {
		return packgeName;
	}

	public void setPackgeName(String packgeName) {
		this.packgeName = packgeName;
	}

	/**
	 * Install app with pm
	 */
	public void installWithCmd (String installPath) {		
		if ("".equals(installPath) || installPath == null) {
			Log.d(MyGlobal.TAG, "install apk is null");
		} else {
			StringBuilder sBuilder = new StringBuilder();
			Process localProcess = null;
			sBuilder.append(CMD_INSTALL);
			sBuilder.append(" ");
			sBuilder.append(installPath);			
			try {
				Log.d(MyGlobal.TAG, sBuilder.toString());
				localProcess = Runtime.getRuntime().exec(sBuilder.toString());
				localProcess.waitFor();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			} finally {
				if (localProcess.exitValue() != 0) {
					localProcess.destroy();
				}
				File currentApk = new File(installPath);
				if (currentApk.exists()) {
					Log.d(MyGlobal.TAG, installPath + "delete..");
					currentApk.delete();
				} else {
					Log.d(MyGlobal.TAG, installPath + "not exist..");
				}
			}
		}
	}
	
	/**
	 * uninstall app with pm
	 * @param packageName package¡¯s Name
	 */
	
	public void uninstallWithCmd (String packageName) {
		if (packageName == null || "".equals(packageName)) {
			Log.d("TAG", "uninstall apk is null");
		}else {
			StringBuilder sBuilder = new StringBuilder();
			Process localProcess = null;
			sBuilder.append(CMD_UNINSTALL);
			sBuilder.append(" ");
			sBuilder.append(packageName);
			Log.d(MyGlobal.TAG, sBuilder.toString());
			try {
				localProcess = Runtime.getRuntime().exec(sBuilder.toString());
				localProcess.waitFor();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (localProcess.exitValue() != 0) {
					localProcess.destroy();
				}
			}	
		} 
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		Log.d(MyGlobal.TAG, "Begin to install..");
		switch (type) {
		case TYPE_WITH_PM_INSTALL:
			if(!"".equals(installPath)) { 
				installWithCmd(installPath);
			} else {
				Log.e(MyGlobal.TAG, "Pm install path is null.");
			}
			break;
		case TYPE_WITH_PM_UNINSTALL:
			if (!"".equals(packgeName)) {
				uninstallWithCmd(installPath);
			} else {
				Log.e(MyGlobal.TAG, "pm uninstall path is null.");
			}
			break;
		default:
			break;
		}
	}
}
