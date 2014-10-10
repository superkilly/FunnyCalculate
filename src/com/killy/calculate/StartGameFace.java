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
	 * ��Ϸ��ҳ�沼��
	 */
	RelativeLayout relativeLayout = null;

	/**
	 * ��ť����������)
	 */
	private static final int widNum = 8;

	/**
	 * ��ť����������[�������]
	 */
	private static final int numHeiNum = 6;

	/**
	 * ��ť����������[��������]
	 */
	private static final int calHeiNum = 2;
	
	/**
	 * ���ְ�ť�������������ť���ֵļ������
	 */
	private static final int SplitHeight = 10;

	/**
	 * �������ҳ����������ְ�ť�����ö�ά���� ��һά�ǰ�ť�ĺ����� �ڶ�ά�ǰ�ť��������
	 */
	private static final Button numBtnArr[][] = new Button[numHeiNum][widNum];

	/**
	 * �������ҳ��������������ť�����ö�ά���� ��һά�ǰ�ť�ĺ����� �ڶ�ά�ǰ�ť��������
	 */
	private static final Button calBtnArr[][] = new Button[calHeiNum][widNum];

	/**
	 * ������б�
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
		// ��������б�
		for (int i = 0; i < numHeiNum; i++) {
			for (int j = 0; j < widNum; j++) {
				numBtnArr[i][j] = new Button(this);
				// ��ť��ID��ͨ��2000+10*�����+�����(���ǵ�һ����಻�ᳬ��10����ť,һ��Ҳ���ᳬ��10��)
				numBtnArr[i][j].setId(getNumId(j, i));
				numBtnArr[i][j].setText(""
						+ Math.abs(random.nextInt(i * SplitHeight + j + 1)));
				addMoveLister(numBtnArr[i][j]);

				GradientDrawable drawable = new GradientDrawable();
				drawable.setShape(GradientDrawable.RECTANGLE); // ����
				drawable.setStroke(1, Color.BLUE); // �߿��ϸ����ɫ
				drawable.setColor(0x22FFFF00); // �߿��ڲ���ɫ
				numBtnArr[i][j].setBackground(drawable);

				RelativeLayout.LayoutParams btParams = new RelativeLayout.LayoutParams(
						btnWidth, btnHeight); // ���ð�ť�Ŀ�Ⱥ͸߶�
				// i��ʾ���ǵ�i�� k��ʾ���ǵ�k��
				btParams.topMargin = btnHeight * i; // �����궨λ
				btParams.leftMargin = btnWidth * j; // �����궨λ
				relativeLayout.addView(numBtnArr[i][j], btParams);
			}
		}

		// ���������б�
		for (int i = 0; i < calHeiNum; i++) {
			for (int j = 0; j < widNum; j++) {
				calBtnArr[i][j] = new Button(this);
				// ��ť��ID��ͨ��2000+10*�����+�����(���ǵ�һ����಻�ᳬ��10����ť,һ��Ҳ���ᳬ��10��)
				calBtnArr[i][j].setId(getCalId(j, i));
				calBtnArr[i][j].setText(calArr[random.nextInt(calArr.length)]);
				addMoveLister(calBtnArr[i][j]);

				GradientDrawable drawable = new GradientDrawable();
				drawable.setShape(GradientDrawable.RECTANGLE); // ����
				drawable.setStroke(1, Color.YELLOW); // �߿��ϸ����ɫ
				drawable.setColor(0x44FF2200); // �߿��ڲ���ɫ
				calBtnArr[i][j].setBackground(drawable);

				RelativeLayout.LayoutParams btParams = new RelativeLayout.LayoutParams(
						btnWidth, btnHeight); // ���ð�ť�Ŀ�Ⱥ͸߶�
				// i��ʾ���ǵ�i�� k��ʾ���ǵ�k��
				btParams.topMargin = btnHeight * (i + numHeiNum) + SplitHeight; // �����궨λ
				btParams.leftMargin = btnWidth * j; // �����궨λ
				relativeLayout.addView(calBtnArr[i][j], btParams);
			}
		}

		// ��Ӵ������������
		RelativeLayout waitCalLayout = new RelativeLayout(this);
		waitCalLayout.layout(0, btnHeight * (numHeiNum + calHeiNum + SplitHeight * 2), screenWidth, screenHeight);
		GradientDrawable drawable = new GradientDrawable();
		drawable.setShape(GradientDrawable.RECTANGLE); // ����
		drawable.setStroke(2, Color.RED); // �߿��ϸ����ɫ
		drawable.setColor(0xFF662200); // �߿��ڲ���ɫ
		waitCalLayout.setBackground(drawable);
		RelativeLayout.LayoutParams layParams = new RelativeLayout.LayoutParams(
				screenWidth - 10, screenHeight - 10 - btnHeight * (numHeiNum + calHeiNum + SplitHeight * 2)); // ���ð�ť�Ŀ�Ⱥ͸߶�
		// i��ʾ���ǵ�i�� k��ʾ���ǵ�k��
		layParams.topMargin = btnHeight * (numHeiNum + calHeiNum + SplitHeight * 2); // �����궨λ
		layParams.leftMargin = 0; // �����궨λ
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
					// ��ȡ�����¼�����λ�õ�ԭʼX����
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
					// �����ж��ƶ��Ƿ񳬳���Ļ
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
					 * 1������ŵ�Ŀ��������ʽ��Χ�ڣ���ŵ�Ŀ��������ʽ��Χ�ڵ���ȷ������
					 * 2�����û�зŵ�Ŀ��������ʽ��Χ�ڣ��򷵻���ԭ����λ��
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
		// �����ж��ƶ��Ƿ񳬳���Ļ
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
		// ���ð�ť�ĳ���һ��Ĳ����Ѿ�����������ʽ���ڣ����ʶ�ð�ť������������ʽ����
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
