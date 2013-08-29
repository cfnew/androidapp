package com.example.bupt.issue.calendar;


import com.example.bupt.R;
import com.example.bupt.base.BaseActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
public class MyCalendar extends BaseActivity  implements CalendarView.OnCellTouchListener{
	private CalendarView mView = null;
	private  Button btCenter;
	private Rect ecBounds;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);   
        setContentView(R.layout.paopao_issue_calendar);
        mView = (CalendarView)findViewById(R.id.calendar);
        mView.setOnCellTouchListener(this);

        btCenter = (Button) findViewById(R.id.paopao_issue_cal_btCenter);
        btCenter.setText(mView.getYear() + "-" + (mView.getMonth() + 1));
        Button btLeft = (Button) findViewById(R.id.paopao_issue_cal_btnLeft);
        btLeft.setText("上个月");
        Button btRight = (Button) findViewById(R.id.paopao_issue_cal_btRight);
        btRight.setText("下个月");
        btLeft.setOnClickListener(new OnClickListener() { 
			public void onClick(View v) {
				mView.previousMonth(); 
				btCenter.setText(mView.getYear() + "-" + (mView.getMonth() + 1));
			}
		});
        btRight.setOnClickListener(new OnClickListener() { 
			public void onClick(View v) {
				mView.nextMonth(); 
				btCenter.setText(mView.getYear() + "-" + (mView.getMonth() + 1));
			}
		});
        
    }

	public void onTouch(Cell cell) {
		
		if(cell.mPaint.getColor() == Color.GRAY) {
			
			mView.previousMonth(); 
			btCenter.setText(mView.getYear() + "-" + (mView.getMonth() + 1));
		} else if(cell.mPaint.getColor() == Color.LTGRAY) {
			
			mView.nextMonth(); 
			btCenter.setText(mView.getYear() + "-" + (mView.getMonth() + 1));
		} else {  
				Intent ret = new Intent();
				ret.putExtra("year", mView.getYear());
				ret.putExtra("month", mView.getMonth());
				ret.putExtra("day", cell.getDayOfMonth());

				ecBounds = cell.getBound();
				mView.getDate();
				mView.mDecoraClick.setBounds(ecBounds);
				mView.invalidate();
				
				this.setResult(RESULT_OK, ret);
				finish();
				overridePendingTransition(R.anim.fade_in,
						R.anim.fade_out);
				return;
		}

	}
	
	public void paopao_issue_cal_back(View v){
		finish();
		overridePendingTransition(R.anim.fade_in,
				R.anim.fade_out);
	}
 
}