package com.example.bupt.base;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import com.example.bupt.utils.AppUtil;

public abstract class BaseActivity extends Activity {
	protected BaseApp baseApp = null;
	protected boolean showLoadBar = false;
	protected boolean showDebugMsg = true;
	// 是否允许销毁
	private boolean allowDestroy = true;

	private View view;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/* 显示内存使用情况 */
		debugMemory("onCreate");
		/* 初始化Application */
		this.baseApp = (BaseApp) this.getApplicationContext();
		// 添加Activity到堆栈
		AppManager.getAppManager().addActivity(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		/* 显示内存使用 */
		debugMemory("onResume");
	}

	@Override
	protected void onPause() {
		super.onPause();
		/* 显示内存使用 */
		debugMemory("onPause");
	}

	@Override
	public void onStart() {
		super.onStart();
		/* 显示内存使用 */
		debugMemory("onStart");
	}

	@Override
	public void onStop() {
		super.onStop();
		/* 显示内存使用 */
		debugMemory("onStop");
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		// 结束Activity&从堆栈中移除
		AppManager.getAppManager().finishActivity(this);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && view != null) {
			view.onKeyDown(keyCode, event);
			if (!allowDestroy) {
				return false;
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	public void setAllowDestroy(boolean allowDestroy) {
		this.allowDestroy = allowDestroy;
	}

	public void setAllowDestroy(boolean allowDestroy, View view) {
		this.allowDestroy = allowDestroy;
		this.view = view;
	}

	

	// 调试方法//////////////////////////////////////////////////////////////////////////////////
	public void debugMemory(String tag) {
		if (this.showDebugMsg) {
			Log.w(this.getClass().getSimpleName(),
					tag + ":" + AppUtil.getUsedMemory());
		}
	}

}
