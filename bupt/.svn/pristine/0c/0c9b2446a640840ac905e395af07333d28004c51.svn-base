package com.example.bupt.ui;

import java.io.File;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

import com.example.bupt.R;
import com.example.bupt.base.BaseActivity;
import com.example.bupt.http.HttpUtils;
import com.example.bupt.utils.FileUtils;
import com.example.bupt.utils.ImageUtil;
import com.example.bupt.utils.StringUtils;
import com.example.bupt.utils.ToastUtil;

/**
 * 图片对话框
 * 
 */
public class ImageDialog extends BaseActivity {

	private ViewSwitcher mViewSwitcher;
	private Button btn_preview;
	private ImageView mImage;

	private Thread thread;
	private Handler handler;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.image_dialog);

		this.initView();

		this.initData();
	}

	private View.OnTouchListener touchListener = new View.OnTouchListener() {
		public boolean onTouch(View v, MotionEvent event) {
			thread.interrupt();
			handler = null;
			finish();
			return true;
		}
	};

	private void initView() {
		mViewSwitcher = (ViewSwitcher) findViewById(R.id.imagedialog_view_switcher);
		mViewSwitcher.setOnTouchListener(touchListener);

		btn_preview = (Button) findViewById(R.id.imagedialog_preview_button);
		btn_preview.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				String imgURL = getIntent().getStringExtra("img_url");
				UiHelper.showImageZoomDialog(v.getContext(), imgURL);
				finish();
			}
		});
		mImage = (ImageView) findViewById(R.id.imagedialog_image);
		mImage.setOnTouchListener(touchListener);
	}

	private void initData() {
		final String imgURL = getIntent().getStringExtra("img_url");
		final String localImg = getIntent().getStringExtra("local_img");
		final String ErrMsg = getString(R.string.msg_load_image_fail);
		handler = new Handler() {
			public void handleMessage(Message msg) {
				if (msg.what == 1 && msg.obj != null) {
					mImage.setImageBitmap((Bitmap) msg.obj);
					mViewSwitcher.showNext();
				} else {
					ToastUtil.showMsg(ImageDialog.this, ErrMsg);
					finish();
				}
			}
		};
		thread = new Thread() {
			public void run() {
				Message msg = new Message();
				Bitmap bmp = null;
				if (!StringUtils.isEmpty(localImg)) {
					bmp = BitmapFactory.decodeFile(localImg);
					btn_preview.setVisibility(View.GONE);
				}
				String filename = FileUtils.getFileName(imgURL);
				try {
					// 读取本地图片
					if (StringUtils.isEmpty(imgURL)) {
						bmp = BitmapFactory.decodeResource(mImage.getResources(), R.drawable.widget_dface);
					}
					if (bmp == null) {
						// 是否有缓存图片
						// Environment.getExternalStorageDirectory();返回/sdcard
						String filepath = getFilesDir() + File.separator + filename;
						File file = new File(filepath);
						if (file.exists()) {
							bmp = ImageUtil.getBitmap(mImage.getContext(),filename);
							if (bmp != null) {
								// 缩放图片
								bmp = ImageUtil.reDrawBitMap(ImageDialog.this,bmp);
							}
						}
					}
					if (bmp == null) {
						bmp = HttpUtils.getNetBitmap(ImageDialog.this,imgURL);
						if (bmp != null) {
							try {
								// 写图片缓存
								ImageUtil.saveImage(mImage.getContext(),filename, bmp);
							} catch (IOException e) {
								e.printStackTrace();
							}
							// 缩放图片
							bmp = ImageUtil.reDrawBitMap(ImageDialog.this, bmp);
						}
					}
					msg.what = 1;
					msg.obj = bmp;
				} catch (Exception e) {
					e.printStackTrace();
					msg.what = -1;
					msg.obj = e;
				}
				if (handler != null && !isInterrupted())
					handler.sendMessage(msg);
			}
		};
		thread.start();
	}
}
