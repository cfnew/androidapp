package com.example.bupt.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.KeyEvent;

public class NewDialog extends Dialog{

	public NewDialog(Context context, int theme) {
		super(context, theme);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK ) {
			this.dismiss();
		}
		return super.onKeyDown(keyCode, event);
	}

}
