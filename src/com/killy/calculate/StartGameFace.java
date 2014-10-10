package com.killy.calculate;

import java.util.Random;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.RelativeLayout;

public class StartGameFace extends Activity {

	/**
	 * 游戏主页面布局
	 */
	RelativeLayout relativeLayout = null;

	/**
	 * 按钮个数（横向)
	 */
	private static final int widNum = 8;

	/**
	 * 按钮个数（纵向）[存放数字]
	 */
	private static final int numHeiNum = 6;

	/**
	 * 按钮个数（纵向）[存放运算符]
	 */
	private static final int calHeiNum = 2;
	
	/**
	 * 数字按钮部分与运算符按钮部分的间隔长度
	 */
	private static final int SplitHeight = 10;

	/**
	 * 存放整个页面的所有数字按钮，采用二维数组 第一维是按钮的横坐标 第二维是按钮的纵坐标
	 */
	private static final Button numBtnArr[][] = new Button[numHeiNum][widNum];

	/**
	 * 存放整个页面的所有运算符按钮，采用二维数组 第一维是按钮的横坐标 第二维是按钮的纵坐标
	 */
	private static final Button calBtnArr[][] = new Button[calHeiNum][widNum];

	/**
	 * 运算符列表
	 */
	private static final String[] calArr = { "+", "-", "*", "/" };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.start_game_face);
		relativeLayout = new RelativeLayout(this);
		relativeLayout.setId(121212);
		DisplayMetrics dm = getResources().getDisplayMetrics();
		int screenWidth = dm.widthPixels;
		int screenHeight = dm.heightPixels;
		final int btnWidth = screenWidth / widNum;
		final int btnHeight = btnWidth;
		Random random = new Random();
		// 添加数字列表
		for (int i = 0; i < numHeiNum; i++) {
			for (int j = 0; j < widNum; j++) {
				numBtnArr[i][j] = new Button(this);
				// 按钮的ID是通过2000+10*横序号+纵序号(考虑到一横最多不会超过10个按钮,一纵也不会超过10个)
				numBtnArr[i][j].setId(getNumId(j, i));
				numBtnArr[i][j].setText(""
						+ Math.abs(random.nextInt(i * SplitHeight + j + 1)));
				addMoveLister(numBtnArr[i][j]);

				GradientDrawable drawable = new GradientDrawable();
				drawable.setShape(GradientDrawable.RECTANGLE); // 画框
				drawable.setStroke(1, Color.BLUE); // 边框粗细及颜色
				drawable.setColor(0x22FFFF00); // 边框内部颜色
				numBtnArr[i][j].setBackground(drawable);

				RelativeLayout.LayoutParams btParams = new RelativeLayout.LayoutParams(
						btnWidth, btnHeight); // 设置按钮的宽度和高度
				// i表示的是第i行 k表示的是第k列
				btParams.topMargin = btnHeight * i; // 纵坐标定位
				btParams.leftMargin = btnWidth * j; // 横坐标定位
				relativeLayout.addView(numBtnArr[i][j], btParams);
			}
		}

		// 添加运算符列表
		for (int i = 0; i < calHeiNum; i++) {
			for (int j = 0; j < widNum; j++) {
				calBtnArr[i][j] = new Button(this);
				// 按钮的ID是通过2000+10*横序号+纵序号(考虑到一横最多不会超过10个按钮,一纵也不会超过10个)
				calBtnArr[i][j].setId(getCalId(j, i));
				calBtnArr[i][j].setText(calArr[random.nextInt(calArr.length)]);
				addMoveLister(calBtnArr[i][j]);

				GradientDrawable drawable = new GradientDrawable();
				drawable.setShape(GradientDrawable.RECTANGLE); // 画框
				drawable.setStroke(1, Color.YELLOW); // 边框粗细及颜色
				drawable.setColor(0x44FF2200); // 边框内部颜色
				calBtnArr[i][j].setBackground(drawable);

				RelativeLayout.LayoutParams btParams = new RelativeLayout.LayoutParams(
						btnWidth, btnHeight); // 设置按钮的宽度和高度
				// i表示的是第i行 k表示的是第k列
				btParams.topMargin = btnHeight * (i + numHeiNum) + SplitHeight; // 纵坐标定位
				btParams.leftMargin = btnWidth * j; // 横坐标定位
				relativeLayout.addView(calBtnArr[i][j], btParams);
			}
		}

		// 添加待填充的运算格子
		RelativeLayout waitCalLayout = new RelativeLayout(this);
		waitCalLayout.layout(0, btnHeight * (numHeiNum + calHeiNum + SplitHeight * 2), screenWidth, screenHeight);
		GradientDrawable drawable = new GradientDrawable();
		drawable.setShape(GradientDrawable.RECTANGLE); // 画框
		drawable.setStroke(2, Color.RED); // 边框粗细及颜色
		drawable.setColor(0xFF662200); // 边框内部颜色
		waitCalLayout.setBackground(drawable);
		RelativeLayout.LayoutParams layParams = new RelativeLayout.LayoutParams(
				screenWidth - 10, screenHeight - 10 - btnHeight * (numHeiNum + calHeiNum + SplitHeight * 2)); // 设置按钮的宽度和高度
		// i表示的是第i行 k表示的是第k列
		layParams.topMargin = btnHeight * (numHeiNum + calHeiNum + SplitHeight * 2); // 纵坐标定位
		layParams.leftMargin = 0; // 横坐标定位
		relativeLayout.addView(waitCalLayout, layParams);

		setContentView(relativeLayout);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main_face, menu);
		return true;
	}

	private void addMoveLister(final Button btn) {
		DisplayMetrics dm = getResources().getDisplayMetrics();
		final int screenWidth = dm.widthPixels;
		final int screenHeight = dm.heightPixels - 50;
		btn.setOnTouchListener(new OnTouchListener() {
			int lastX, lastY;

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				int ea = event.getAction();
				Log.i("TAG", "Touch:" + ea);
				switch (ea) {
				case MotionEvent.ACTION_DOWN:
					// 获取触摸事件触摸位置的原始X坐标
					lastX = (int) event.getRawX();
					lastY = (int) event.getRawY();
					relativeLayout.bringChildToFront(v);
					v.postInvalidate();
					break;
				case MotionEvent.ACTION_MOVE:
					int dx = (int) event.getRawX() - lastX;
					int dy = (int) event.getRawY() - lastY;
					int l = v.getLeft() + dx;
					int b = v.getBottom() + dy;
					int r = v.getRight() + dx;
					int t = v.getTop() + dy;
					// 下面判断移动是否超出屏幕
					if (l < 0) {
						l = 0;
						r = l + v.getWidth();
					}
					if (t < 0) {
						t = 0;
						b = t + v.getHeight();
					}
					if (r > screenWidth) {
						r = screenWidth;
						l = r - v.getWidth();
					}
					if (b > screenHeight) {
						b = screenHeight;
						t = b - v.getHeight();
					}
					v.layout(l, t, r, b);
					lastX = (int) event.getRawX();
					lastY = (int) event.getRawY();
					v.postInvalidate();
					break;
				case MotionEvent.ACTION_UP:
					/**
					 * 1：如果放到目的运算表达式范围内，则放到目的运算表达式范围内的正确格子中
					 * 2：如果没有放到目的运算表达式范围内，则返回其原来的位置
					 */
					actionUp(v, event, lastX, lastY, screenWidth, screenHeight);
					break;
				}
				return false;
			}
		});
	}

	private void actionUp(View v, MotionEvent event, int lastX, int lastY,
			int screenWidth, int screenHeight) {
		int dx = (int) event.getRawX() - lastX;
		int dy = (int) event.getRawY() - lastY;
		int l = v.getLeft() + dx;
		int b = v.getBottom() + dy;
		int r = v.getRight() + dx;
		int t = v.getTop() + dy;
		// 下面判断移动是否超出屏幕
		if (l < 0) {
			l = 0;
			r = l + v.getWidth();
		}
		if (t < 0) {
			t = 0;
			b = t + v.getHeight();
		}
		if (r > screenWidth) {
			r = screenWidth;
			l = r - v.getWidth();
		}
		if (b > screenHeight) {
			b = screenHeight;
			t = b - v.getHeight();
		}
		
		int btnSize = screenWidth / widNum;
		// 当该按钮的超过一半的部分已经落入运算表达式框内，则标识该按钮被拉入运算表达式框内
		if (t > btnSize * (numHeiNum + calHeiNum) - btnSize / 2 + 2 * SplitHeight) {
			l = btnSize * (l / btnSize);
//			t = 
		} else {
			if (isNumBtn(v.getId())) {
				int top = btnSize * (v.getId() / 10);
				int left = btnSize * (v.getId() % 10);
				v.layout(left, top, left + btnSize, top + btnSize);
			} else if (isCalBtn(v.getId())) {
				int top = btnSize * (v.getId() / 10);
				int left = btnSize * (v.getId() % 10);
				v.layout(left, top + 10, left + btnSize, top + btnSize + SplitHeight);
			}
		}
	}

	private int getNumId(int widthIndex, int heightIndex) {
		return 10 * heightIndex + widthIndex;
	}

	private int getCalId(int widthIndex, int heightIndex) {
		return 10 * (heightIndex + numHeiNum) + widthIndex;
	}

	private boolean isNumBtn(int id) {
		if (id / 10 < numHeiNum) {
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}

	private boolean isCalBtn(int id) {
		if (id / 10 >= numHeiNum && id / 10 < calHeiNum + numHeiNum) {
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}
}
